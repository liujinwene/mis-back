package com.sec.security.service;

import java.util.List;

import com.sec.security.model.SecAcl;
import com.sec.security.model.SecMenu;

public interface MenuService {

	
	/**
	 * 
	 * @return
	 */
	List<SecMenu> findMenus(boolean hasRole);
	
	void save(SecMenu menu);
	
	void delete(Long id);
	
	SecMenu findById(Long id);
	
	SecMenu  selectAndChildren(Long menuId);
	///////////////////////
	/////acl start
	//////////////////////
	
	/**
	 * 获取所有acl
	 * 
	 * @return
	 */
	List<SecAcl> listAcls(boolean hasRole);
	
	SecAcl findAcl(Long id);
	
	void saveAcl(SecAcl acl);
	
	void deleteAcl(Long... ids);
	
	
}
