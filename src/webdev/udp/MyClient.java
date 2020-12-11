package webdev.udp;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class MyClient {
	public static void main(String[] args) throws IOException {
		DatagramSocket client = new DatagramSocket(6666);
//		byte[] data = "Hello Java, Hello ".getBytes();
		byte[] data = convert(68.19);
		DatagramPacket packet = new DatagramPacket(data,data.length,new InetSocketAddress("localhost",8888));
		client.send(packet);
		client.close();
	}
	
	// double -> byte[]
	public static byte[] convert(double num) throws IOException {
		byte[] data = null;
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(bos);
		dos.writeDouble(num);
		dos.flush();
		data = bos.toByteArray();
		dos.close();
		return data;
	}
}
