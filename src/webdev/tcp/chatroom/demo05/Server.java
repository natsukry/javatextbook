package webdev.tcp.chatroom.demo05;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 问题：只能接受一个客户端 改进：加入多线程
 */
public class Server {
	private List<MyChannel> all = new ArrayList<MyChannel>();
	public static void main(String[] args) throws IOException {
		new Server().start();
	}

	public void start() throws IOException{
		ServerSocket server = new ServerSocket(9999);
		
		while (true) {
			Socket socket = server.accept();
			MyChannel channel = new MyChannel(socket);
			all.add(channel);  //每条通路都加入到all容器中，便于管理。
			new Thread(channel).start();
		}
	}

	// 每个client一条通道
	private class MyChannel implements Runnable {
		DataInputStream dis;
		DataOutputStream dos;
		private boolean isRunning = true;
		private String clientName;
		public MyChannel(Socket socket) {
			try {
				dis = new DataInputStream(socket.getInputStream());
				dos = new DataOutputStream(socket.getOutputStream());
				clientName=dis.readUTF();
				send("welcome you "+clientName);
				sendOthers(clientName+" joined chatroom",true);
			} catch (IOException e) {
				isRunning = false;
				CloseUtil.closeAll(dis, dos);
				all.remove(this);  // 移除自身
			}

		}

		public void send(String msg) {
			if (msg != null && !msg.equals("")) {
				try {
					dos.writeUTF(msg);
					dos.flush();
				} catch (IOException e) {
					isRunning = false;
					CloseUtil.closeAll(dos);
					all.remove(this);
				}
			}

		}

		public String receive() {
			String msg = "";
			try {
				msg = dis.readUTF();
			} catch (IOException e) {
				isRunning = false;
				CloseUtil.closeAll(dos);
				all.remove(this);
			}
			return msg;

		}
		
		//发给其他client
		public void sendOthers(String msg, boolean isSysMsg) {
			if (msg.contains("@")) { // 私聊信息
				String toName = msg.substring(1,msg.indexOf(":"));
				String content = msg.substring(msg.indexOf(":")+1);
//				System.out.println(toName+"      ---------------");
				
				for(MyChannel channel : all) { // 寻找toName对应的通路
					if (channel.clientName.equals(toName) ) {
						//发给toName 对应client
						channel.send(this.clientName+" 悄悄对您说："+content);
					}
					
				}
			}else {
				for(MyChannel channel : all) {
					if (channel==this) {
						continue;
					}
					if (isSysMsg) { // 系统消息
						channel.send("系统消息："+msg);
					}
					else {
						//发给其他client
						channel.send(this.clientName+"对大家说："+msg);
					}
					
				}
			}
			
		}

		@Override
		public void run() {
			while (isRunning) {
//				send(receive());
				sendOthers(receive(),false);
			}

		}
	}

}
