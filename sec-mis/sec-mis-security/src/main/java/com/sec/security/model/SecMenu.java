package com.sec.security.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.apache.ibatis.type.Alias;

/**
 * 用户一级菜单静态数据
 * 
 * @author bl
 * 
 */
@Alias("secMenu")
public class SecMenu extends AbstractEntity implements Comparable<SecMenu> {

	private static final long serialVersionUID = 1L;

	public static final int MENU_TYPE_USER = 1;

	public static final int MENU_TYPE_MEMBER = 0;

	public static final String PROP_NAME = "name";
	public static final String PROP_TYPE = "type";
	public static final String PROP_ENABLED = "enabled";
	public static final String PROP_LOCATION = "enabled";

	//@Column(name = "name")
	private String name; // 菜单名称

	//@Column(name = "position")
	private Integer position = 0; // 大菜单显示的位置,默认显示在左边
	
	//@Column(name = "location")
	private String location; //

	//@Column(name = "order_num")
	private Integer orderNum=1; // 排序使用

	//@Column(name = "display")
	private boolean display; // 是否启用

	//@ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
	//@JoinColumn(name = "parent_id")
	//private Menu parent;

	//@OneToMany(cascade = CascadeType.REMOVE, mappedBy = "parent", fetch = FetchType.EAGER)
	@JsonIgnore
	private List<SecMenu> children = new ArrayList<SecMenu>();
	//@Column(name = "icon")
	private String icon;

	//@Column(name = "create_time")
	private Date createTime;

	//@Column(name = "modify_time")
	private Date modifyTime;
	
	//@ManyToMany(mappedBy = "menus", targetEntity = Role.class)
	// @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@JsonIgnore
	private List<SecRole> roles;// = Collections.emptySet();
	
	private Long parentId;
	
	private String target;
	
	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public SecMenu() {
	}

	@Override
	public int compareTo(SecMenu o) {
		return this.getOrderNum().compareTo(o.getOrderNum());
	}

	public SecMenu(Long id) {
		this.setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDisplay() {
		return display;
	}

	public void setDisplay(boolean display) {
		this.display = display;
	}

	public Integer getPosition() {
		return position;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}

	public Integer getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(Integer orderNum) {
		this.orderNum = orderNum;
	}
	
	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

/*	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}*/
	@JsonIgnore
	public List<SecMenu> getChildren() {
		return children;
	}
	@JsonIgnore
	public void setChildren(List<SecMenu> children) {
		this.children = children;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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
	public List<SecRole> getRoles() {
		return roles;
	}
	public void setRoles(List<SecRole> roles) {
		this.roles = roles;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof SecMenu) {
			SecMenu o = (SecMenu) obj;

			if (o.getId().equals(getId())) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}

	}
	public String toString(){
		return String.format("menu:[id:%s,name:%s,location:%s]", id,name,location);
	}
}
