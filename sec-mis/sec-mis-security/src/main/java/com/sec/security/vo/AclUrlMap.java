package com.sec.security.vo;
import com.sec.security.model.AbstractEntity;

public class AclUrlMap extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	public static final String ACL_URL_MAP_PROPERTY_NAME = "moduleName";
	public static final String ACL_URL_MAP_PROPERTY_ENABLED = "enabled";
	
	//@Column(name = "module_name", nullable = false)
	private String moduleName; // 功能包

	//@Column(name = "path", nullable = false)
	private String path; // url正则匹配式子

	//如果配置多个rolecode 请用逗号分割开
	//@Column(name = "role_code", nullable = false)
	private String roleCode; // 角色代码

	//@Column(name = "role_name", nullable = false)
	private String roleName; //角色名字

	//@Column(name = "enabled")
	private boolean enabled; // 是否启用
	
	//@Column(name = "sort")
	private int sort; // 排序(所有短的匹配方在后面)
	
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;
	}

}
