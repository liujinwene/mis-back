package com.sec.security.tag;

import java.util.List;

import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

import com.sec.mis.lang.XArrayUtils;
import com.sec.security.model.SecAcl;
import com.sec.security.model.SecMenu;
import com.sec.security.model.SecRole;
import com.sec.security.model.SecUser;
import com.sec.security.utils.SessionUtils;

/***
 *
 *
 */
public class UrlAuthorizeTag extends BodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2676797861298990138L;
	
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public int  doStartTag(){
		SecUser user = SessionUtils.getUser();
		if(user==null || StringUtils.isEmpty(url)){
			return SKIP_BODY;
		}
		List<SecRole> roles = user.getRoles();
		if(XArrayUtils.isNotEmpty(roles)){
			for(SecRole role:roles){
				List<SecMenu> menus = role.getMenus();
				if(XArrayUtils.isNotEmpty(menus)){
					for(SecMenu menu:menus){
						if(StringUtils.equals(menu.getLocation(), url)){
							return EVAL_BODY_INCLUDE;
						}
					}
				}
				///////
				List<SecAcl> acls = role.getAcls();
				if(XArrayUtils.isNotEmpty(acls)){
					for(SecAcl acl:acls){
						if(StringUtils.equals(acl.getPattern(), url)){
							return EVAL_BODY_INCLUDE;
						}
					}
				}
			}
		}
		return SKIP_BODY; 
	}
}
