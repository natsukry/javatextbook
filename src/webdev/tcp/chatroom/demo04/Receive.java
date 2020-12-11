package webdev.tcp.chatroom.demo04;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;


public class Receive implements Runnable{
	private DataInputStream dis;
	private boolean isRunning = true;
	
	public Receive(Socket socket) {
		try {
			dis = new DataInputStream(socket.getInputStream());
		} catch (IOException e) {
			isRunning = false;
			CloseUtil.closeAll(dis);
		}
	}
	
	public String receiveMsg() {
		String msg = "";
		try {
			msg = dis.readUTF();
		} catch (IOException e) {
			isRunning = false;
			CloseUtil.closeAll(dis);
		}
		return msg;
	}
	@Override
	public void run() {
		while(isRunning) {
			System.out.println(receiveMsg());
		}
		
	}

}
