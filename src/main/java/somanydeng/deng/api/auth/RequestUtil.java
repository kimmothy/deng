package somanydeng.deng.api.auth;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;


public class RequestUtil {
	
	public void printHttpHeaderInfo(HttpServletRequest request) {
		//###request.getHeaders("Authorization: Bearer")
		Map<String, String> map = new HashMap<String, String>();
		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
		    String key = (String) headerNames.nextElement();
		    String value = request.getHeader(key);
		    map.put(key, value);
		    System.out.println(key + "," + value);
		}
	}
	
	
	public HttpURLConnection conn(String url, String method) {
		HttpURLConnection conn = null;
		try { 
			URL obj = new URL(url);
		    conn = (HttpURLConnection) obj.openConnection();
		    conn.setRequestMethod( method );	//get으로 요청갈래 post로 갈래? method로 받아봐야
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("charset", "utf-8");
			conn.setDoOutput( true );
			conn.setInstanceFollowRedirects( false );
			conn.setUseCaches( false );
		} catch (IOException e) {
			e.printStackTrace();
		}
		return conn;
	}
	
	
	public void setHttpHeader(HttpURLConnection conn,String key, String value) {
		conn.setRequestProperty(key, value);
		//conn.setRequestProperty("Authorization", "Bearer " + access_Token);	
	}
	
	
	public String sendGet(HttpURLConnection conn) {
		StringBuffer buffer = new StringBuffer();
		BufferedReader br = null;
		try { 
		    br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String inputLine;
		    while ((inputLine = br.readLine()) != null) {
		    	buffer.append(inputLine);
		    }
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return buffer.toString();
	}
	
	public String sendPost(HttpURLConnection conn, HashMap<String, Object> paramMap) {
		StringBuffer buffer = new StringBuffer();
		BufferedReader br = null;
		
		try { 
			
			//POST :  param을 body에 담아 보낸다. ---------------
			StringBuffer paramBuffer = new StringBuffer();
			for (Map.Entry<String,Object> map : paramMap.entrySet()) {
	            if (paramBuffer.length() != 0) paramBuffer.append('&');
	            paramBuffer.append(map.getKey());
	            paramBuffer.append('=');
	            paramBuffer.append(String.valueOf(map.getValue()));
	        }
			
			System.out.println(paramBuffer.toString());
			byte[] postDataBytes = paramBuffer.toString().getBytes("UTF-8");
	        conn.setRequestProperty( "Content-Length", Integer.toString( postDataBytes.length ));
			try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
			   wr.write( postDataBytes );
			}
			
			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		    String inputLine;
		    while ((inputLine = br.readLine()) != null) {
		    	buffer.append(inputLine);
		    }
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public  HashMap<String, Object> sendGetReturnMap(HttpURLConnection conn) {
		StringBuffer buffer = new StringBuffer();
		BufferedReader br = null;
		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		Gson gson = new Gson();
		try {


			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				buffer.append(inputLine);
			}

			responseMap = gson.fromJson(buffer.toString(), responseMap.getClass());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return responseMap;
	}


	public HashMap<String, Object> sendPostReturnMap(HttpURLConnection conn, HashMap<String, Object> paramMap) {
		StringBuffer buffer = new StringBuffer();
		BufferedReader br = null;
		HashMap<String, Object> responseMap = new HashMap<String, Object>();
		Gson gson = new Gson();

		try {

			//POST :  param을 body에 담아 보낸다. ---------------
			StringBuffer paramBuffer = new StringBuffer();
			for (Map.Entry<String,Object> map : paramMap.entrySet()) {
				if (paramBuffer.length() != 0) paramBuffer.append('&');
				paramBuffer.append(map.getKey());
				paramBuffer.append('=');
				paramBuffer.append(String.valueOf(map.getValue()));
			}
			//System.out.println(paramBuffer.toString());
			byte[] postDataBytes = paramBuffer.toString().getBytes("UTF-8");
			conn.setRequestProperty( "Content-Length", Integer.toString( postDataBytes.length ));
			try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
				wr.write( postDataBytes );
			}

			br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				buffer.append(inputLine);
			}

			responseMap = gson.fromJson(buffer.toString(), responseMap.getClass());
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(buffer.toString());
		return responseMap;
	}

	
}
