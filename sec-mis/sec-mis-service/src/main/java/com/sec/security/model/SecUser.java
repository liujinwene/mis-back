package com.sec.security.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.type.Alias;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 用户实体
 */

@Alias("secUser")
public class SecUser extends AbstractEntity implements UserDetails {

	private static final long serialVersionUID = 4816982434768112326L;
	public static final String PROP_ROLES = "roles";
	public static final String PROP_USERNAME = "username";
	public static final String PROP_ENABLED = "enabled";

	//@Column(name = "realname")
	private String realname; // 真实姓名

	//@Column(name = "username", nullable = false)
	private String username; // 用户名

	//@Column(name = "password", nullable = false)
	@JsonIgnore
	private String password; // 密码

	//@Column(name = "enabled")
	private boolean enabled;

	//@Column(name = "cert_id")
	private String certId;

	//@Column(name = "email")
	private String email;

	//@Column(name = "mobile")
	private String mobile;

	//@Column(name = "account_non_expired")
	private boolean accountNonExpired;

	//@Column(name = "account_non_locked")
	private boolean accountNonLocked;

	//@Column(name = "credentials_non_expired")
	private boolean credentialsNonExpired = true; // 目前这个字段不需要用

	//@Column(name = "address")
	private String address;

	//@Column(name = "create_time")
	private Date createTime; // 用户创建时间

	//@Column(name = "modify_time")
	private Date modifyTime;

	//@Column(name = "last_login_time")
	private Date lastLoginTime; // 用户创建时间
	
	private String phone;
	
	private String qq;
	
	private String aduitPass;

	// 业务
	//@ManyToMany(targetEntity = Role.class)
	//@JoinTable(name = "sec_user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	private List<SecRole> roles = Collections.emptyList(); // 后台用户对应的角色

	public SecUser() {

	}

	//@Transient
	@JsonIgnore
	public List<SecMenu> getMenuTree() {
		//List<Menu> list = new ArrayList<Menu>();
		/**
		 * fix bug:
		 * 当一个用户拥有两个角色其中两个角色中有共同的菜单，则出现bug:顶级菜单的children为空
		 */
		//TODO 此bug可能是hibernate实用不当，或者就是它的bug
		//顶级menu
		Set<SecMenu> topMenus = new HashSet<SecMenu>();
		//存放不在topMenus的子类菜单里
		List<SecMenu> tempMenus = new ArrayList<SecMenu>();
		for (SecRole role : roles) {
			if(role.getMenus()==null){
				continue;
			}
			for (SecMenu menu : role.getMenus()) {
				if(!menu.isDisplay()){
					continue;
				}
				if (menu.getParentId() == null) {
					topMenus.add(menu);
				}else{
					tempMenus.add(menu);
				}
			}
		}
		//将tempMenus归类到topMenus中去
		//有些许性能影响，因为当未出现上述bug时，就做无用功了
		addChildren(topMenus,tempMenus);
		List<SecMenu> list = new ArrayList<SecMenu>(topMenus);
		//list = topMenus.
		sort(list);
		return list;
	}
	private void addChildren(Set<SecMenu> topMenus,List<SecMenu> tempMenus){
		for(SecMenu topMenu:topMenus){
			for(int index=0;index<tempMenus.size();index++){
				/*if(tempMenu.getParent()!=null && topMenu.getId().longValue()==tempMenu.getParent().getId().longValue()){
					List<Menu> children = topMenu.getChildren();
					if(!hasMenu(children,tempMenu)){
						children.add(tempMenu);
					}
				}*/
				SecMenu tempMenu = tempMenus.get(index);
				SecMenu  parent = findParent(topMenu,tempMenu);
				if(parent!=null && !hasMenu(parent.getChildren(),tempMenu)){
					parent.getChildren().add(tempMenu);
					tempMenus.remove(index);
					index--;
				}
			}
		}
	}
	/**
	 * 递归查询父Menu
	 * @param parentMenu 被查询的对象
	 * @param menu 需要查询自个的父Menu的menu
	 * @return
	 */
	private SecMenu findParent(SecMenu parentMenu,SecMenu menu){
		if(menu.getParentId()!=null && menu.getParentId().equals(parentMenu.getId())){
			return parentMenu;
		}
		List<SecMenu> children = parentMenu.getChildren();
		for(SecMenu child:children){
			SecMenu parent = findParent(child,menu);
			if(parent!=null){
				return parent;
			}
		}
		return null;
	}
	private boolean hasMenu(List<SecMenu> children,SecMenu menu){
		if(children==null || children.size()==0){
			return false;
		}
		for(SecMenu temp:children){
			if(temp.getId().equals(menu.getId())){
				return true;
			}
		}
		
		return false;
	}
	private void sort(List<SecMenu> list){
		Collections.sort(list);
		for(SecMenu menu:list){
			if(menu.getChildren()!=null && menu.getChildren().size()>0){
				sort(menu.getChildren());
			}
		}
	}
	@Override
	//@Transient
	@JsonIgnore
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getUsername() {
		return this.username;
	}
	@JsonIgnore
	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getCertId() {
		return certId;
	}

	public void setCertId(String certId) {
		this.certId = certId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	@JsonIgnore
	public List<SecRole> getRoles() {
		return roles;
	}
	@JsonIgnore
	public void setRoles(List<SecRole> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	@JsonIgnore
	public void setPassword(String password) {
		this.password = password;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getAduitPass()
    {
        return aduitPass;
    }

    public void setAduitPass(String aduitPass)
    {
        this.aduitPass = aduitPass;
    }

    @Override
	public int hashCode() {
		final int prime = 31;
		int result = 0;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (getClass() != obj.getClass()) return false;
		SecUser other = (SecUser) obj;
		if (username == null) {
			if (other.username != null) return false;
		} else if (!username.equals(other.username)) return false;
		return true;
	}

}
