package com.sec.web.security.vo;

import com.sec.security.model.SecAcl;
import com.sec.security.model.SecMenu;

/***
 * 前台菜单以及Acl的树展现wrapper
 *
 */
public class UrlResource {

	/**
	 * 若是Acl的id,则为menu.id_acl.id
	 */
	private String id;
	
	private String parentId;
	
	private String name;
	
	private String url;
	
	/**
	 * 对应前台checkbox checked
	 */
	private boolean checked;

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	/**
	 * 是否是menu
	 */
	private boolean menu;
	
	public boolean isMenu() {
		return menu;
	}

	public void setMenu(boolean menu) {
		this.menu = menu;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public static UrlResource menuToUrlResource(SecMenu menu){
		UrlResource url = new UrlResource();
		url.setId(menu.getId().toString());
		url.setName(menu.getName());
		url.setUrl(menu.getLocation());
		url.setMenu(true);
		if(menu.getParentId()!=null){
			url.setParentId(menu.getParentId().toString());
		}
		return url;
	}
	
	public static UrlResource aclToUrlResource(SecAcl acl){
		UrlResource url = new UrlResource();
		url.setId(acl.getMenuId().toString()+"_"+acl.getId().toString());
		url.setName(acl.getName());
		url.setUrl(acl.getPattern());
		url.setParentId(acl.getMenuId().toString());
		url.setMenu(false);
		return url;
	}
}
