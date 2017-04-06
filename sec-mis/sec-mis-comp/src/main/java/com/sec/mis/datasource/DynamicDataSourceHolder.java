package com.sec.mis.datasource;

public class DynamicDataSourceHolder {
	private static ThreadLocal<Object> keys = new ThreadLocal<Object>();

	public static <T> void set(T key) {
		keys.set(key);
	}

	@SuppressWarnings("unchecked")
	public static <T> T get() {
		return (T) keys.get();
	}
	
	public static void clear() {
		keys.set(null);
	}

}
