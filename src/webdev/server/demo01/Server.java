package webdev.server.demo01;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	ServerSocket server;
	public static void main(String[] args) throws IOException {
		Server server = new Server();
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
	
	public void stop() {}
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
			socket=server.accept();
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			StringBuilder sb = new StringBuilder();
			String msg = null;
			while((msg = br.readLine()).length()>0) {
				sb.append(msg);
				sb.append("\r\n");
				if (msg==null) {
					break;
				}
			}
			
			String requestInfoString = sb.toString().trim();
			System.out.println(requestInfoString);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		

		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
