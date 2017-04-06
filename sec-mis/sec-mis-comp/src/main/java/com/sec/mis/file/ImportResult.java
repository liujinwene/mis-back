package com.sec.mis.file;

import java.util.List;
import java.util.Map;


/**
 * 文件导入
 * @author BYX
 * @param <T>
 *
 */
public class ImportResult<T> {
	private boolean success;	//导入是否成功，所有数据成功时才算成功，只要存在失败就是失败
	private String message;		//提示信息
	private String filePath;	//失败时，提供下载的url
	private int successCount;	//导入成功的个数
	private int errorCount;		//导入失败的个数
	private Object object;		//用来保存其它信息
	private List<T> dataList;	//用户保存结果	
	private Map<String,Object> map; //用户保存其它参数
	
	public ImportResult() {
		
	}

	public ImportResult(boolean success, String message, String filePath,int successCount, int errorCount) {
		super();
		this.success = success;
		this.message = message;
		this.filePath = filePath;
		this.successCount = successCount;
		this.errorCount = errorCount;
	}
	
	public boolean isSuccess() {
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
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(int successCount) {
		this.successCount = successCount;
	}
	public int getErrorCount() {
		return errorCount;
	}
	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}
	
	
	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}


	public Map<String, Object> getMap() {
		return map;
	}

	public void setMap(Map<String, Object> map) {
		this.map = map;
	}

 
	
	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	@Override
	public String toString() {
		return "ImportResult [success=" + success + ", message=" + message
				+ ", filePath=" + filePath + ", successCount=" + successCount
				+ ", errorCount=" + errorCount + ", object=" + object
				+ ", dataList=" + dataList + ", map=" + map + "]";
	}
	
	
	
	
}
