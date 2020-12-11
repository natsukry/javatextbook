package webdev.tcp.chatroom.demo05;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

// 问题：输入流和输出流在同一线程中
// 改进：输入流和输出流应该独立
// 加入client用户名
public class Client {
	public static void main(String[] args) throws IOException {
		System.out.println("please input your user name...");
		BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
		String name = bReader.readLine();
		
		if (name.equals("")) {
			return;
		}
		
		Socket socket = new Socket("localhost", 9999);
		new Thread(new Send(socket,name)).start();
		new Thread(new Receive(socket)).start();
				
	}
}
