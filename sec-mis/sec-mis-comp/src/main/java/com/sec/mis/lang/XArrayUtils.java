package com.sec.mis.lang;

import java.util.List;

import org.apache.commons.lang.ArrayUtils;

public class XArrayUtils extends ArrayUtils {
	/**
	 * 将String数组装换成Long数组
	 * 
	 * @param array
	 * @return
	 */
	public static Long[] toLongArray(String[] array) {
		int size = array.length;
		Long[] longArray = new Long[size];
		for (int i = 0; i < size; i++) {
			longArray[i] = Long.parseLong(array[i]);
		}
		return longArray;
	}
	
	public static boolean isEmpty(List<?> list){
		return list==null || list.size()==0;
	}
	
	public static boolean isNotEmpty(List<?> list){
		return !isEmpty(list);
	}

}
