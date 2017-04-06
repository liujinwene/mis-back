package com.sec.mis.poi;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * excel 实体类
 * @author BYX
 *
 */
public class ExcelEntity {
	private String fileName;	//文件名
	private String filePathAndName;	//文件路径和文件名
	private InputStream inputStream;	//输入流
	private int sheetCount;		//sheet的个数
	private List<ExcelSheet> sheetList;	//sheet列表
	private Map<Integer,Integer> titleRowMap;	//sheet中标题所在的行号，key是sheet的索引号，从0开始，默认值是0，即第1行为标题
	private Map<Integer,Integer> contentRowMap;	//sheet中内容所在的行号，key是sheet的索引号，从0开始，默认值是1，即第2行开始为数据内容
	private Map<String,Object> param;		//参数
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePathAndName() {
		return filePathAndName;
	}
	public void setFilePathAndName(String filePathAndName) {
		this.filePathAndName = filePathAndName;
	}
	public int getSheetCount() {
		return sheetCount;
	}
	public void setSheetCount(int sheetCount) {
		this.sheetCount = sheetCount;
	}
	public List<ExcelSheet> getSheetList() {
		return sheetList;
	}
	public void setSheetList(List<ExcelSheet> sheetList) {
		this.sheetList = sheetList;
	}

	public Map<Integer, Integer> getTitleRowMap() {
		return titleRowMap;
	}
	public void setTitleRowMap(Map<Integer, Integer> titleRowMap) {
		this.titleRowMap = titleRowMap;
	}
	public Map<Integer, Integer> getContentRowMap() {
		return contentRowMap;
	}
	public void setContentRowMap(Map<Integer, Integer> contentRowMap) {
		this.contentRowMap = contentRowMap;
	}
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public Map<String, Object> getParam() {
		return param;
	}
	public void setParam(Map<String, Object> param) {
		this.param = param;
	}
	@Override
	public String toString() {
		return "ExcelEntity [fileName=" + fileName + ", filePathAndName="
				+ filePathAndName + ", inputStream=" + inputStream
				+ ", sheetCount=" + sheetCount + ", sheetList=" + sheetList
				+ ", titleRowMap=" + titleRowMap + ", contentRowMap="
				+ contentRowMap + ", param=" + param + "]";
	}
	
	public  void initExcelEntity(){
		Map titleRowMap = new HashMap();
	    titleRowMap.put(0, 0);
	    this.setTitleRowMap(titleRowMap);
	    
	    Map contentRowMap = new HashMap();
	    contentRowMap.put(0, 1);
	    this.setContentRowMap(contentRowMap);
	}
}
