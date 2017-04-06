package com.sec.mis.lang;

import java.text.MessageFormat;
import java.util.Random;

public class StringUtils extends org.apache.commons.lang.StringUtils {
	public static String format(String pattern, Object... args) {
		Object[] arguments = null;
		if (args != null && args.length > 0) {
			arguments = new Object[args.length];
			for (int i = 0; i < args.length; i++) {
				if (args[i] != null && (args[i] instanceof Integer || args[i] instanceof Long)) {
					arguments[i] = String.valueOf(args[i]);
				} else {
					arguments[i] = args[i];
				}
			}
		}
		return MessageFormat.format(pattern, arguments);
	}
	
	public static String subStr(String sources, String str1, String str2) {
		int position1 = sources.indexOf(str1);
		int position2 = sources.indexOf(str2);
		if (position1 < 0 || position2 < 0) {
			return sources;
		}
		return sources.substring(position1 + str1.length(), position2);
	}
	
	/**
	 * 生成随机数
	 * @param pwd_len
	 * @return
	 */
	public static String genRandomNum(int pwd_len){
		  //35是因为数组是从0开始的，26个字母
		  final int  maxNum = 26;
		  int i;  //生成的随机数
		  int count = 0; //生成的密码的长度
		  char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k',
		    'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
		    'x', 'y', 'z' };
		  
		  StringBuffer pwd = new StringBuffer("");
		  Random r = new Random();
		  while(count < pwd_len){
		   //生成随机数，取绝对值，防止生成负数，
		   
		   i = Math.abs(r.nextInt(maxNum));  //生成的数最大为36-1
		   
		   if (i >= 0 && i < str.length) {
		    pwd.append(str[i]);
		    count ++;
		   }
		  }
		  
		  return pwd.toString();
		 }
	
	/**
	 * 判断字符是否为空 ，空返回true
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str){
		if(str==null || "".equals(str.trim())){
			return true;
		}
		return false;
	}

	public static String getRandom6Number() {
		int code = 0;
		while (code <= 0) {
			code = (int) (Math.random() * 1000000);
		}

		if (code < 10) {
			code = code * 100000;
		} else if (code < 100) {
			code = code * 10000;
		} else if (code < 1000) {
			code = code * 1000;
		} else if (code < 10000) {
			code = code * 100;
		} else if (code < 100000) {
			code = code * 10;
		}

		return String.valueOf(code);
	}
	
	/**
	 * 首字母转大写
	 * @param s
	 * @return
	 */
	public static String toUpperCaseFirstOne(String s) {
		if (Character.isUpperCase(s.charAt(0))) return s;
		else return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}
}
