package webdev.tcp.socket;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(8888);
		while (true) {  // 一次只能处理一个
			Socket socket = server.accept();
			System.out.println("一个客户端建立连接");

			// 发送数据
			String msg = "Welcome";
			// socket.getOutputStream() 是字节流，要发送字符流需要包装

			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bw.write(msg);
			bw.newLine();
			bw.flush();
		}

	}
}
