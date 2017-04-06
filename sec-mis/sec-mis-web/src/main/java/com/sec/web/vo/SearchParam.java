package com.sec.web.vo;

import java.util.HashMap;
import java.util.Map;

public class SearchParam {
	
	private int start = 0;
	
	private int limit = 15;
	
	private Map<String, Object> sp = new HashMap<String, Object>();

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Map<String, Object> getSp() {
		return sp;
	}

	public void setSp(Map<String, Object> sp) {
		this.sp = sp;
	}

	@Override
	public String toString() {
		return "SearchParam [start=" + start + ", limit=" + limit + ", sp="
				+ sp + "]";
	}
	
/*	public int getInt(String key){
		Object value = sp.get(key);
		if(value!=null && value.toString().matches("^(0|[1-9][0-9]*)$")){
			return Integer.parseInt(value.toString());
		}
		return -1;
	}
	
	public boolean getBoolean(String key){
		Object value = sp.get(key);
		if(value!=null){
			value = value.trim().toLowerCase();
			if(value.equals("0") || value.equals("false")){
				return false;
			}else if(value.equals("1") || value.equals("true")){
				return true;
			}
		}
		return false;
	}*/
	
	
	
}
