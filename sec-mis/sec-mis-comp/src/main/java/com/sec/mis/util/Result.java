package com.sec.mis.util;

/**
 * 用于返回操作信息
 * 
 * @author Administrator
 *
 */
public class Result {
	/**结果为空*/
    public static final String RESULTCODE_DATA_NULL="-1";
    /**成功*/
    public static final String RESULTCODE_SUCCESS="00";
	
	private boolean success;
	private String message;
	private Object object;
	private String resultCode;
	
	
	public Result(boolean success, String message, Object object,String resultCode) {
		super();
		this.success = success;
		this.message = message;
		this.object = object;
		this.resultCode=resultCode;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public Result() {

	}

	public Result(boolean success) {
		this.success = success;
	}

	public Result(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public static Result error(String message) {
		return new Result(false, message);
	}

	public static Result success() {
		return new Result(true, null);
	}

	public boolean getSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public String getResultCode() {
		return resultCode;
	}


	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	
}
