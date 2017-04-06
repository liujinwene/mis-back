package com.sec.web;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.ui.ModelMap;

public abstract class BaseController {

	protected Map<String, String> getParams(HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		try{
			Enumeration<String> keys = request.getParameterNames();
			if(keys==null){
				return params;
			}
			while(keys.hasMoreElements()){
				String key = keys.nextElement();
				String value = new String(((String)request.getParameter(key)).getBytes("ISO-8859-1"), "UTF-8");
				if(StringUtils.isNotEmpty(value)){
					params.put(key, value);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return params;
	}
	
	protected void paramModel(HttpServletRequest request,ModelMap model) {
		try{
			Enumeration<String> keys = request.getParameterNames();
			if(keys==null){
				return;
			}
			while(keys.hasMoreElements()){
				String key = keys.nextElement();
				String value = new String(((String)request.getParameter(key)).getBytes("ISO-8859-1"), "UTF-8");
				if(StringUtils.isNotEmpty(value)){
					model.addAttribute(key, value);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
