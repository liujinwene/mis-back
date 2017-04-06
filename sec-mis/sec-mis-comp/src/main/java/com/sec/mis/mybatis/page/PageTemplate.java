package com.sec.mis.mybatis.page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sec.mis.page.Page;

public abstract class PageTemplate<T> {
	protected abstract int count(Map<String, Object> query);

	protected abstract List<T> list(Map<String, Object> query, int start, int limit, String order);
	
	protected Map<String,Object> queryArgs(Map<String, Object> query, int start, int limit, String order) {
		Map<String, Object> _query = new HashMap<String, Object>(query);
		// FIXME: 分页，排序
		_query.put("start", start);
		_query.put("limit", limit);
		_query.put("order", order);
		return _query;
	}

	public Page<T> page(Map<String, Object> query, int pageNo, int pageSize, String order) {
		Page<T> page = Page.empty();
		int total = count(query);
		if (total > 0) {
			int start = pageSize == -1 ? 0 : (pageNo - Page.PAGE_1ST) * pageSize;
			if (start >= total) throw new IllegalArgumentException();
			int limit = pageSize;
			List<T> items = list(query, start, limit, order);
			afterList(items, query);
			page = Page.create(items, total, pageNo, pageSize);
		}
		return page;

	}

	protected void afterList(List<T> items, Map<String, Object> query) {

	}
}
