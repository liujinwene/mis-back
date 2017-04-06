package com.sec.security.dao;

import org.apache.ibatis.annotations.Param;

import com.sec.mis.mybatis.BaseDao;
import com.sec.mis.mybatis.MybatisDao;
import com.sec.security.model.SecUser;

@MybatisDao
public interface SecUserDao extends BaseDao<SecUser>{//GenericDAO<User, Long>, PageSearch<User> {

	/**
	 * 
	 * @param userId
	 * @param eager
	 * @param fetchTarget
	 * @return
	 */
	//public List<User> getUserById(Long userId, boolean eager, String fetchTarget);
	
	/***
	 * 通过登录名查找用户
	 * @param userName
	 * @return
	 */
	public SecUser getUserByName(String userName);
	/***
	 * 删除用户的角色关系
	 * @param userId
	 */
	void deleteUserRole(Long... userId);
	/***
	 * 保存用户-角色的关系
	 * @param userId
	 * @param roleIds
	 */
	void saveUserRole(@Param("userId")Long userId, @Param("roleIds")Long... roleIds);
	
	/**
	 *删除用户-渠道关系
	 * @param id
	 */
	public void deleteUserChannel(Long... userId);
	/**
	 * 保存用户-渠道关系
	 * @param userId
	 * @param array
	 */
	public void saveUserChannel(@Param("userId")Long userId, @Param("channelIds")Integer... channelIds);
}
