package com.example.applock.utils;

import java.security.MessageDigest;
public class MD5Utils {
	public static <bytes> String encode(String str) throws Exception{
		MessageDigest digest=MessageDigest.getInstance("MD5");
		byte[] bytes=digest.digest(str.getBytes());
		StringBuffer buffer=new StringBuffer();
		for(int i=0;i<bytes.length;i++){
			String s = Integer.toHexString(0xff&bytes[i]);		
			if(s.length()==1){
				buffer.append("0"+s);
			}else{
				buffer.append(s);
			}
		}	
		return buffer.toString();
	}
}
