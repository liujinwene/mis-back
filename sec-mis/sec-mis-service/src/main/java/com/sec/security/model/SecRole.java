package com.sec.security.model;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.apache.ibatis.type.Alias;

@Alias("secRole")
public class SecRole extends AbstractEntity implements GrantedAuthority {

	private static final long serialVersionUID = 1L;

	public static final int ROLE_TYPE_USER = 1;

	public static final int ROLE_TYPE_MEMBER = 0;

	public static final String PROP_NAME = "name";
	public static final String PROP_TYPE = "type";
	public static final String PROP_CODE = "code";
	public static final String PROP_ACLS = "acls";
	public static final String PROP_ENABLED = "enabled";

	//@Column(name = "code", nullable = false)
	private String code; // 角色名称 按照约定的命名ROLE_+大写的英文单词

	//@Column(name = "name", nullable = false)
	private String name; // 角色说明

	//@Column(name = "enabled")
	private boolean enabled = true; // 是否启用,默认为true

	//@Column(name = "remark")
	private String remark;

	//@Column(name = "create_time")
	private Date createTime;

	//@Column(name = "modify_time")
	private Date modifyTime;

	@JsonIgnore
	//@ManyToMany(targetEntity = Menu.class)
	//@JoinTable(name = "sec_role_menu", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "menu_id"))
	private List<SecMenu> menus;// = Collections.emptyList(); // 菜单授权

	@JsonIgnore
	//@ManyToMany(targetEntity = Acl.class)
	//@JoinTable(name = "sec_role_acl", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "acl_id"))
	private List<SecAcl> acls;// = Collections.emptyList(); // 访问路径授权

	//@JsonIgnore
	//@ManyToMany(mappedBy = "roles", targetEntity = User.class)
	//private List<User> users = Collections.emptyList();// 角色中用户

	public final static String ROLE_DEFAULT = "ROLE_DEFAULT-COMMON344";
	public static SecRole getDefaultRole(){
		SecRole role = new SecRole();
		role.setId(Long.MAX_VALUE);
		role.setName("ROLE_DEFAULT");
		role.setCode(ROLE_DEFAULT);
		return role;
	}
	public SecRole() {

	}

	public SecRole(Long id) {
		this.setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	@JsonIgnore
	public List<SecMenu> getMenus() {
		return menus;
	}

	public void setMenus(List<SecMenu> menus) {
		this.menus = menus;
	}
	/*@JsonIgnore
	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}*/

	public int compareTo(Object o) {
		if (!(o instanceof SecRole)) {
			throw new IllegalArgumentException("Type must be Role");
		}
		SecRole role = ((SecRole) o);
		return this.name.compareTo(role.getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (getClass() != obj.getClass()) return false;
		SecRole other = (SecRole) obj;
		if (name == null) {
			if (name != null) return false;
		} else if (!name.equals(other.name)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "Role [getId()=" + getId() + ", name=" + name + "]";
	}

	@Override
	public String getAuthority() {
		return this.getCode();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	@JsonIgnore
	public List<SecAcl> getAcls() {
		return acls;
	}

	public void setAcls(List<SecAcl> acls) {
		this.acls = acls;
	}

}
