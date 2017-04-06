package com.sec.web;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sec.security.model.SecMenu;
import com.sec.security.model.SecUser;
import com.sec.security.utils.SessionUtils;

@Controller
@RequestMapping("/home")
public class HomeController {
	private static final Log log = LogFactory.getLog(HomeController.class);
	
	@RequestMapping("")
	public String index(ModelMap model) {
		SecUser user = SessionUtils.getUser();
		List<SecMenu> menus = user.getMenuTree();
		model.addAttribute("user", user);
		model.addAttribute("menuJson", toJson(doWraper(menus)));
		return "home";
	}
	
	private List<MenuWraper> doWraper(List<SecMenu> menus){
		List<MenuWraper> list = new ArrayList<MenuWraper>();
		if(CollectionUtils.isNotEmpty(menus)){
			for(SecMenu menu:menus){
				MenuWraper wraper =new MenuWraper(menu);
				if(CollectionUtils.isNotEmpty(menu.getChildren())){
					wraper.setChildren(doWraper(menu.getChildren()));
				}
				list.add(wraper);
			}
		}
		return list;
	}
	private String toJson(List<MenuWraper> menus){
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			return objectMapper.writeValueAsString(menus);
		} catch (Exception e) {
			log.error(e);
		}
		return "[]";
	}
	
	private class MenuWraper{
		private Long id;
		private String name;
		private String url;
		private Integer orderNum;
		private boolean display; 
		private String icon;
		public String getIcon() {
			return icon;
		}
		public void setIcon(String icon) {
			this.icon = icon;
		}
		private List<MenuWraper> children;
		
		public MenuWraper(SecMenu menu){
			this.id = menu.getId();
			this.name = menu.getName();
			this.url = menu.getLocation();
			this.orderNum = menu.getOrderNum();
			this.display = menu.isDisplay();
			this.icon = menu.getIcon();
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
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
		public Integer getOrderNum() {
			return orderNum;
		}
		public void setOrderNum(Integer orderNum) {
			this.orderNum = orderNum;
		}
		public boolean isDisplay() {
			return display;
		}
		public void setDisplay(boolean display) {
			this.display = display;
		}
		public List<MenuWraper> getChildren() {
			return children;
		}
		public void setChildren(List<MenuWraper> children) {
			this.children = children;
		}
		
	}
}
