package com.sec.security.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sec.mis.page.Page;
import com.sec.mis.poi.ExcelEntity;
import com.sec.mis.poi.ExcelSheet;
import com.sec.security.dao.SecRoleDao;
import com.sec.security.dao.SecUserDao;
import com.sec.security.model.SecUser;
import com.sec.security.service.UserService;

@Service("SecUserServiceImpl")
public class UserServiceImpl implements UserService {

	@Resource
	private SecUserDao secUserDao;
	@Resource
	private SecRoleDao secRoleDao;
	
	@Resource
	private PasswordEncoder passwordEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		return getUserByName(userName);
	}
	
	public Page<SecUser> findUsers(Map<String, Object> queryParam, int start, int limit, String order) {
		int count = secUserDao.count(queryParam);
		if(count<=0){
			return Page.empty();
		}
		queryParam.put("start", start);
		queryParam.put("limit", limit);
		return Page.create(secUserDao.list(queryParam), count);
	}

	@Override
	public void saveUser(SecUser user,Integer[] channelIds, Long... roleIds) {
		initParms(user);

		if (user.getId() == null) {
			user.setCreateTime(new Date());
			secUserDao.create(user);
			saveUserRole(user.getId(),roleIds);
		} else {
		    secUserDao.update(user);
			if(roleIds !=null && roleIds.length > 0){
			    secUserDao.deleteUserRole(user.getId());
				saveUserRole(user.getId(),roleIds);
			}
			if(channelIds != null && channelIds.length > 0){
				this.secUserDao.deleteUserChannel(user.getId());
				saveUserChannel(user.getId(), channelIds);
			}
		}
	}
	private void saveUserRole(Long userId,Long... roleIds){
		if(roleIds.length>0){
			List<Long> list = new ArrayList<Long>();
			
			for(Long roleId:roleIds){
				if(roleId!=null){
					list.add(roleId);
				}
			}
			if(list.size()>0){
			    secUserDao.saveUserRole(userId, list.toArray(new Long[list.size()]));
			}
		}
	}
	private void saveUserChannel(Long userId,Integer... channelIds){
		if(channelIds.length > 0){
			List<Integer> list = new ArrayList<Integer>();
			for(Integer channelId : channelIds){
				if(channelId != null){
					list.add(channelId);
				}
			}
			if(list.size()>0){
			    secUserDao.saveUserChannel(userId, list.toArray(new Integer[list.size()]));
			}
		}
	}
	// 初始化用户密码
	private void initParms(SecUser user) {
		if (user.getId() == null) {
			encodePassword(user);
		}

	}
	private void encodePassword(SecUser user){
		String password = passwordEncoder.encode(user.getPassword());
		user.setPassword(password);
	}
	@Override
	public SecUser getUserById(Long id) {
		SecUser user =  secUserDao.get(id);
		if(user!=null){
			user.setRoles(secRoleDao.getRoleByUserId(user.getId()));
		}
		return user;
	}

	@Override
	public void deleteUserByIds(Long... ids) {
	    secUserDao.deleteUserRole(ids);
	    secUserDao.deletes(ids);
	}

	@Override
	public void auditUser(Long id, Boolean enabled) {
		SecUser user = secUserDao.get(id);
		user.setEnabled(enabled);
		secUserDao.update(user);
	}

	@Override
	public SecUser getUserByName(String userName) {
		SecUser user = secUserDao.getUserByName(userName);
		if(user!=null){
			user.setRoles(secRoleDao.getRoleByUserId(user.getId()));
		}
		return user;
	}

	public void updateLastLoginDate(SecUser user) {
	    secUserDao.update(user);
	}

	@Override
	public List<SecUser> listUsers(Map<String, Object> queryParam) {
		return this.secUserDao.list(queryParam);
	}

	@Override
	public void genSecUserExcel(List<SecUser> dataList, ExcelEntity excelEntity) {
		List<ExcelSheet> sheetList = new ArrayList<ExcelSheet>();	
		ExcelSheet excelSheet = new ExcelSheet();
		excelSheet.setSheetName("用户管理(" + dataList.size() + ")");
		List<String> titleList = new ArrayList<String>();
		titleList.add("用户名");
		titleList.add("名字");
		titleList.add("是否锁定");
		titleList.add("手机");
		titleList.add("email");
		titleList.add("创建时间");
		titleList.add("修改时间");
		titleList.add("上次登录时间");
		
		excelSheet.setTitleList(titleList);
		int[] columnWidth = null;
		
		columnWidth = new int[] { 8000, 8000, 4000,4000,8000,6000,6000,6000};	
		
	
		excelSheet.setColumnWidth(columnWidth);

		if (null != dataList && dataList.size() > 0 && null != excelEntity) {
			List<List<String>> contentList = new ArrayList<List<String>>();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
			for (SecUser item : dataList) {
				List<String> contentItem = new ArrayList<String>();
				contentItem.add(item.getUsername());
				contentItem.add(item.getRealname());
				
				if(item.isEnabled()){
					contentItem.add("否");
				}else{
					contentItem.add("是");
				}
				
				contentItem.add(item.getMobile());
				contentItem.add(item.getEmail());
				contentItem.add(df.format(item.getCreateTime()));
				contentItem.add(df.format(item.getModifyTime()));
				contentItem.add(df.format(item.getLastLoginTime()));

				contentList.add(contentItem);
			}
			excelSheet.setContentList(contentList);
		}
		sheetList.add(excelSheet);
		excelEntity.setSheetList(sheetList);
		
	}
}
