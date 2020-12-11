package webdev.tcp.chatroom.demo01;

import java.io.*;
import java.net.Socket;

// 问题：输入流和输出流在同一线程中
// 改进：输入流和输出流应该独立
public class Client {
	public static void main(String[] args) throws IOException {
		Socket socket = new Socket("localhost", 9999);
		DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
		BufferedReader consoleBR = new BufferedReader(new InputStreamReader(System.in));
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		String msg;
		while (true) {

			msg = consoleBR.readLine();

			dos.writeUTF(msg);
			dos.flush();

			String rcvMsgString = dis.readUTF();
			System.out.println(rcvMsgString);
		}

	}
}
