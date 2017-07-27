package com.test;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSON;

public class Test {

	public static void main(String[] args) throws Exception {
		String first = first();
		String[] strings = RsaUtil.createKeyPairs();
		String string = second(strings, first);
		System.out.println("获得:" + string);
		System.out.println(URLEncoder.encode(string, "utf-8"));
	}

	public static String second(String[] s, String password) throws Exception {
		System.out.println("自己的公钥:" + s[0]);
		System.out.println("自己的密钥:" + s[1]);
		StringBuffer sb2 = new StringBuffer("http://182.254.219.46:8080/AntSchool/login/login?id=2015002530");
		sb2.append("&&password=").append(password).append("&&publicKey=").append(s[0]);
		URL url = new URL(sb2.toString());
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.connect();
		InputStream inputStream = connection.getInputStream();
		byte[] bytes = new byte[512];
		StringBuffer sb = new StringBuffer();
		int len = -1;
		while ((len = inputStream.read(bytes)) != -1) {
			sb.append(new String(bytes, 0, len));
		}
		Status status = JSON.parseObject(sb.toString(), Status.class);
		System.out.println(status);
		int i = status.getStatus();
		if (i == 200) {
			System.out.println("获取token");
			return RsaUtil.decryptData(status.getMessage(), s[1]);
		}
		System.out.println(i);
		return status.getMessage();
	}

	public static String first() throws Exception {
		URL url = new URL("http://182.254.219.46:8080/AntSchool/login/getPublicKey?id=2015002530");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.connect();
		InputStream inputStream = connection.getInputStream();
		byte[] bytes = new byte[512];
		StringBuffer sb = new StringBuffer();
		int len = -1;
		while ((len = inputStream.read(bytes)) != -1) {
			sb.append(new String(bytes, 0, len));
		}
		inputStream.close();
		Status status = JSON.parseObject(sb.toString(), Status.class);
		System.out.println(status);
		String message = status.getMessage();
		String data = RsaUtil.encryptData("hzkjzyjsxy", message);
		System.out.println("加密后的密码:" + data);
		return URLEncoder.encode(data, "utf-8");
	}
}
