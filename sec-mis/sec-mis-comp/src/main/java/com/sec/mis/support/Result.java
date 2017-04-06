package com.sec.mis.support;

import com.sec.mis.lang.StringUtils;

/**
 * 用于返回操作信息
 * 
 * @author Administrator
 *
 */
public class Result {

	public static final String SUCCESS = "success";
	public static final String ERROR = "error";

	private String key;
	private String message;
	private Object relationKey;

	private Result(String key, String message) {
		this.key = key;
		this.message = message;
	}

	public static Result byKey(String key, Object... args) {
		String message = Messages.getMessage(key, args);
		return new Result(key, message);
	}

	public static Result byMsg(String key, String message) {
		return new Result(key, message);
	}

	public static Result byTpl(String key, String pattern, Object... args) {
		String message = StringUtils.format(pattern, args);
		return byMsg(key, message);
	}

	public static Result byExc(ServiceException e) {
		String key = e.getKey();
		key = (e.getKey() == null || e.getKey().trim().length() == 0) ? ERROR: key;
		return new Result(key, e.getMessage());
	}

	public static Result success() {
		return new Result(SUCCESS, "");
	}
	
	public static Result bySuccess(String message) {
		return new Result(SUCCESS, message);
	}

	public static Result byMsg(String message) {
		return byMsg(ERROR, message);
	}

	public boolean isSuccess() {
		return SUCCESS.equals(key);
	}

	public String getMessage() {
		return message;
	}

	public String getKey() {
		return key;
	}

	public Object getRelationKey() {
		return relationKey;
	}

	public void setRelationKey(Object relationKey) {
		this.relationKey = relationKey;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}

	
}
