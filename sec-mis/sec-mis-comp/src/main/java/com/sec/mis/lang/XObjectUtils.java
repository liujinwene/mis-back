package com.sec.mis.lang;

import org.apache.commons.lang.StringUtils;

public class XObjectUtils {
	public static boolean isEmpty(Object o) {
		if (o instanceof String) {
			return StringUtils.isEmpty((String) o);
		} else {
			return o == null;
		}
	}

	public static boolean isNotEmpty(Object o) {
		return !isEmpty(o);
	}
	
	public static boolean isIntegerEquals(Integer o1,Integer o2){
		if(o1==null || o2==null){
			return false;
		}
		return o1.equals(o2);
	}
}
