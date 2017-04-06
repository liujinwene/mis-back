package com.sec.mis.file;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class WebUtils
{
    public static void initQueryMap(Map<String, Object> param,String property,String timeStr,boolean isDate) {
        String value = (String)param.get(property);
        if(value!=null && value.trim()!="") {
            if(isDate) {
                param.put(property, value+timeStr);
            }else {
                param.put(property, value);
            }
        }else {
            param.put(property, null);
        }
    }
    
    /**
     * 获取web项目的URL,例如：http://localhost:8080/xyb-mis-web
     * @param request
     * @return
     */
    public static String getWebProjectUrl(HttpServletRequest request){
    	 String projectUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                 + request.getContextPath() ;
    	 return projectUrl;
    }
}
