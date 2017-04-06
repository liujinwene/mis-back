package com.sec.security.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sec.mis.mybatis.BaseDao;
import com.sec.mis.mybatis.MybatisDao;
import com.sec.security.model.SecRole;
@MybatisDao
public interface SecRoleDao extends BaseDao<SecRole>{// GenericDAO<Role, Long>, PageSearch<Role>{
	
	// List<Role> getRoleById(Long roleId,boolean eager,String fetchTarget);
	
	 List<SecRole> getRoleByUserId(Long userId);
	 
	 /***
	  * 包含menu，acl
	  * @param roleId
	  * @return
	  */
	 SecRole getAll(Long roleId);
	 /**
	  * 保存角色与menu的关系
	  * @param roleId
	  * @param menuIds
	  */
	 void saveRoleMenu(@Param("roleId")Long roleId, @Param("menuIds")Long... menuIds);
	 /***
	  * 保存角色与acl的关系
	  * @param roleId
	  * @param aclIds
	  */
	 void saveRoleAcl(@Param("roleId")Long roleId, @Param("aclIds")Long... aclIds);
	 /**
	  * 删除角色与menu的关系
	  * @param roleId
	  */
	 void deleteMenuByRoleId(Long roleId);
	 /***
	  * 删除角色与acl的关系
	  * @param roleId
	  */
	 void deleteAclByRoleId(Long roleId);
	 
}
