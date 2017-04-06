package com.sec.mis.mybatis;

import java.util.List;
import java.util.Map;

public interface PageDao<T> {

	List<T> list(Map<String, Object> query);

	int count(Map<String, Object> query);

}
