package com.sec.security.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.sec.security.dao.SecAclDao;
import com.sec.security.dao.SecMenuDao;
import com.sec.security.model.SecAcl;
import com.sec.security.model.SecMenu;
import com.sec.security.service.MenuService;

@Service("SecMenuServiceImpl")
public class MenuServiceImpl implements MenuService {

	@Resource
	private SecAclDao secAclDao;

	@Resource
	private SecMenuDao secMenuDao;
	

	@Override
	public List<SecMenu> findMenus(boolean hasRole) {
		if(hasRole){
			return secMenuDao.selectMenuRoles(null);
		}else{
			return secMenuDao.list(null);
		}
		/*List<Menu> menus = menuDao.findAll();
		if(lazily){
			for(Menu menu:menus){
				menu.getRoles().size();
			}
		}
		return menus;*/
	}

	@Override
	public void save(SecMenu menu) {
		if(menu.getId() == null){
			menu.setCreateTime(new Date());
			secMenuDao.create(menu);
		}else{
			//menu.setModifyTime(new Date());
		    secMenuDao.update(menu);
		}
		
	}
	private void getMenuIds(List<SecMenu> menus,List<Long> ids){
		if(CollectionUtils.isNotEmpty(menus)){
			for(SecMenu menu:menus){
				ids.add(0,menu.getId());
				getMenuIds(menu.getChildren(), ids);
			}
		}
	}
	@Override
	public void delete(Long id) {
		/*
		 * hibernate不熟悉，先这样写
		 */
		//TODO 记得改呀。。。。。
	/*	List<Long> idList = new ArrayList<Long>();
		for(Long id:ids){
			Menu menu = menuDao.get(id);
			idList.add(menu.getId());
			getMenuIds(menu.getChildren(),idList);
			menuDao.delete(menu);
		}
		aclDao.deleteByMenuId(idList.toArray(new Long[idList.size()]));*/
	//	menuDao.delete(ids);
		List<Long> idList = new ArrayList<Long>();
		SecMenu menuAndChildren = secMenuDao.selectAndChildren(id);
		idList.add(menuAndChildren.getId());
		getMenuIds(menuAndChildren.getChildren(),idList);
		
		Long[] ids = idList.toArray(new Long[idList.size()]);
		//批量删除时，有CONSTRAINT，so一个个删吧。。。。。
		for(Long menuId:ids){
		    secMenuDao.delete(menuId);
		}
		secAclDao.deleteByMenuId(ids);
	}

	@Override
	public SecMenu findById(Long id) {
		return secMenuDao.get(id);
	}
	@Override
	public SecMenu selectAndChildren(Long menuId){
		return secMenuDao.selectAndChildren(menuId);
	}
	@Override
	public List<SecAcl> listAcls(boolean hasRole) {
		/*List<Acl> acls = aclDao.findAll();
		for (Acl acl : acls) {
			acl.getRoles().size();
		}
		return acls;*/
		if(hasRole){
			return secAclDao.selectAclRoles();
		}else{
			return secAclDao.list(Collections.EMPTY_MAP);
		}
	}
	
	@Override
	public SecAcl findAcl(Long id){
		return secAclDao.get(id);
	}

	@Override
	public void saveAcl(SecAcl acl) {
		if(acl.getId()==null){
		    secAclDao.create(acl);
		}else{
		    secAclDao.update(acl);
		}
	}

	@Override
	public void deleteAcl(Long... ids) {
	    secAclDao.deletes(ids);
	}
}
