package com.sec.mis.file;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;

public class FileUtils {

	private static int getRecommandImageIndex() {
		Date now = new Date();
		Random random = new Random(now.getTime());
		int mode = random.nextInt()%3;
		mode = mode <0 ? mode*(-1) : mode;
		return mode+1;
	}
	
	/**
	 * 获取随机名字的头像
	 * @return
	 */
	public static String getRecommandImageName() {
		return "newHeader" + getRecommandImageIndex() + ".jpg";
	}
	
	public static boolean isFileExsited(String path) {
		File file = new File(path);
		return file.exists();
	}

	public static void mkParent(String path) {
		File dir = new File(path);
		String parent = dir.getParent();
		mkdir(parent);
	}

	public static void mkdir(String path) {		
		File dir = new File(path);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}
	
	/**
	 * 单位转换
	 * @param bytes
	 * @return
	 */
	 public static String formatFileFize(long bytes){
		 BigDecimal   bd   =   null;
		 if(bytes >= 1000000000){
			 bd = new BigDecimal(bytes/1000000000);
			 return  bd.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue() + " GB";
		 }else if(bytes >= 1000000){
			 bd = new BigDecimal(bytes/1000000); 
			 return  bd.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue() + " MB";
		 }
		 bd = new BigDecimal(bytes/1000); 
		 return  bd.setScale(2,   BigDecimal.ROUND_HALF_UP).doubleValue() + " KB";
	 }
	 
	public static String convertFileSize(long size){
		if(size/(1024*1024*1024)>=1){
			return size/(1024*1024*1024)+" GB";
		}else if(size/(1024*1024)>=1){
			return size/(1024*1024)+" MB";
		}else if(size/1024>1){
			return size/1024+" KB";
		}else if(size>0){//小于1KB通用1KB
			return "1.0 KB";
		}else{
			return "0 KB";
		}
	}
	
	
}
