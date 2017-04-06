package com.sec.web.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sec.mis.util.Result;
import com.sec.security.model.SecAcl;
import com.sec.security.model.SecMenu;
import com.sec.security.service.MenuService;
import com.sec.web.security.vo.UrlResource;
 

@Controller
@RequestMapping("/menu")
public class MenuController {
	private static final Log log = LogFactory.getLog(MenuController.class);
	
	@Resource
	private MenuService menuService;
	
	@RequestMapping("/list")
	public String list() {
		return "security/menu/list";
	}
	
	@RequestMapping("/tree-data")
	@ResponseBody
	public List<UrlResource> findListdata() {
		List<SecMenu> menus = menuService.findMenus(false);
		Collections.sort(menus);
		List<SecAcl> acls = menuService.listAcls(false);
		List<UrlResource> urls = toUrlResource(menus, acls);
		return urls;//new DataGridPage(urls.size(), urls);
	}
	
	private List<UrlResource> toUrlResource(List<SecMenu> menus,List<SecAcl> acls){
		List<UrlResource> urls = new ArrayList<UrlResource>();
		if(CollectionUtils.isNotEmpty(menus)){
			for(SecMenu menu:menus){
				urls.add(UrlResource.menuToUrlResource(menu));
			}
		}
		if(CollectionUtils.isNotEmpty(acls)){
			for(SecAcl acl:acls){
				urls.add(UrlResource.aclToUrlResource(acl));
			}
			
		}
		return urls;
	}
	
	@RequestMapping("/add")
	public String add(@RequestParam(value="parentId",defaultValue="")Long parentId, ModelMap model) {
		SecMenu menu = new SecMenu();
		//Menu parent = new Menu();
		//parent.setId(parentId);
		menu.setParentId(parentId);
		model.addAttribute("menu", menu);
		return "security/menu/form";
	}
	
	@RequestMapping("/update")
	public String update(@RequestParam("menuId")Long menuId, ModelMap model) {
		model.addAttribute("menu", menuService.findById(menuId));
		return "security/menu/form";
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Result save(SecMenu menu){
		try{
			/*if(menu.getParent().getId() == null){
				menu.setParent(null);
			}*/
			menuService.save(menu);
			Result cm = new  Result(true);
			cm.setObject(UrlResource.menuToUrlResource(menu));
			return cm;
		}catch(Exception e){
			log.error("保存menu失败", e);
			return new Result(false,"未知错误");
		}
	}
	@RequestMapping("/delete")
	@ResponseBody
	public boolean delete(@RequestParam("menuId")Long menuId){
		try{
			menuService.delete(menuId);
			return true;
		}catch(Exception e){
			log.error("删除menu失败", e);
			return false;
		}
	}
	//////////////////////////
	// acl start
	//////////////////////////
	
	@RequestMapping("/add-acl")
	public String addAcl(@RequestParam(value="menuId",defaultValue="")Long menuId, ModelMap model) {
		//Menu menu = new Menu(menuId);
		SecAcl acl = new SecAcl();
		acl.setMenuId(menuId);
		model.addAttribute("acl", acl);
		return "security/menu/acl-form";
	}
	
	@RequestMapping("/update-acl")
	public String updateAcl(@RequestParam("aclId")Long aclId, ModelMap model) {
		model.addAttribute("acl", menuService.findAcl(aclId));
		return "security/menu/acl-form";
	}
	
	@RequestMapping("/save-acl")
	@ResponseBody
	public Result saveAcl(SecAcl acl){
		try{
			/*if(menu.getParent().getId() == null){
				menu.setParent(null);
			}*/
			menuService.saveAcl(acl);
			Result cm = new  Result(true);
			cm.setObject(UrlResource.aclToUrlResource(acl));
			return cm;
		}catch(Exception e){
			log.error("保存mAcl失败", e);
			return new Result(false,"未知错误");
		}
	}
	
	@RequestMapping("/delete-acl")
	@ResponseBody
	public boolean deleteAcl(@RequestParam("aclId")Long aclId){
		try{
			menuService.deleteAcl(aclId);
			return true;
		}catch(Exception e){
			log.error("删除Acl失败", e);
			return false;
		}
	}
}
