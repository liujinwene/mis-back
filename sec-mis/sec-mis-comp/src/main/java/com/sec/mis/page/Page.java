package com.sec.mis.page;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Page<T> {

	private static Properties properties = new Properties();
	static {
		InputStream input = null;
		try {
			input = Page.class.getResourceAsStream("/page.properties");
			properties.load(input);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
				}
			}
		}
	}

	/**
	 * 默认第一页页码
	 */
	public static final int PAGE_1ST = Integer.parseInt(properties.getProperty("page.PAGE_1ST"));// 第一页，从0开始
	/**
	 * 默认每页记录数
	 */
	public static final int PAGE_SIZE = Integer.parseInt(properties.getProperty("page.PAGE_SIZE"));// 默认一页记录数
	/**
	 * 从第一条记录开始查询
	 */
	public static final int START = Integer.parseInt(properties.getProperty("page.START"));
	/**
	 * 查询所有记录
	 */
	public static final int ALL = -1;

	private List<T> items = Collections.emptyList();// 当前页记录
	private int pageNo = PAGE_1ST;// 当前页码
	private int pageSize = PAGE_SIZE;// 每页记录数
	private int total = 0;// 记录总条数
	private PageIndex pageindex ; //页码开始索引和结束索引

	public static <T> Page<T> empty() {
		Page<T> page = new Page<T>();
		return page;
	}

	public static <T> Page<T> create(List<T> items, int total) {
		Page<T> page = new Page<T>();
		page.setItems(items);
		page.setTotal(total);
		return page;
	}

	/**
	 * 创建分页
	 * 
	 * @param items
	 *            当前页记录
	 * @param total
	 *            总记录数
	 * @param pageNo
	 *            当前页码
	 * @param pageSize
	 *            一页记录数
	 * 
	 * @return
	 */
	public static <T> Page<T> create(List<T> items, int total, int pageNo, int pageSize) {
		Page<T> page = new Page<T>();
		page.setItems(items);
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);
		page.setTotal(total);
		return page;
	}

	@Deprecated
	public static <T> Page<T> create2(List<T> items, int start, int limit, int total) {
		Page<T> page = new Page<T>();
		page.setItems(items);
		page.setPageNo(PAGE_1ST + start / limit);
		page.setPageSize(limit);
		page.setTotal(total);
		return page;
	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> items) {
		this.items = items;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
		this.pageindex = PageIndex.getPageIndex(5, pageNo+1, getPageCount());
	}

	/**
	 * 获取共有多少页
	 * 
	 * @return
	 */
	public int getPageCount() {
		int pageCount = this.total / this.pageSize;
		if (this.total % this.pageSize > 0) {
			pageCount++;
		}
		return pageCount;
	}

	/**
	 * 获取第一页页码
	 * 
	 * @return
	 */
	public int getFirstPageNo() {
		return PAGE_1ST;
	}

	/**
	 * 获取最后一页页码
	 * 
	 * @return
	 */
	public int getLastPageNo() {
		return this.getPageCount() + PAGE_1ST - 1;
	}

	/**
	 * 获取当前页记录条数
	 * 
	 * @return
	 */
	public int getItemSize() {
		return this.items.size();
	}

	public int getStart() {
		return (this.getPageNo() - this.getFirstPageNo()) * this.getPageSize() - START;
	}
	
	public int getLimit() {
		return this.getPageSize();
	}
	
	public PageIndex getPageindex() {
		return pageindex;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("pageNo:").append(getPageNo()).append(",");
		builder.append("pageSize:").append(getPageSize()).append(",");
		builder.append("totalCount:").append(getTotal()).append(",");
		builder.append("itemSize:").append(getItemSize()).append(",");
		builder.append("items:").append(getItems());
		return builder.toString();
	}

}
