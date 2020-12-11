package webdev.tcp.chatroom.demo02;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * 问题：只能接受一个客户端
 * 改进：加入多线程
 */
public class Server {
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(9999);
		Socket socket = server.accept();
		DataInputStream dis = new DataInputStream(socket.getInputStream());
		while (true) {
			String rcvMsgString = dis.readUTF();
			System.out.println(rcvMsgString);

			DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
			dos.writeUTF("server received: " + rcvMsgString);
			dos.flush();
		}
	}

}
