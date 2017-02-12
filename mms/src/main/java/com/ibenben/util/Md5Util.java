package com.ibenben.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Md5Util {
	public static String md5(String source,int bit) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(source.getBytes());
		byte b[] = md.digest();
		int i;
		StringBuffer buf = new StringBuffer("");
		for(int offset=0;offset<b.length;offset++){
			i=b[offset];
			if(i<0)
				i+=256;
			if(i<16)
				buf.append("0");
			buf.append(Integer.toHexString(i));
		}
		if(bit == 16){
			return buf.substring(8, 24);
		}
		if(bit == 32){
			return buf.toString();
		}
		return null;
	}
	
	
	public static byte[] md5Orgin(String source) throws NoSuchAlgorithmException{
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(source.getBytes());
		return md.digest();
	}
	
	public static String Base64Md5(String source) throws NoSuchAlgorithmException{
		byte b[] = md5Orgin(source);
		return Base64.getEncoder().encodeToString(b);
	}
	
}
