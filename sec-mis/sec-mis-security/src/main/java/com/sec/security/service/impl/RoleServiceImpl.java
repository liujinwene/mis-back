package com.sec.security.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.sec.mis.page.Page;
import com.sec.mis.poi.ExcelEntity;
import com.sec.mis.poi.ExcelSheet;
import com.sec.security.dao.SecRoleDao;
import com.sec.security.model.SecRole;
import com.sec.security.service.MenuService;
import com.sec.security.service.RoleService;

@Service("SecRoleServiceImpl")
public class RoleServiceImpl implements RoleService{
	
	@Resource
	private SecRoleDao secRoleDao;
	@Resource
	private MenuService menuService;
	@Override
	public List<SecRole> findRoles() {
		return secRoleDao.list(null);
	}

	@Override
	public SecRole findRole(Long roleId,boolean loadAll) {
		if(loadAll){
			return secRoleDao.getAll(roleId);
		}else{
			return secRoleDao.get(roleId);
		}
		
	}
	
	@Override
	public Page<SecRole> findRoles4Page(Map<String, Object> queryParam, int start, int limit, String order){
		int count = secRoleDao.count(queryParam);
		if(count<=0){
			return Page.empty();
		}
		queryParam.put("start", start);
		queryParam.put("limit", limit);
		return Page.create(secRoleDao.list(queryParam), count);
	}

	public void saveRole(SecRole role,Long[] menuIds,Long[] aclIds){
		if(role.getId()==null){
		    secRoleDao.create(role);
			saveMenuAcl(role.getId(),menuIds,aclIds);
		}else{
		    secRoleDao.update(role);
		    secRoleDao.deleteMenuByRoleId(role.getId());
		    secRoleDao.deleteAclByRoleId(role.getId());
			saveMenuAcl(role.getId(),menuIds,aclIds);
		}
		
	}
	
	private void saveMenuAcl(Long roleId,Long[] menuIds,Long[] aclIds){
		if(menuIds!=null && menuIds.length>0){
			List<Long> menuList = new ArrayList<Long>();
			for(Long menuId:menuIds){
				if(menuId!=null){
					menuList.add(menuId);
				}
			}
			if(menuList.size()>0){
			    secRoleDao.saveRoleMenu(roleId, menuList.toArray(new Long[menuList.size()]));
			}
		}
		if(aclIds!=null && aclIds.length>0){
			List<Long> aclList = new ArrayList<Long>();
			for(Long aclId:aclIds){
				if(aclId!=null){
					aclList.add(aclId);
				}
			}
			if(aclList.size()>0){
			    secRoleDao.saveRoleAcl(roleId, aclList.toArray(new Long[aclList.size()]));
			}
		}
	}
	
	@Override
	public void deleteRole(Long... roleIds) {
	    secRoleDao.deletes(roleIds);
	}
	
	@Override
	public List<SecRole> findByCode(String code){
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("code", code);
		return secRoleDao.list(param);
	}

	@Override
	public List<SecRole> listRoles(Map<String, Object> queryParam) {
		return secRoleDao.list(queryParam);
	}

	@Override
	public void genSecRoleExcel(List<SecRole> dataList, ExcelEntity excelEntity) {
		List<ExcelSheet> sheetList = new ArrayList<ExcelSheet>();	
		ExcelSheet excelSheet = new ExcelSheet();
		excelSheet.setSheetName("角色管理(" + dataList.size() + ")");
		List<String> titleList = new ArrayList<String>();
		titleList.add("代码");
		titleList.add("名称");
		titleList.add("是否有效");
		titleList.add("说明");
		titleList.add("创建时间");
		titleList.add("修改时间");

		
		excelSheet.setTitleList(titleList);
		int[] columnWidth = null;
		
		columnWidth = new int[] { 8000, 8000, 4000,8000,6000,6000};	
		
	
		excelSheet.setColumnWidth(columnWidth);

		if (null != dataList && dataList.size() > 0 && null != excelEntity) {
			List<List<String>> contentList = new ArrayList<List<String>>();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
			for (SecRole item : dataList) {
				List<String> contentItem = new ArrayList<String>();
				contentItem.add(item.getCode());
				contentItem.add(item.getName());
				
				if(item.isEnabled()){
					contentItem.add("true");
				}else{
					contentItem.add("false");
				}
				
				contentItem.add(item.getRemark());
				if(!StringUtils.isEmpty(item.getCreateTime())){
					contentItem.add(df.format(item.getCreateTime()));
				}else{
					contentItem.add("");
				}
				
				if(!StringUtils.isEmpty(item.getModifyTime())){
					contentItem.add(df.format(item.getModifyTime()));
				}else{
					contentItem.add("");
				}
				
				contentList.add(contentItem);
			}
			excelSheet.setContentList(contentList);
		}
		sheetList.add(excelSheet);
		excelEntity.setSheetList(sheetList);
	}
	
}
