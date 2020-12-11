package webdev.server.demo01;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 响应请求
 * 
 * @authorJason
 *
 */
public class Server4 {
	private final String BLANK=" "; 
	private final String CRLF="\r\n"; 
	ServerSocket server;

	public static void main(String[] args) throws IOException {
		Server4 server = new Server4();
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

	public void stop() {
	}

	public void receive() {
		Socket socket;
		try {
			socket = server.accept();
			// 改变逐行读的方法，一次读入全部数据。
			byte[] data = new byte[20480];
			int len = socket.getInputStream().read(data);
			String requestInfoString = new String(data, 0, len).trim();

			System.out.println(requestInfoString);

			// 响应
			StringBuilder responseContext = new StringBuilder();
			responseContext.append("<!DOCTYPE html>\n" + "<html>\n" + "<head>\n" + "</head>\n" + "<body>\n"
					+ " HELLO ALOHA  WELCOME \n" + "</body>\n" + "</html>");
			Response response = new Response(socket);
			response.push(responseContext.toString());
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
