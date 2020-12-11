package webdev.ip;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class InetSocketDemo {
	public static void main(String[] args) {
		InetSocketAddress address = new InetSocketAddress("127.0.0.1",8080);
		System.out.println(address.getHostName());
		System.out.println(address.getPort());
		
		InetAddress inetAddress = address.getAddress();
		System.out.println(inetAddress.getHostName());
		System.out.println(inetAddress.getHostAddress());
	}
}
