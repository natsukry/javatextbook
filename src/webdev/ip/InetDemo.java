package webdev.ip;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetDemo {
	public static void main(String[] args) throws UnknownHostException {
		InetAddress address = InetAddress.getByName("www.163.com");
		System.out.println(address.getHostName());
		System.out.println(address.getHostAddress());
		
		
	}
	
	
}
