package com.sec.security.model;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.type.Alias;
import org.codehaus.jackson.annotate.JsonIgnore;

@Alias("secAcl")
public class SecAcl extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	public static final int ACL_TYPE_USER = 1;
	public static final int ACL_TYPE_MEMBER = 0;
	public static final String PROP_MENU = "menu";
	public static final String PROP_NAME = "name";
	public static final String PROP_TYPE = "type";
	public static final String PROP_ROLES = "roles";
	public static final String PROP_MENU_ID = "menuId";
	public static final String PROP_ENALBED = "enabled";

	//@Column(name = "name", nullable = false)
	private String name; // 显示名称

	//@Column(name = "url_pattern", nullable = false)
	private String pattern; // 资源地址

	//@Column(name = "order_num")
	private int orderNum; // 排序

	//@Column(name = "create_time")
	private Date createTime; // 是否启用

	//@Column(name = "modify_time")
	private Date modifyTime;

	//@ManyToOne()
	//@JoinColumn(name = "menu_id")
	//@JsonIgnore
	//private Menu menu; // 资源对应的一级菜

	//@ManyToMany(mappedBy = "acls", targetEntity = Role.class)
	// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	private List<SecRole> roles;// = Collections.EMPTY_LIST;
	
	private String code;
	
	//@Column(name = "menu_id")
	private Long menuId;
	
	public Long getMenuId() {
		return menuId;
	}

	public void setMenuId(Long menuId) {
		this.menuId = menuId;
	}

	public SecAcl() {

	}

	public SecAcl(Long id) {
		this.setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}
	
/*	@JsonIgnore
	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}*/
	@JsonIgnore
	public List<SecRole> getRoles() {
		return roles;
	}
	public void setRoles(List<SecRole> roles) {
		this.roles = roles;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String toString(){
		return String.format("acl:[id:%s,name:%s,pattern:%s]", id,name,pattern);
	}
}
