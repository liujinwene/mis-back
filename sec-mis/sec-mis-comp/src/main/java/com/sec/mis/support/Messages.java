package com.sec.mis.support;

import java.util.ResourceBundle;

import com.sec.mis.lang.StringUtils;

public abstract class Messages {

	private static ResourceBundle bundle = ResourceBundle.getBundle("Messages");

	public static final String getMessage(String key) {
		return bundle.getString(String.valueOf(key));
	}

	public static final String getMessage(String key, Object... args) {
		String message = getMessage(key);
		return StringUtils.format(message, args);
	}

}
