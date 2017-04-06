package com.sec.mis.poi;

import java.util.Arrays;
import java.util.List;

/**
 * ExcellSheet
 * @author BYX
 *
 */
public class ExcelSheet {
	public static int DEFAULT_TITLEROWNUM = 0;	//默认为第1行显示标题
	public static int DEFAULT_CONTENTROWNUM = 1;	//默认为第2行显示数据
	//public static int DEFAULT_COLUMNWIDTH = 5000;	/
	private String sheetName;	//sheetName
	private List<String> titleList;	//标题内容
	private List<List<String>> contentList;	//数据内容
	private int columnCount ;	//列的个数要求及实际列数
	private int recordCountLimit;	//数据行的最大限制
	private int recordCount;	//实际的数据行个数，不包括标题 
	private int titleRowNum;	//title所在的行，从0开始
	private int contentRowNum;	//内容开始的行，从0开始
	private int[] columnWidth;	//列的宽度
	
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public List<String> getTitleList() {
		return titleList;
	}
	public void setTitleList(List<String> titleList) {
		this.titleList = titleList;
	}
	public List<List<String>> getContentList() {
		return contentList;
	}
	public void setContentList(List<List<String>> contentList) {
		this.contentList = contentList;
	}
	public int getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}
	public int getTitleRowNum() {
		return titleRowNum;
	}
	public void setTitleRowNum(int titleRowNum) {
		this.titleRowNum = titleRowNum;
	}
	public int getContentRowNum() {
		return contentRowNum;
	}
	public void setContentRowNum(int contentRowNum) {
		this.contentRowNum = contentRowNum;
	}
	public int getColumnCount() {
		return columnCount;
	}
	public void setColumnCount(int columnCount) {
		this.columnCount = columnCount;
	}
	public int getRecordCountLimit() {
		return recordCountLimit;
	}
	public void setRecordCountLimit(int recordCountLimit) {
		this.recordCountLimit = recordCountLimit;
	}
	public int[] getColumnWidth() {
		return columnWidth;
	}
	public void setColumnWidth(int[] columnWidth) {
		this.columnWidth = columnWidth;
	}
	
	@Override
	public String toString() {
		return "ExcelSheet [sheetName=" + sheetName + ", titleList="
				+ titleList + ", contentList=" + contentList + ", columnCount="
				+ columnCount + ", recordCountLimit=" + recordCountLimit
				+ ", recordCount=" + recordCount + ", titleRowNum="
				+ titleRowNum + ", contentRowNum=" + contentRowNum
				+ ", columnWidth=" + Arrays.toString(columnWidth) + "]";
	}
	
}
