package com.sec.security.vo;

import java.io.Serializable;
import java.util.List;

import com.sec.security.model.SecAcl;

/**
 * 用户权限包装类
 * 
 * @author bl
 * 
 */
public class AclWarp implements Serializable {

	private static final long serialVersionUID = -7265976421160453260L;

	private String menuName;

	private List<SecAcl> acls;

	public AclWarp(String menuName, List<SecAcl> acls) {
		this.acls = acls;
		this.menuName = menuName;

	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public List<SecAcl> getAcls() {
		return acls;
	}

	public void setAcls(List<SecAcl> acls) {
		this.acls = acls;
	}

	public int getAclSize() {
		if (this.acls == null)
			return 0;
		else
			return this.acls.size();
	}

}
