package webdev.tcp.chatroom.demo01;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

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
