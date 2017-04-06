package com.sec.mis.service.impl;

import java.util.List;
import java.util.Map;

import com.sec.mis.mybatis.BaseDao;
import com.sec.mis.service.BaseService;

/***
 * 该类仅仅只针对于普通实体类的CRUD
 * 不推荐其它service的基础父类
 * @author jiangqi
 *
 * @param <T>
 */
public abstract class BaseServiceImpl<T> implements BaseService<T> {

	public abstract BaseDao<T> getDao();
	@Override
	public List<T> list(Map<String, Object> query) {
		return getDao().list(query);
	}

	@Override
	public int count(Map<String, Object> query) {
		return (Integer)getDao().count(query);
	}

	@Override
	public T get(long id) {
		return (T)getDao().get(id);
	}
	@Override
	public int create(T model) {
		return getDao().create(model);
	}
	@Override
	public int delete(long id) {
		return getDao().delete(id);
	}
	@Override
	public int deletes(Long... ids) {
		getDao().deletes(ids);
		return ids.length;
	}
	@Override
	public int update(T model) {
		return getDao().update(model);
	}

	
}
