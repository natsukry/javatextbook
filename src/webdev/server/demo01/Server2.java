package webdev.server.demo01;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server2 {
	ServerSocket server;

	public static void main(String[] args) throws IOException {
		Server2 server = new Server2();
		server.start();
	}

	public void start() {
		try {
			server = new ServerSocket(9999);
			receive();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void stop() {
	}

	/**
	 * why it does'nt work
	 */
	public void receiveVer2() {
		Socket socket;
		try {
			socket = server.accept();
			DataInputStream dis = new DataInputStream(socket.getInputStream());
			String rcvMsgString = dis.readUTF();
			System.out.println(rcvMsgString);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void receive() {
		Socket socket;
		try {
			socket = server.accept();
			// 改变逐行读的方法，一次读入全部数据。
			byte[] data = new byte[20480];
			int len = socket.getInputStream().read(data);
			String requestInfoString = new String(data, 0, len).trim();

			System.out.println(requestInfoString);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
