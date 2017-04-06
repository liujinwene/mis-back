package com.sec.mis.file;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.FileUtil;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.sec.mis.lang.UuidUtils;
import com.sec.mis.poi.ExcelEntity;
import com.sec.mis.poi.ExcelUtils;
import com.sec.mis.vfs.WFS;

/**
 * 文件下载
 * @author BYX
 *
 */
public class FileDownloadUtil {
	  private static String FILENAME_PREFIX = "prefix";
	  private static String FILENAME_POSTFIX = "postfix";
	  
	  public static String getRealPathForDownload(HttpServletRequest request){
		  String filePath = "/WEB-INF/xls";
		  String realPath = request.getSession().getServletContext()
					.getRealPath(filePath);
			File folder = new File(realPath);
			if(!folder .exists()  && !folder .isDirectory()){
				folder.mkdirs();	
			}
			return realPath;
	  }
	  
	  public static ExcelEntity initExcelEntity(HttpServletRequest request,String prefix,String postfix){
		  ExcelEntity excelEntity = new ExcelEntity();
		  String realPath = FileDownloadUtil.getRealPathForDownload(request);
		  String filePathAndName = realPath + File.separator +prefix+UuidUtils.getUuidTrimHyphen()+postfix;
		  excelEntity.setFilePathAndName(filePathAndName);
		  Map<String,Object> param = new HashMap<String,Object>();
		  param.put(FILENAME_PREFIX, prefix);
		  param.put(FILENAME_POSTFIX, postfix);
		  excelEntity.setParam(param);
		  return excelEntity;
	  }
	  
	
	/**
	   * 输出文件
	   * @param filePathAndName
	   * @param attachmentName
	   * @param response
	   */
	  public static void outputFileForDownload(HttpServletResponse response,ExcelEntity excelEntity){
		  boolean result = ExcelUtils.writeExcel2003(excelEntity);
		  if(result){
			  OutputStream os = null;
			  String filePathAndName = excelEntity.getFilePathAndName();
			  Map<String,Object> paramMap = excelEntity.getParam();
			  String prefix =  (String)paramMap.get(FILENAME_PREFIX);
			  String postfix = (String)paramMap.get(FILENAME_POSTFIX);
			  File downloadFile = new File(filePathAndName);
				try{
					response.setContentType("application/vnd.ms-excel;charset=utf-8");
					SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//
					String dateStr = df.format(new Date());
					String fileName = prefix+dateStr+postfix;
					response.addHeader("Content-Disposition", "attachment;filename=" +fileName);
					os = response.getOutputStream();  
					os.write(FileUtils.readFileToByteArray(new File(filePathAndName)));  
				    os.flush(); 
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					 if (os != null) {  
				            try {
								os.close();
							} catch (IOException e) {
								e.printStackTrace();
							}  
				        } 
					 downloadFile.delete();
				}
		  }
	  }
  
	  
//	  /**
//		 *  个人中心--我的投资--合同下载
//		 * @param req
//		 * @param res
//		 * @param projectType
//		 * @param agreementId
//		 * @param projectId
//		 * @return
//		 */
//		@LoginHandler
//		@ResponseBody
//		@RequestMapping("contractDownload")
//		public String contractDownload(HttpServletRequest req, HttpServletResponse res,ProjectType projectType, long agreementId,long projectId) {
//			LoginUser loginUser = LoginUserHolder.get();
//			long userId = loginUser.getId();
//			Agreement agreement = projectService.getAgreement(projectType,agreementId,userId,projectId);
//			if(agreement==null) return null;
//			if(userId != agreement.getUserId()){
//				return null;
//			}
//			String[] strs = {agreement.getPdfURL()};
//			try {
//				String root = this.getClass().getClassLoader().getResource("/").getPath();
//				String zipName = writeZip(strs,String.valueOf(agreement.getOrderId()),root);
//				this.pushFile2client(req,res,zipName);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			return null;
//		}
	  
	  private static String writeZip(String[] strs,String zipname,String root) throws IOException {
			String[] files = strs;
	        String zipName = root+"/"+zipname+".zip";
	        OutputStream os = new BufferedOutputStream( new FileOutputStream( zipName ) );
	        ZipOutputStream zos = new ZipOutputStream( os );
	        byte[] buf = new byte[8192];
	        int len;
	        for (int i=0;i<files.length;i++) {
	        	
	        	if(StringUtils.isBlank(files[i])) continue;
	        	
	            FileObject fileObject = WFS.resolveFile(files[i]);
	            String fileName = files[i].substring(files[i].lastIndexOf("/"));
	            File file = new File(root+"/"+fileName);
	            FileUtils.writeByteArrayToFile(file, FileUtil.getContent(fileObject));
	            if ( !file.isFile() ) continue;
	            ZipEntry ze = new ZipEntry( file.getName() );
	            zos.putNextEntry( ze );
	            BufferedInputStream bis = new BufferedInputStream( new FileInputStream( file ) );
	            while ( ( len = bis.read( buf ) ) > 0 ) {
	                zos.write( buf, 0, len );
	            }
	            bis.close();
	            zos.closeEntry();
	            file.delete();
	        }
	        zos.setEncoding("UTF-8");
	        zos.closeEntry();
	        zos.close();
	        return zipName;
	        
//	        for(int i=0;i<files.length;i++){
//	         System.out.println("------------"+files );
//	         File file= new File(files );
//	         file.delete();
//	        }
	    }
}
