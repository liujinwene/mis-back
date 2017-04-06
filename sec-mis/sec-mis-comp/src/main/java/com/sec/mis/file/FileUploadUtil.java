package com.sec.mis.file;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.sec.mis.lang.UuidUtils;
import com.sec.mis.poi.ExcelEntity;
import com.sec.mis.poi.ExcelSheet;
import com.sec.mis.poi.ExcelUtils;
import com.sec.mis.util.Result;

/**
 * 文件上传
 * @author BYX
 *
 */
public class FileUploadUtil {
	public static final String FILE_UPLOAD_ROOT_PATH = "/fileupload";	//文件上传的根目录
	public static final String FILE_UPLOAD_TEMPLATE = "/template";	//模板文件的相对目录
	public static final String FILE_UPLOAD_ERROR	=	"/error";	//错误文件的路径
	private static final Log log = LogFactory.getLog(FileUploadUtil.class);
	
	/**
	 * 获取具体文件路径
	 * @param request
	 * @param dir
	 * @return
	 */
	public static String getRealPathForUpload(HttpServletRequest request,String dir){
		  String filePath = FileUploadUtil.FILE_UPLOAD_ROOT_PATH+dir;
		  String realPath = request.getSession().getServletContext()
					.getRealPath(filePath);
			File folder = new File(realPath);
			if(!folder .exists()  && !folder .isDirectory()){
				folder.mkdirs();	
			}
			return realPath;
	  }
	
	
	/**
	 * 生成错误文件内容，以供下载
	 * @param request
	 * @param errorFileName
	 * @param errorMsg
	 * @return
	 */
	public static String genErrorFileInfo(HttpServletRequest request,String errorFileName,String errorMsg){
		
		String projectUrl = WebUtils.getWebProjectUrl(request);
	    String errorFileForWeb = projectUrl+FileUploadUtil.FILE_UPLOAD_ROOT_PATH+FileUploadUtil.FILE_UPLOAD_ERROR+"/"+errorFileName;
	    String errorFileForReal = FileUploadUtil.getRealPathForUpload(request, FileUploadUtil.FILE_UPLOAD_ERROR)+File.separator+errorFileName;
	    try {
			IOUtils.write(errorMsg, new FileOutputStream(new File(errorFileForReal)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return errorFileForWeb;
	}
	
	/**
	 * 生成物理路径
	 * @param request
	 * @param errorFileName
	 * @return
	 */
	public static String getPathForReal(HttpServletRequest request,String errorFileName){
		return FileUploadUtil.getRealPathForUpload(request, FileUploadUtil.FILE_UPLOAD_ERROR)+File.separator+errorFileName;	
	}
	
	/**
	 * 生成web路径
	 * @param request
	 * @param errorFileName
	 * @return
	 */
	public static String getPathForWeb(HttpServletRequest request,String errorFileName){
		return WebUtils.getWebProjectUrl(request)+FileUploadUtil.FILE_UPLOAD_ROOT_PATH+FileUploadUtil.FILE_UPLOAD_ERROR+"/"+errorFileName;
	}
	
	/**
	 * 对excel进行默认的设置
	 * 默认是第1行是标题，第2行开始是数据
	 */
	public static void initExcelEntity(ExcelEntity excelEntity){
		Map titleRowMap = new HashMap();
	    titleRowMap.put(0, 0);
	    excelEntity.setTitleRowMap(titleRowMap);
	    
	    Map contentRowMap = new HashMap();
	    contentRowMap.put(0, 1);
	    excelEntity.setContentRowMap(contentRowMap);
	}
	
	/**
	 * 获取上传的文件信息
	 * @param request
	 * @return
	 */
	public static MultipartFile getMultipartFile(HttpServletRequest request){
		return ((MultipartHttpServletRequest) request).getFile("uploadfile");
	}
	
	/**
	 * 获取解析后的excel实体
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public static ExcelEntity getExcelEntity(HttpServletRequest request) throws Exception {
		MultipartFile multiFile = FileUploadUtil.getMultipartFile(request);
		ExcelEntity excelEntity = new ExcelEntity();
		excelEntity.setInputStream(multiFile.getInputStream());
	    excelEntity.initExcelEntity();
	    ExcelUtils.parseExcel(excelEntity);
	    log.info("\n excelEntity:"+excelEntity);
	    
	    return excelEntity;
	}
	
	/**
	 * 生成导出结果
	 * @param excelEntity
	 * @param result
	 * @param filePathAndName
	 */
	public static void genExcelResult(ExcelEntity excelEntity,ImportResult result, String filePathAndName) {
		ExcelEntity excelEntity2 = new ExcelEntity();
        excelEntity2.setFilePathAndName(filePathAndName);
        ExcelSheet excelSheet = excelEntity.getSheetList().get(0);
        
        List<List<String>> dataList  = excelEntity.getSheetList().get(0).getContentList();
        
        List<Result> resultList = new ArrayList<Result>();
        if(null != result && null != (result.getObject() )){
        	resultList = ((List<Result>)result.getObject());
        }
    
        List<ExcelSheet> sheetList = new ArrayList<ExcelSheet>();
       // ExcelSheet excelSheet = new ExcelSheet();
        if (null != dataList && dataList.size() > 0){
        	excelSheet.setSheetName("结果信息(" + dataList.size() + ")");
        }else{
        	excelSheet.setSheetName("结果信息(0)");
        }
        
        
        List<String> titleList = excelSheet.getTitleList();
        if(null !=titleList && titleList.size()>0 ){
        	  titleList.add("结果");
              titleList.add("备注");
              excelSheet.setTitleList(titleList);
              int[] columnWidth = new int[titleList.size()];
              for(int i=0;i<columnWidth.length-1;i++){
            	  columnWidth[i] = 6000;
              }
              columnWidth[columnWidth.length-1] = 30000; //备注需要宽一点
              excelSheet.setColumnWidth(columnWidth);
        }
        
        if (null != dataList && dataList.size() > 0){
        	
            if (null != dataList && dataList.size() > 0 && null != excelEntity2){
                List<List<String>> contentList = new ArrayList<List<String>>();
                int index = 0 ;
                for (List<String> item : dataList){
                    List<String> contentItem = item;
                   
                    if(null != resultList && resultList.size()>0 && resultList.size()>= index){
                    	Result resultItemn  = resultList.get(index);
                    	
                    	if(resultItemn.getSuccess()){
                    		contentItem.add("成功");
                    	}else{
                    		contentItem.add("失败");
                    	}
	                    contentItem.add(resultItemn.getMessage());
                    }else{
                    	contentItem.add("失败");
                    	contentItem.add("");
                    }
                    
                    contentList.add(contentItem);
                    index++;
                }
                excelSheet.setContentList(contentList);
            }
        }
        sheetList.add(excelSheet);
        excelEntity2.setSheetList(sheetList);
        ExcelUtils.writeExcel2003(excelEntity2);
	}
	
	/**
	 * 生成结果，目前主要是错误时才生成，每条记录都有结果（成功或者失败，失败时还有失败的原因）
	 * @param request
	 * @param excelEntity
	 * @param result
	 */
	public static void genImportResult(HttpServletRequest request,ExcelEntity excelEntity,ImportResult result){
		String errorFileName = "import_result_"+UuidUtils.getUuidTrimHyphen()+".xls";
		FileUploadUtil.genExcelResult(excelEntity, result, FileUploadUtil.getPathForReal(request, errorFileName));
		result.setFilePath(FileUploadUtil.getPathForWeb(request, errorFileName));
	}
}
