package webdev.tcp.chatroom.demo02;

import java.io.IOException;
import java.net.Socket;

// 问题：输入流和输出流在同一线程中
// 改进：输入流和输出流应该独立
public class Client {
	public static void main(String[] args) throws IOException {
		Socket socket = new Socket("localhost", 9999);
		new Thread(new Receive(socket)).start();
		new Thread(new Send(socket)).start();		
	}
}
