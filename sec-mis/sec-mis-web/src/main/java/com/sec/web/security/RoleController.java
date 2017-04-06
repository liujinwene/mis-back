package com.sec.web.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sec.mis.file.FileDownloadUtil;
import com.sec.mis.page.Page;
import com.sec.mis.poi.ExcelEntity;
import com.sec.mis.util.Result;
import com.sec.security.model.SecAcl;
import com.sec.security.model.SecMenu;
import com.sec.security.model.SecRole;
import com.sec.security.service.MenuService;
import com.sec.security.service.RoleService;
import com.sec.web.security.vo.UrlResource;
import com.sec.web.vo.SearchParam;
 

@Controller
@RequestMapping("/role")
public class RoleController {
	
	private static final Log log = LogFactory.getLog(RoleController.class);
	
	@Resource
	private RoleService roleService;
	@Resource
	private MenuService menuService;
	
	@RequestMapping("/list")
	public String list() {
		return "security/role/list";
	}
	@RequestMapping("/list-data")
	@ResponseBody
	public Page<SecRole> findListData(SearchParam sp) {
		Map<String, Object> param = this.genSecRoleSearchParam(sp);
		int start = sp.getStart();
		int limit = sp.getLimit();
		
		Page<SecRole> roles = roleService.findRoles4Page(param, start, limit, null);
		return roles;
	}
	
	@RequestMapping("/add")
	public String add(ModelMap model) {
		return "security/role/form";
	}
	
	@RequestMapping("/update")
	public String update(@RequestParam("roId")Long roleId,ModelMap model) {
		model.addAttribute("role", roleService.findRole(roleId,false));
		return "security/role/form";
	}
	@RequestMapping("/save")
	@ResponseBody
	public Result save(SecRole role,@RequestParam(value="menuIds[]",defaultValue="")Long[] menuIds,@RequestParam(value="aclIds[]",defaultValue="")Long[] aclIds){
		try{
			if(role.getId() == null){
				List<SecRole> exits = roleService.findByCode(role.getCode());
				if(exits!=null && exits.size()>0){
					return  new Result(false,"code已经存在"); 
				}
			}
			roleService.saveRole(role, menuIds, aclIds);
			Result res = new Result(true);
			res.setObject(role);
			return res;
		}catch(Exception e){
			log.error("保存role失败", e);
			return new Result(false,"未知错误");
		}
	}
	@RequestMapping("/delete")
	@ResponseBody
	public boolean delete(@RequestParam(value = "roleIds[]") Long[] roleIds){
		try{
			roleService.deleteRole(roleIds);
			return true;
		}catch(Exception e){
			log.error("删除role失败", e);
			return false;
		}
	}
	
	@RequestMapping("/menu")
	@ResponseBody
	public List<UrlResource> findMenuByRoleId(@RequestParam("roleId")Long roleId){
		List<SecMenu> menus = menuService.findMenus(false);
		List<SecAcl> acls = menuService.listAcls(false);
		SecRole role = roleService.findRole(roleId,true);
		List<UrlResource> urls = toUrlResource(menus, acls,role);
		return urls;//new DataGridPage(urls.size(), urls);
	}
	
	private List<UrlResource> toUrlResource(List<SecMenu> menus,List<SecAcl> acls,SecRole role){
		List<UrlResource> urls = new ArrayList<UrlResource>();
		List<SecMenu> roleMenus = role.getMenus();
		List<SecAcl> roleAcls = role.getAcls();
		///////
		if(CollectionUtils.isNotEmpty(menus)){
			for(SecMenu menu:menus){
				UrlResource url = UrlResource.menuToUrlResource(menu);
				url.setChecked(isMenuChecked(menu,roleMenus));
				urls.add(url);
			}
		}
		if(CollectionUtils.isNotEmpty(acls)){
			for(SecAcl acl:acls){
				UrlResource url = UrlResource.aclToUrlResource(acl);
				url.setChecked(isAclChecked(acl, roleAcls));
				urls.add(url);
			}
			
		}
		return urls;
	}
	private boolean isMenuChecked(SecMenu menu, List<SecMenu> roleMenus){
		if(CollectionUtils.isNotEmpty(roleMenus)){
			for(SecMenu roleMenu:roleMenus){
				if(menu.getId().equals(roleMenu.getId())){
					return true;
				}
			}
		}
		return false;
	}
	
	private boolean isAclChecked(SecAcl acl,List<SecAcl> roleAcls){
		if(CollectionUtils.isNotEmpty(roleAcls)){
			for(SecAcl roleAcl:roleAcls){
				if(acl.getId().equals(roleAcl.getId())){
					return true;
				}
			}
		}
		return false;
		
	}
	
	/**
	 * 查询和导出 条件
	 * @param sp
	 * @return
	 */
	public Map<String, Object> genSecRoleSearchParam (SearchParam sp){
		Map<String, Object> param = sp.getSp();
		return param;
	}
	
	/**
     *  导出
     * @param sp
     * @param request
     * @param response
     */
    @RequestMapping("/exportSecRoleData")  
    @ResponseBody
    public void exportSecRoleData(SearchParam sp,HttpServletRequest request,HttpServletResponse response){
    	//获取查询参数
    	Map<String, Object> param = this.genSecRoleSearchParam(sp);
    	
    	//获取数据
    	List<SecRole> dataList = this.roleService.listRoles(param);
    	
    	//初始化参数
    	ExcelEntity excelEntity = FileDownloadUtil.initExcelEntity(request, "sec_role_", ".xls");
    	
    	//生成excel需要的数据
    	
    	this.roleService.genSecRoleExcel(dataList, excelEntity);

    	//输出
    	FileDownloadUtil.outputFileForDownload(response, excelEntity);
    }
}
