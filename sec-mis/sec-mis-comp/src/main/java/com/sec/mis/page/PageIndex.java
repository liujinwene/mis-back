package com.sec.mis.page;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于分页控件页码显示
 * @author Firefly
 * 2015年7月13日
 */
public class PageIndex {
	private long startindex;
	private long endindex;
	private Object[] arry ; //用于页面上循环页码的数组
	
	public PageIndex(long startindex, long endindex) {
		this.startindex = startindex;
		this.endindex = endindex;
		List<Long> list = new ArrayList<Long>() ;
		for(long i=startindex;i<=endindex;i++){
			list.add(i) ;
		}
		arry = new Object[list.size()] ;
		arry = list.toArray() ;
	}
	public long getStartindex() {
		return startindex;
	}
	public void setStartindex(long startindex) {
		this.startindex = startindex;
	}
	public long getEndindex() {
		return endindex;
	}
	public void setEndindex(long endindex) {
		this.endindex = endindex;
	}
	public Object[] getArry() {
		return arry;
	}
	public void setArry(Object[] arry) {
		this.arry = arry;
	}
	
	public static PageIndex getPageIndex(long viewpagecount, int currentPage, long totalpage){
			long startpage = currentPage-(viewpagecount%2==0? viewpagecount/2-1 : viewpagecount/2);
			long endpage = currentPage+viewpagecount/2;
			if(startpage<1){
				startpage = 1;
				if(totalpage>=viewpagecount) endpage = viewpagecount;
				else endpage = totalpage;
			}
			if(endpage>totalpage){
				endpage = totalpage;
				if((endpage-viewpagecount)>0) startpage = endpage-viewpagecount+1;
				else startpage = 1;
			}
			return new PageIndex(startpage, endpage);		
	}
}