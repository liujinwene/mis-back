package com.sec.mis.mybatis.page;

import java.util.List;
import java.util.Map;

import com.sec.mis.mybatis.PageDao;
import com.sec.mis.page.Page;

public class PageUtils {
	public static <T> Page<T> list(final PageDao<T> dao, Map<String, Object> query, int pageNo, int pageSize, String order) {
		return new PageTemplate<T>() {
			@Override
			protected int count(Map<String, Object> query) {
				return dao.count(query);
			}

			@Override
			protected List<T> list(Map<String, Object> query, int start, int limit, String order) {
				return dao.list(queryArgs(query, start, limit, order));
			}
		}.page(query, pageNo, pageSize, order);
	}

	public static <T> Page<T> list(final PageDao<T> dao, Map<String, Object> query, int pageNo, int pageSize) {
		return list(dao, query, pageNo, pageSize, null);
	}
}
