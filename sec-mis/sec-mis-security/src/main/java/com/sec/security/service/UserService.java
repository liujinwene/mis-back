package com.sec.security.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sec.mis.page.Page;
import com.sec.mis.poi.ExcelEntity;
import com.sec.security.model.SecUser;

public interface UserService {

	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

	/**
	 * 查询用户列表
	 */
	Page<SecUser> findUsers(Map<String, Object> queryParam, int start, int limit, String order);

	/**
	 * 保存更新用户
	 * 
	 * @param role
	 */
	void saveUser(SecUser user, Integer[] channelIds,Long... roleIds);

	/**
	 * 查询用户信息
	 */
	SecUser getUserById(Long id);

	SecUser getUserByName(String userName);

	/**
	 * 删除用户
	 * 
	 * @param ids
	 */
	void deleteUserByIds(Long... ids);

	/**
	 * 审核用户
	 * 
	 * @param id
	 * @param enabled
	 */
	void auditUser(Long id, Boolean enabled);
	
	void updateLastLoginDate(SecUser user);
	
	List<SecUser> listUsers(Map<String, Object> queryParam);
	
	void genSecUserExcel(List<SecUser> dataList,ExcelEntity excelEntity);
}
