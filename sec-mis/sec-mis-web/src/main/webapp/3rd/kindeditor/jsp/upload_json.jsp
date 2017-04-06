<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*,java.io.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.json.simple.*" %>
<%@ page import="org.apache.commons.vfs2.FileObject" %>
<%@ page import="com.sec.mis.util.SysConf" %>
<%@ page import="com.sec.mis.vfs.WFS" %>
<%@ page import="org.apache.commons.io.IOUtils" %>
 
<%

/**
 * KindEditor JSP
 * 
 * 本JSP程序是演示程序，建议不要直接在实际项目中使用。
 * 如果您确定直接使用本程序，使用之前请仔细确认相关安全设置。
 * 
 */
 String IMG_UPLOAD_HTTP = "file.root.mall.http";
 String IMG_UPLOAD_PATH = "file.root.mall.path";
//文件保存目录路径
String savePath = SysConf.get(IMG_UPLOAD_PATH)+"/goods/";
//文件保存目录URL
String saveUrl  = SysConf.get(IMG_UPLOAD_HTTP)+"/goods/";

//定义允许上传的文件扩展名
HashMap<String, String> extMap = new HashMap<String, String>();
extMap.put("image", "gif,jpg,jpeg,png,bmp");
extMap.put("flash", "swf,flv");
extMap.put("media", "swf,flv,mp3,wav,wma,wmv,mid,avi,mpg,asf,rm,rmvb");
extMap.put("file", "doc,docx,xls,xlsx,ppt,htm,html,txt,zip,rar,gz,bz2");

//最大文件大小
long maxSize = 1000000;

response.setContentType("text/html; charset=UTF-8");

if(!ServletFileUpload.isMultipartContent(request)){
	out.println(getError("请选择文件。"));
	return;
}
 
String dirName = request.getParameter("dir");
if (dirName == null) {
	dirName = "image";
}
if(!extMap.containsKey(dirName)){
	out.println(getError("目录名不正确。"));
	return;
}
//创建文件夹
savePath += dirName + "/";
saveUrl += dirName + "/";
File saveDirFile = new File(savePath);
if (!saveDirFile.exists()) {
	saveDirFile.mkdirs();
}
SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
String ymd = sdf.format(new Date());
savePath += ymd + "/";
saveUrl += ymd + "/";
File dirFile = new File(savePath);
if (!dirFile.exists()) {
	dirFile.mkdirs();
}

FileItemFactory factory = new DiskFileItemFactory();
ServletFileUpload upload = new ServletFileUpload(factory);
upload.setHeaderEncoding("UTF-8");
List items = upload.parseRequest(request);
Iterator itr = items.iterator();
while (itr.hasNext()) {
	FileItem item = (FileItem) itr.next();
	String fileName = item.getName();
	long fileSize = item.getSize();
	if (!item.isFormField()) {
		//检查文件大小
		if(item.getSize() > maxSize){
			out.println(getError("上传文件大小超过限制。"));
			return;
		}
		//检查扩展名
		String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
		if(!Arrays.<String>asList(extMap.get(dirName).split(",")).contains(fileExt)){
			out.println(getError("上传文件扩展名是不允许的扩展名。\n只允许" + extMap.get(dirName) + "格式。"));
			return;
		}

		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String newFileName = df.format(new Date()) + "_" + new Random().nextInt(1000) + "." + fileExt;
		FileObject file = null;
		try{
			// server folder
			file = WFS.resolveFile("mall://goods/textEidt/"+newFileName);
			// server empty file
			
			//数据库存  goods/img.jpg
			if (!file.exists()) file.createFile();
			// copy upload file to server file
			int rows = IOUtils.copy(item.getInputStream(), file.getContent().getOutputStream());
			
			//File uploadedFile = new File(savePath, newFileName);
			//item.write(uploadedFile);
			if(rows > 0){
				//获取文件上传全路径
				String url = SysConf.get(IMG_UPLOAD_HTTP)+"/goods/textEidt/"+newFileName;
				JSONObject obj = new JSONObject();
				obj.put("error", 0);
				obj.put("url", url);
				out.println(obj.toJSONString());
			}else{
				out.println(getError("上传文件失败。"));
				return;
			}
			
		}catch(Exception e){
			out.println(getError("上传文件失败。"));
			return;
		}finally {
			IOUtils.closeQuietly(item.getInputStream());
			WFS.closeQuietly(file);
		} 
	}
}
%>
<%!
private String getError(String message) {
	JSONObject obj = new JSONObject();
	obj.put("error", 1);
	obj.put("message", message);
	return obj.toJSONString();
}
%>