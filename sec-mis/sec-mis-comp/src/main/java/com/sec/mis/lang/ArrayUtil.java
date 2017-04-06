package com.sec.mis.lang;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayUtil {

	private ArrayUtil(){}
	
	public static <T> List<T> array2List(T[] array) {
		return Arrays.asList(array);
	}
	
	public static Map<String, Object> array2Map(Object[][] array) {
		Map<String, Object> map = new HashMap<String, Object>();
		for (Object[] d : array) {
			map.put(d[0].toString(), d[1]);
		}
		return map;
	}
	
}
