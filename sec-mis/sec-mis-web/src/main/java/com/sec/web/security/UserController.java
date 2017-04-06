package com.sec.web.security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sec.mis.file.FileDownloadUtil;
import com.sec.mis.page.Page;
import com.sec.mis.poi.ExcelEntity;
import com.sec.mis.util.Result;
import com.sec.security.model.SecRole;
import com.sec.security.model.SecUser;
import com.sec.security.service.RoleService;
import com.sec.security.service.UserService;
import com.sec.security.utils.SessionUtils;
import com.sec.web.vo.SearchParam;
 

@Controller
@RequestMapping("/user")
public class UserController {
	private static final Log log = LogFactory.getLog(UserController.class);
	
	@Resource
	private UserService userService;
	
	@Resource
	private RoleService roleService;
	
	@Resource
	private PasswordEncoder passwordEncoder;
	
	@RequestMapping("/list")
	public String list() {
		return "security/user/list";
	}
	@RequestMapping("/list-data")
	@ResponseBody
	public Page<SecUser> findListData(SearchParam sp){
		Map<String, Object> param = this.genSecUserSearchParam(sp);
		int start = sp.getStart();
		int limit = sp.getLimit();
		Page<SecUser> users = userService.findUsers(param, start, limit, null);
		return users;//new DataGridPage(users.getItemSize(),users.getItems());
	}
	
	@RequestMapping("/add")
	public String add(ModelMap model) {
		return "security/user/form";
	}
	
	@RequestMapping("/update")
	public String update(@RequestParam("userId")Long userId,ModelMap model) {
		SecUser user = userService.getUserById(userId);
		model.addAttribute("user", user);
		return "security/user/form";
	}
	
	
	@RequestMapping("/user-role")
	@ResponseBody
	public List<Map<String,Object>> findTreeRole(@RequestParam("userId")Long userId){
		SecUser user = userService.getUserById(userId);
		List<SecRole> userRoles = user.getRoles();
	//	userRoles.size();
		List<SecRole> allRoles = roleService.findRoles();
		List<Map<String,Object>> urls = new ArrayList<Map<String,Object>>();
		if(CollectionUtils.isNotEmpty(allRoles)){
			for(SecRole role:allRoles){
				Map<String,Object> node = new HashMap<String, Object>();
				node.put("id", role.getId());
				node.put("name", role.getName());
				node.put("checked", isRoleChecked(userRoles, role));
				urls.add(node);
			}
		}
		return urls;
	}
	
	private boolean isRoleChecked(List<SecRole> userRoles, SecRole role){
		if(CollectionUtils.isNotEmpty(userRoles)){
			for(SecRole userRole:userRoles){
				if(userRole.getId().equals(role.getId())){
					return true;
				}
			}
		}
		return false;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public Result save(SecUser user,@RequestParam(value="roleIds[]",defaultValue="")Long[] roleIds,@RequestParam(value="channelIds[]",defaultValue="")Integer[] channelIds){
        try
        {
            SecUser exits = userService.getUserByName(user.getUsername());
            if (user.getId() == null && exits != null)
            {
                return new Result(false, exits.getUsername() + "已经存在");
            }
            if (user.getId() != null)
            {
                if (StringUtils.isNotBlank(user.getPassword()))
                {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                } else if (exits != null && StringUtils.isNotBlank(exits.getPassword()))
                {
                    user.setPassword(exits.getPassword());
                }
                if (StringUtils.isNotBlank(user.getAduitPass()))
                {
                    user.setAduitPass(passwordEncoder.encode(user.getAduitPass()));
                } else if (exits != null && StringUtils.isNotBlank(exits.getAduitPass()))
                {
                    user.setAduitPass(exits.getAduitPass());
                }
                if (exits != null)
                {
                    user.setCreateTime(exits.getCreateTime());
                    user.setLastLoginTime(exits.getLastLoginTime());
                }
            } else
            {
                if (StringUtils.isNotBlank(user.getAduitPass()))
                {
                    user.setAduitPass(passwordEncoder.encode(user.getAduitPass()));
                }
            }

            userService.saveUser(user,channelIds,roleIds);
            Result res = new Result(true);
            res.setObject(user);
            return res;
        }catch(Exception e){
			log.error("保存user失败", e);
			return new Result(false,"未知错误");
		}
	}
	@RequestMapping("/delete")
	@ResponseBody
	public boolean delete(@RequestParam(value = "userIds[]") Long[] userIds){
		try{
			userService.deleteUserByIds(userIds);
			return true;
		}catch(Exception e){
			log.error("删除user失败", e);
			return false;
		}
	}
	
	@RequestMapping("/change-pwd")
	public String changePwd(ModelMap model) {
		Long userId = SessionUtils.getUser().getId();
		SecUser user = userService.getUserById(userId);
		model.addAttribute("user", user);
		return "security/user/change-pwd";
	}
	@RequestMapping("/save-pwd")
	@ResponseBody
	public Result savePwd(@RequestParam(value="newPassword")String newPassword,@RequestParam(value="oldPassword")String oldPassword){
		try{
			Long userId = SessionUtils.getUser().getId();
			
			SecUser user = userService.getUserById(userId);
			if(!passwordEncoder.matches(oldPassword, user.getPassword())){
				return new Result(false,"原始密码错误");
			}
			user.setPassword(passwordEncoder.encode(newPassword));
			userService.saveUser(user, null);
			Result res = new Result(true,"修改密码成功！");
			return res;
		}catch(Exception e){
			log.error("修改密码失败", e);
			return new Result(false,"修改密码失败");
		}
	}
	
	/**
	 * 查询和导出 条件
	 * @param sp
	 * @return
	 */
	public Map<String, Object> genSecUserSearchParam (SearchParam sp){
		Map<String, Object> param = sp.getSp();
		return param;
	}
	
	/**
     *  导出
     * @param sp
     * @param request
     * @param response
     */
    @RequestMapping("/exportSecUserData")  
    @ResponseBody
    public void exportSecUserData(SearchParam sp,HttpServletRequest request,HttpServletResponse response){
    	//获取查询参数
    	Map<String, Object> param = this.genSecUserSearchParam(sp);
    	
    	//获取数据
    	List<SecUser> dataList = this.userService.listUsers(param);
    	
    	//初始化参数
    	ExcelEntity excelEntity = FileDownloadUtil.initExcelEntity(request, "sec_user_", ".xls");
    	
    	//生成excel需要的数据
    	
    	this.userService.genSecUserExcel(dataList, excelEntity);

    	//输出
    	FileDownloadUtil.outputFileForDownload(response, excelEntity);
    }
}
