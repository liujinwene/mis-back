package com.sec.security.service;

import java.util.List;
import java.util.Map;

import com.sec.mis.page.Page;
import com.sec.mis.poi.ExcelEntity;
import com.sec.security.model.SecRole;

public interface RoleService {
	
	List<SecRole> findRoles();
	
    Page<SecRole> findRoles4Page(Map<String, Object> queryParam, int start, int limit, String order);
	
	/**
	 * loadAll为true加载menus,acls
	 * @param roleId
	 * @param loadAll
	 * @return
	 */
	SecRole findRole(Long roleId,boolean loadAll);
	
	//void saveRole(Role role);
	
	void saveRole(SecRole role,Long[] menuIds,Long[] aclIds);
	
	void deleteRole(Long... roleId);
	
	List<SecRole> findByCode(String code);
	
	List<SecRole> listRoles(Map<String, Object> queryParam);
	
	void genSecRoleExcel(List<SecRole> dataList,ExcelEntity excelEntity);
	
}
