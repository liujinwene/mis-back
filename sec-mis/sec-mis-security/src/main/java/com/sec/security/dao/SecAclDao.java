package com.sec.security.dao;

import java.util.List;

import com.sec.mis.mybatis.BaseDao;
import com.sec.mis.mybatis.MybatisDao;
import com.sec.security.model.SecAcl;

@MybatisDao
public interface SecAclDao extends BaseDao<SecAcl>{//GenericDAO<Acl, Long>, PageSearch<Acl> {

	//List<Acl> getAclById(Long aclId, boolean eager, String fetchTarget);
	/***
	 * 由menuIds删除acl
	 * @param menuIds
	 */
	void deleteByMenuId(Long... menuIds);
	/***
	 * 查找所有的acl以及它所对应的角色
	 * @return
	 */
	List<SecAcl> selectAclRoles();
}
