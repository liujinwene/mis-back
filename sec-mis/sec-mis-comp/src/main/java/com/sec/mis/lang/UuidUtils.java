package com.sec.mis.lang;

import java.util.UUID;

public class UuidUtils {
	/**
	 * 获取原始的uuid
	 * @return
	 */
	public static String getUuid(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}

	/**
	 * 去掉原始uuid中的连字符号
	 * @return
	 */
	public static String getUuidTrimHyphen(){
		UUID uuid = UUID.randomUUID();
		String result = uuid.toString().replaceAll("-", "");
		return result;
	}


	public static void main(String[] args){
		System.out.println(UuidUtils.getUuid());
		System.out.println(UuidUtils.getUuidTrimHyphen());
	}
}
