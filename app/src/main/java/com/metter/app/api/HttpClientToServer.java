package com.metter.app.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpClientToServer {
	public HttpClientToServer(){
			
	}
	public String sendtogprsdoPost(String upIMEI ,String upMeterId ,String upSendCount,String urlAddress ,String upTx){
		HttpPost httpPost = new HttpPost(urlAddress);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		NameValuePair pair1 = new BasicNameValuePair("imei", upIMEI);
		NameValuePair pair2 = new BasicNameValuePair("meterId", upMeterId);		
		NameValuePair pair3 =new BasicNameValuePair("tx", upTx);
		NameValuePair pair4 =new BasicNameValuePair("sendCount", upSendCount);
		params.add(pair1);
		params.add(pair2);
		params.add(pair3);
		params.add(pair4);
		HttpEntity he;
		try {
			he = new UrlEncodedFormEntity(params, "utf8");
			httpPost.setEntity(he);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		HttpClient hc = new DefaultHttpClient();
		try {
			HttpResponse ht = hc.execute(httpPost);
			if(ht.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity het = ht.getEntity();
				InputStream is = het.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String response = "";
				String readLine = null;
				
				while((readLine =br.readLine()) != null){
					response = response + readLine;
				}
				is.close();
				br.close();
				return response;
			}else{
				return "\"-2\"";
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "\"-2\"";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "\"-2\"";
		}
	}
	public String getexedoPost(String upIMEI ,String upMeterId ,String urlAddress){
		HttpPost httpPost = new HttpPost(urlAddress);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		NameValuePair pair1 = new BasicNameValuePair("imei", upIMEI);
		NameValuePair pair2 = new BasicNameValuePair("meterId", upMeterId);		
		params.add(pair1);
		params.add(pair2);
		HttpEntity he;
		try {
			he = new UrlEncodedFormEntity(params, "utf8");
			httpPost.setEntity(he);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		HttpClient hc = new DefaultHttpClient();
		try {
			HttpResponse ht = hc.execute(httpPost);
			if(ht.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity het = ht.getEntity();
				InputStream is = het.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String response = "";
				String readLine = null;
				
				while((readLine =br.readLine()) != null){
					response = response + readLine;
				}
				is.close();
				br.close();
				return response;
			}else{
				return "\"-2\"";
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "\"-2\"";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "\"-2\"";
		}
	}
	public String getgprsdoPost(String urlAddress){
			HttpPost httpPost = new HttpPost(urlAddress);
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			HttpEntity he;
			try {
				he = new UrlEncodedFormEntity(params, "utf8");
				httpPost.setEntity(he);
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		HttpClient hc = new DefaultHttpClient();
		try {
			HttpResponse ht = hc.execute(httpPost);
			if(ht.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity het = ht.getEntity();
				InputStream is = het.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String response = "";
				String readLine = null;
				while((readLine =br.readLine()) != null){
					response = response + readLine;
				}
				is.close();
			   	br.close();
				return response;
			}else{
				return "err:getStatusLine 连接服务器失败，稍后再试！";
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "err:ClientProtocol 连接服务器失败，稍后再试！";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "err:IOException 连接服务器失败，稍后再试！";
		}
	}
	
	public String gettreedoPost(String fieldName ,String fieldValue ,String urlAddress){
		HttpPost httpPost = new HttpPost(urlAddress);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		NameValuePair pair1 = new BasicNameValuePair("fieldName", fieldName);
		NameValuePair pair2 = new BasicNameValuePair("fieldValue", fieldValue);		
		params.add(pair1);		
		params.add(pair2);
		HttpEntity he;
		try {
			he = new UrlEncodedFormEntity(params, "utf8");
			httpPost.setEntity(he);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		
		HttpClient hc = new DefaultHttpClient();
		try {
			HttpResponse ht = hc.execute(httpPost);
			if(ht.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity het = ht.getEntity();
				InputStream is = het.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String response = "";
				String readLine = null;
				
				while((readLine =br.readLine()) != null){
					//response = br.readLine();
					response = response + readLine;
				}
				is.close();
				br.close();
				return response;
			}else{
				return "err:getStatusLine 连接服务器失败，稍后再试！";
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "err:ClientProtocol 连接服务器失败，稍后再试！";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "err:IOException 连接服务器失败，稍后再试！";
		}
	}
	public String sendtogprsdoPost(String account,String fieldName ,String fieldValue ,String urlAddress){
		HttpPost httpPost = new HttpPost(urlAddress);
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		NameValuePair pair1 = new BasicNameValuePair("account", account);
		NameValuePair pair2 = new BasicNameValuePair("fieldName", fieldName);
		NameValuePair pair3 = new BasicNameValuePair("fieldValue", fieldValue);		
		params.add(pair1);		
		params.add(pair2);
		params.add(pair3);
		HttpEntity he;
		try {
			he = new UrlEncodedFormEntity(params, "utf8");
			httpPost.setEntity(he);
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
		
		
		HttpClient hc = new DefaultHttpClient();
		try {
			HttpResponse ht = hc.execute(httpPost);
			if(ht.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
				HttpEntity het = ht.getEntity();
				InputStream is = het.getContent();
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
				String response = "";
				String readLine = null;
				
				while((readLine =br.readLine()) != null){
					//response = br.readLine();
					response = response + readLine;
				}
				is.close();
				br.close();
				return response;
			}else{
				return "err:getStatusLine 连接服务器失败，稍后再试！";
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "err:ClientProtocol 连接服务器失败，稍后再试！";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "err:IOException 连接服务器失败，稍后再试！";
		}
	}
	public String getlogindoPost(String login ,String password ,String urlAddress) throws Exception{
		Map<String, String> params = new HashMap<String,String>();
    	params.put("loginName", "admin");
    	params.put("password", "admin1234");
    	String res = "";
		List<NameValuePair> pairs = new ArrayList<NameValuePair>();//存放请求参数
		if(params!=null && !params.isEmpty()){
			for(Map.Entry<String, String> entry : params.entrySet()){
				pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
		}
		UrlEncodedFormEntity entity = new UrlEncodedFormEntity(pairs, "utf-8");
		HttpPost httpPost = new HttpPost(urlAddress);
		httpPost.setEntity(entity);
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse response = client.execute(httpPost);
		if(response.getStatusLine().getStatusCode() == 200){
			HttpEntity het = response.getEntity();
			InputStream is = het.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			String readLine = null;
			
			while((readLine =br.readLine()) != null){
				res = res + readLine;
			}
			is.close();
			br.close();
			
		}
		return res;
		//HttpPost httpPost = new HttpPost(urlAddress);
//		HttpPost httpPost = new HttpPost("http://58.240.47.50:8074/EnergyManager/android/getLogin.action");
//	
//		
//		
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		NameValuePair pair1 = new BasicNameValuePair("loginName", "admin");
//		NameValuePair pair2 = new BasicNameValuePair("password", "admin1234");		
//		params.add(pair1);		
//		params.add(pair2);
//		HttpEntity he;
//		try {
//			he = new UrlEncodedFormEntity(params, "utf-8");
//			httpPost.setEntity(he);
//		} catch (UnsupportedEncodingException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		} 
//		
//		
//		HttpClient hc = new DefaultHttpClient();
//		try {
//			HttpResponse ht = hc.execute(httpPost);
//			if(ht.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
//				HttpEntity het = ht.getEntity();
//				InputStream is = het.getContent();
//				BufferedReader br = new BufferedReader(new InputStreamReader(is));
//				String response = "";
//				String readLine = null;
//				
//				while((readLine =br.readLine()) != null){
//					//response = br.readLine();
//					response = response + readLine;
//				}
//				is.close();
//				br.close();
//				return response;
//			}else{
//				return "err:getStatusLine 连接服务器失败，稍后再试！";
//			}
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "err:ClientProtocol 连接服务器失败，稍后再试！";
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "err:IOException 连接服务器失败，稍后再试！";
//		}
	}
}
