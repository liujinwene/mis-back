package com.sec.mis.support;

import com.sec.mis.lang.StringUtils;

/**
 * 业务异常类
 * 
 * @author Administrator
 *
 */
public class ServiceException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3837659312079505063L;

	private String key;

	/**
	 * 创建异常
	 * 
	 * @param key
	 *            对应Messages.properties中key
	 * @param args
	 *            对应Messages.properties中消息模板预留参数
	 * @return
	 */
	public static ServiceException byKey(String key, Object... args) {
		return new ServiceException(key, args);
	}

	/**
	 * 创建异常
	 * 
	 * @param message
	 *            异常消息
	 * @return
	 */
	public static ServiceException byMsg(String message) {
		return new ServiceException(message);
	}

	/**
	 * 创建异常
	 * 
	 * @param cause
	 * @return
	 */
	public static ServiceException byExc(Throwable cause) {
		return new ServiceException(cause);
	}

	/**
	 * 创建异常
	 * 
	 * @param cause
	 * @param message
	 * @return
	 */
	public static ServiceException byExc(Throwable cause, String message) {
		return new ServiceException(cause, message);
	}

	/**
	 * 创建异常
	 * 
	 * @param template
	 *            异常模板，如你的用户名是{0}
	 * @param args
	 *            模板参数
	 * @return
	 */
	public static ServiceException byTpl(String template, Object... args) {
		return byMsg(StringUtils.format(template, args));
	}

	protected ServiceException() {
		super();
	}

	protected ServiceException(String message) {
		super(message);
	}

	protected ServiceException(String key, Object... args) {
		super(Messages.getMessage(key, args));
		this.key = key;
	}

	protected ServiceException(Throwable cause) {
		super(cause);
	}

	protected ServiceException(Throwable cause, String message) {
		super(message, cause);
	}

	public String getKey() {
		return key;
	}

}
