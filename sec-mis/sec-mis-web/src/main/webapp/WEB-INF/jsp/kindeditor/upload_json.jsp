<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.apache.commons.vfs2.FileObject" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.io.IOUtils" %>
<%@ page import="org.springframework.web.bind.*" %>
<%@ page import="org.springframework.web.multipart.*" %>
<%@ page import="org.springframework.stereotype.Controller" %>
<%@ page import="org.json.simple.*" %>
<%@ page import="com.sec.mis.util.SysConf" %>
<%@ page import="com.sec.mis.vfs.WFS" %> 
<%
/**
 * KindEditor JSP
 * 
 * 本JSP程序是演示程序，建议不要直接在实际项目中使用。
 * 如果您确定直接使用本程序，使用之前请仔细确认相关安全设置。
 * 
 */
 /*
 boolean isMultipart = ServletFileUpload.isMultipartContent(request);//检查输入请求是否为multipart表单数据。
	JSONObject obj = new JSONObject();
 if (!isMultipart) {
    return "";
 } 
	FileObject file = null;
	CommonsMultipartFile uploadFile = null;
	try {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		uploadFile = (CommonsMultipartFile) multipartRequest.getFile("imgFile");// 表单中对应的文件名；
		String filenameSuffix = uploadFile.getOriginalFilename().substring  
				(uploadFile.getOriginalFilename().lastIndexOf("."));//文件名后缀
		String imgName = DateUtil.getCurrentDateTimeStr1()+"_"+(new Random().nextInt(10))+filenameSuffix;
		// server folder
		file = WFS.resolveFile("mall://goods/textEidt/"+imgName);
		// server empty file
		
		//数据库存  goods/img.jpg
		if (!file.exists()) file.createFile();
		
		// copy upload file to server file
		int rows = IOUtils.copy(uploadFile.getInputStream(), file.getContent().getOutputStream());
		if (rows > 0) {
			Result result = new Result();
			//获取文件上传全路径
			String url = SysConf.get("file.root.mall.http")+"/goods/textEidt/"+imgName;
			obj.put("error", 0);
			obj.put("url", url);
		} else {
			obj.put("error", 0);
			obj.put("message", "上传失败");
		}
	} finally {
		IOUtils.closeQuietly(uploadFile.getInputStream());
		WFS.closeQuietly(file);
	}
	out.println(obj.toJSONString());*/
%>
<%!
private String getError(String message) {
	JSONObject obj = new JSONObject();
	obj.put("error", 1);
	obj.put("message", message);
	return obj.toJSONString();
}
%>