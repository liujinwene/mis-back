package com.sec.mis.page;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;

import com.sec.mis.lang.DateUtils;
import com.sec.mis.lang.StringUtils;

public class QueryArgs {

	private int pageNo = Page.PAGE_1ST;
	private int pageSize = Page.PAGE_SIZE;
	private String order;

	public static final String[] PATTERNS = { "yyyy-MM-dd" };

	private Map<String, String> args = new HashMap<String, String>();

	private Map<String, Object> daoArgs = new HashMap<String, Object>();

	public void setArgs(Map<String, String> args) {
		this.args = args;
	}

	public Map<String, String> getArgs() {
		return args;
	}

	public Map<String, Object> get() {
		Map<String, Object> query = new HashMap<String, Object>(daoArgs);
		query.put("start", getStart());
		query.put("limit", getLimit());
		query.put("order", order);
		return query;
	}

	public QueryArgs put(String key, Object value) {
		init() ;
		daoArgs.put(key, value);
		return this;
	}
	public QueryArgs remove(String key) {
		init();
		daoArgs.remove(key);
		return this;
	}


	private void init() {
		if (daoArgs.isEmpty()) daoArgs.putAll(args);
	}

	private Date getDate(Map<String, Object> args, String key) {
		Object val = args.get(key);
		if (val == null) return null;
		Date date = null;
		if (val instanceof String && StringUtils.isNotEmpty((String) val)) {
			date = DateUtils.parseDate(MapUtils.getString(daoArgs, key), PATTERNS);
		}else if(val instanceof Date) {
			date = (Date)val;
		}
		return date;
	}

	public QueryArgs beginDay(String key) {
		init();
		Date date = getDate(daoArgs, key);
		if (date != null) {
			date = DateUtils.truncateToDay(date);
		}
		daoArgs.put(key, date);
		return this;
	}

	public QueryArgs endDay(String key) {
		init();
		Date date = getDate(daoArgs, key);
		if (date != null) {
			date = DateUtils.truncateToDay(date);
			date = DateUtils.addDay(date, 1);
		}
		daoArgs.put(key, date);
		return this;
	}
	
	/**
	 * 查询最近一周数据
	 * @param sdatekey
	 * @param edatekey
	 * @return
	 */
	public QueryArgs lastestWeek(String sdatekey, String edatekey){
		init();
		daoArgs.put(sdatekey, DateUtils.getSunday(new Date())); //周日
		daoArgs.put(edatekey, DateUtils.getSaturday(new Date())); //周六
		return this ;
	}
	
	/**
	 * 查询最近一个月数据
	 * @param sdatekey
	 * @param edatekey
	 * @return
	 */
	public QueryArgs lastestMonth(String sdatekey, String edatekey){
		init();
		daoArgs.put(sdatekey, DateUtils.getMonthStart(new Date())); //当月初
		daoArgs.put(edatekey, DateUtils.getMonthEnd(new Date())); //当月末
		return this ;
	}
	
	/**
	 * 查询最近三个月数据
	 * @param sdatekey
	 * @param edatekey
	 * @return
	 */
	public QueryArgs lastest3Month(String sdatekey, String edatekey){
		init();
		daoArgs.put(sdatekey, DateUtils.getMonthStart(new Date())); //当月月初
		daoArgs.put(edatekey, DateUtils.addMonth(DateUtils.getMonthEnd(new Date()), 2)); //三个月月末
		return this ;
	}
	
	/**
	 * 初始化 带间隔的查询 前端格式: 最小值-最大值 返回 参数名_s - 参数名_e
	 * 
	 * @param key
	 * @param remove TODO
	 * @return
	 */
	public QueryArgs initIntervalQuery(String key, boolean remove) {
		String value = getString(key);

		if (StringUtils.isBlank(value) || value.indexOf("-") <= 0) return this;
		String[] array = value.split("-");

		daoArgs.put(key + "_s", array[0]);
		daoArgs.put(key + "_e", array[1]);

		if (remove) args.remove(key);

		return this;
	}

	public Object get(String key) {
		return args.get(key);
	}

	public Integer getInteger(String key) {
		return MapUtils.getInteger(args, key);
	}
	
	public Integer getIntegerDefault(String key, Integer defaultValue) {
		return MapUtils.getInteger(args, key, defaultValue);
	}

	public String getString(String key) {
		return MapUtils.getString(args, key);
	}

	public int getFirstPageNo() {
		return Page.PAGE_1ST;
	}

	public int getStart() {
		return (getPageNo() - getFirstPageNo()) * getPageSize();
	}

	public int getLimit() {
		return getPageSize();
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
