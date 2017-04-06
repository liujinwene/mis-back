package com.sec.mis.service;

import java.util.List;
import java.util.Map;


public interface BaseService<T> {
	
	/**
	 * 分页获取查询列表
	 * @param query
	 * @return
	 */
	List<T> list(Map<String, Object> query);
	/**
	 * 获取总数
	 * @param query
	 * @return
	 */
	int count(Map<String, Object> query);
	/**
	 * 获取详细信息
	 * @param query
	 * @return
	 */
	T get(long id);
	/**
	 * 新增信息
	 * @param model
	 * @return
	 */
	int create(T model);
	/**
	 * 删除信息
	 * @param model
	 * @return
	 */
	int delete(long id);
	/**
	 * 批量删除信息
	 * @param model
	 * @return
	 */
	int deletes(Long... ids);
	/**
	 * 修改信息
	 * @param model
	 * @return
	 */
	int update(T model);
}
