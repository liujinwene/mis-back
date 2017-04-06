package com.sec.security.dao;

import java.util.List;
import java.util.Map;

import com.sec.mis.mybatis.BaseDao;
import com.sec.mis.mybatis.MybatisDao;
import com.sec.security.model.SecMenu;

@MybatisDao
public interface SecMenuDao extends BaseDao<SecMenu>{//GenericDAO<Menu, Long>, PageSearch<Menu>{
	
	//public List<Menu> findMenuList(Map<String, String> queryParam);
	/***
	 * 查找菜单以及菜单对应的角色
	 * @param queryParam
	 * @return
	 */
	public List<SecMenu> selectMenuRoles(Map<String, Object> queryParam);
	/***
	 * 根据menuId查找对应的菜单，并递归查询自己的子类
	 * @param menuId
	 * @return
	 */
	SecMenu selectAndChildren(Long menuId);
}
