package com.sec.mis.util;

import java.security.MessageDigest;

import org.apache.log4j.Logger;
/**
 * @author wuyou
 *
 */
public class Md5Utils {
 
	protected static Logger logger = Logger.getLogger(Md5Utils.class.getName());

 
	/**
	 * MD5
	 * 
	 * @param source
	 * @return
	 */
	public static String md5(String source) {
		StringBuffer sb = new StringBuffer(32);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] array = md.digest(source.getBytes("utf-8"));

			for (int i = 0; i < array.length; i++) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
						.toUpperCase().substring(1, 3));
			}
		} catch (Exception e) {
			return null;
		}
		return sb.toString();
	}
}
