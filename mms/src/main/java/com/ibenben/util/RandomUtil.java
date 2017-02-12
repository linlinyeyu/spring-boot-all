package com.ibenben.util;

import java.util.Random;

import org.apache.shiro.crypto.hash.Md5Hash;


public class RandomUtil {
	public static String generateBarCode(String seed){
		Md5Hash hash = new Md5Hash(seed);
		String str = hash.toHex();
		String head = String.format("%06d", Long.parseLong(str.substring(9, 17),16));
		head = head.substring(head.length() - 6, head.length());
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		String tail = String.format("%06d", random.nextInt(1000000));
		return head + tail;
	}
	
	public static String generateFileName(String seed){
		Md5Hash hash = new Md5Hash(seed);
		String str = hash.toHex();
		String head = str.substring(9, 17);
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		String tail = String.format("%06d", random.nextInt(1000000));
		return head + tail;
	}
	
	public static String generateToken(String seed) {
		return new Md5Hash(seed).toHex();
	}
	
}
