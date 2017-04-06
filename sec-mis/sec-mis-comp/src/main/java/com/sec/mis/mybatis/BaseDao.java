package com.sec.mis.mybatis;



public interface BaseDao<T> extends PageDao<T>{
	
	T get(long id);
	
	int create(T model);

	int update(T model);

	int delete(Long id);
	
	int deletes(Long... ids);
	
}
