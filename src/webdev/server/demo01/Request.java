package webdev.server.demo01;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Request {
	//请求方式
	private String method;
	//请求资源	
	private String url;
	//请求参数	
	private Map<String, List<String>> parameterMapValue;
	
	private final String BLANK=" "; 
	private final String CRLF="\r\n"; 
	
	private InputStream is;
	private String requestInfo;
	
//	初始化
	public Request() {
		method = "";
		url = "";
		parameterMapValue = new HashMap<String, List<String>>();
		requestInfo = "";
	}
	public Request(InputStream is) {
		this();
		this.is  = is;
		
		
		try {
			byte[] data = new byte[20480];
			int len = is.read(data);
			requestInfo = new String(data,0,len);
		} catch (IOException e) {
			return;
		}
		//分析头信息
		parseRequestInfo();
		
	}
	//分析请求信息，分解字符串
	private void parseRequestInfo() {
		if (requestInfo == null ||(requestInfo=requestInfo.trim()).equalsIgnoreCase("")) {
			return;
		}
		/**
		 * 从信息的首行分解出：请求方式 请求路径 请求参数
		 * GET /index.html?name=123 HTTP/1.1
		 * 
		 * 如果为post方式，请求参数在正文最后
		 * POST /index.html HTTP/1.1
		 */
		String paramString = new String();
		// 1.请求方式
		String firstLine = requestInfo.substring(0,requestInfo.indexOf(CRLF));
		this.method = firstLine.substring(0,firstLine.indexOf("/")).trim();
		String urlString = firstLine.substring(firstLine.indexOf("/")+1,firstLine.indexOf("HTTP/")).trim();
		if (this.method.equalsIgnoreCase("post")) {
			this.url = urlString;
//			如果为post方式，请求参数在正文最后
			paramString = requestInfo.substring(requestInfo.lastIndexOf(CRLF)).trim();
			
		}
		else if (this.method.equalsIgnoreCase("get")) {
			if (urlString.contains("?")) { // 存在参数
				// index.html?name=123&password=123 HTTP/1.1
				String[] urlArray = urlString.split("\\?");
				this.url = urlArray[0];
				paramString = urlArray[1];
			}else {
				this.url = urlString;
			}
		}
		// 2 请求方式封装到map
	}
}
