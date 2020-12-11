package webdev.udp;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class MyServer {
	public static void main(String[] args) throws IOException {
		DatagramSocket server = new DatagramSocket(8888);
		byte[] container = new byte[1024];
		DatagramPacket packet = new DatagramPacket(container, container.length);
		server.receive(packet);
		
		// 处理数据
		double data = convert(packet.getData());
		System.out.println(data);
		server.close();
	}
	
	public static double convert(byte[] data) throws IOException{
		DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
		return dis.readDouble();
	}
	
	
}
