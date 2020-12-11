package webdev.tcp.chatroom.demo04;

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
				sendOthers(clientName+" joined chatroom");
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
		public void sendOthers(String msg) {
			for(MyChannel channel : all) {
				if (channel==this) {
					continue;
				}
				//发给其他client
				channel.send(msg);
			}
		}

		@Override
		public void run() {
			while (isRunning) {
//				send(receive());
				sendOthers(this.clientName+": "+receive());
			}

		}
	}

}
