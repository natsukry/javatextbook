package webdev.server.demo01;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Date;

public class Response {
	private final String BLANK=" "; 
	private final String CRLF="\r\n"; 
	private BufferedWriter bw;
	Socket clientSocket;
	// 头信息
	StringBuilder headInfo;
	// 正文长度
	int len;
	StringBuilder responseBuilder;
	int code = 200;
	public Response() {
		len = 0;
		headInfo = new StringBuilder();
		responseBuilder = new StringBuilder();
	}
	
	public Response(Socket socket) {
		this();
		clientSocket = socket;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			code = 505;
		}
	}
	
	public void createHead(int code) {
		// 1. http 版本 状态码 描述
		headInfo.append("HTTP/1.1").append(BLANK).append(code).append(BLANK);
		switch (code) {
		case 200:
			headInfo.append("OK");
			break;
		case 404:
			headInfo.append("NOT FOUND");
			break;
		case 505:
			headInfo.append("SERVER ERROR");
			break;
		}
		headInfo.append(CRLF);
		
		// 2.响应头
		headInfo.append("Server: nginx/0.6.39").append(CRLF);

		headInfo.append("Date:").append(new Date()).append(CRLF);

		headInfo.append("Content-Type:text/html;charset=GBK").append(CRLF);;
		// 正文长度：以字节为单位
		headInfo.append("Content-Length:").append(len).append(CRLF);;
		// 3.空行
		headInfo.append(CRLF);
		responseBuilder.append(headInfo);
		
	} 
	// 加入正文
	public void getContentLen(String contentString) {
		len+=contentString.getBytes().length;
	}
	
	public void push(String contentString) throws IOException {
		getContentLen(contentString);
		createHead(code);
		responseBuilder.append(contentString);
		bw.write(responseBuilder.toString());
		bw.flush();
		bw.close();
	}
}
