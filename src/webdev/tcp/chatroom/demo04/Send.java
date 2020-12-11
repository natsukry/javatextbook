package webdev.tcp.chatroom.demo04;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Send implements Runnable {

	private DataOutputStream dos;
	private BufferedReader consoleBR;
	private Boolean isRunning = true;
	// client用户名
	private String name;

	public Send() {
		consoleBR = new BufferedReader(new InputStreamReader(System.in));
	}

	public Send(Socket socket, String name) {
		this();
		try {
			dos = new DataOutputStream(socket.getOutputStream());
			this.name = name;
			send(this.name);
		} catch (IOException e) {
			isRunning = false;
			CloseUtil.closeAll(dos, consoleBR);
		}
	}
	// 从控制台获取数据
	public String getMsgFromConsole() {
		try {
			return consoleBR.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block

		}
		return "";
	}

	public void send(String msg) {
		if ((null != msg) && !msg.equals("")) {
			try {
				dos.writeUTF(msg);
				dos.flush();
			} catch (IOException e) {
				isRunning = false;
				CloseUtil.closeAll(dos, consoleBR);
			}
		}

	}

	@Override
	public void run() {
		while (isRunning) {
			send(getMsgFromConsole());
		}
	}

}
