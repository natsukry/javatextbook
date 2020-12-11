package webdev.server.demo01;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * 响应请求
 * 
 * @authorJason
 *
 */
public class Server3 {
	private final String BLANK=" "; 
	private final String CRLF="\r\n"; 
	ServerSocket server;

	public static void main(String[] args) throws IOException {
		Server3 server = new Server3();
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
					+ " HELLO   \n" + "</body>\n" + "</html>");
			StringBuilder responseBuilder = new StringBuilder();
			// 1. http 版本 状态码 描述
			responseBuilder.append("HTTP/1.1").append(BLANK).append("200").append("OK").append(CRLF);
			// 2.响应头
			responseBuilder.append("Server: nginx/0.6.39").append(CRLF);

			responseBuilder.append("Date:").append(new Date()).append(CRLF);

			responseBuilder.append("Content-Type:text/html;charset=GBK").append(CRLF);;
			// 正文长度：以字节为单位
			responseBuilder.append("Content-Length:").append(responseContext.toString().getBytes().length).append(CRLF);;
			// 3.空行
			responseBuilder.append(CRLF);
			// 4.正文
			responseBuilder.append(responseContext).append(CRLF);

			// 输出
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			bw.write(responseBuilder.toString());
			bw.flush();
			bw.close();
			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
