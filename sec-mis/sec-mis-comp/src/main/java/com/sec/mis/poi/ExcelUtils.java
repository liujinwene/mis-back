package com.sec.mis.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.StringUtils;

/**
 * excel工具类，主要用于导入，导出
 * @author BYX
 *
 */
public class ExcelUtils {
	private static final int DEFAULT_PER_SHEET_COUNT = 2000;//每个sheet可容纳总行数
	private static final Log log = LogFactory.getLog(ExcelUtils.class);
	static SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");

	public static ExcelEntity parseExcel(ExcelEntity excelEntity){
		if(null !=excelEntity){
			 try {
				 
				Workbook book = null;
				//先从文件输入流中获取，如果获取不到，再从文件路径中获取
				if(null != excelEntity.getInputStream()){
					book = getExcelWorkbook(excelEntity.getInputStream());
				}
				if(null != book && !StringUtils.isEmpty(excelEntity.getFilePathAndName())){
					book =  getExcelWorkbook(excelEntity.getFilePathAndName());
				}
			 
				
				if(null != book){
					int sheetCount = book.getNumberOfSheets();
					List<ExcelSheet> sheetList = new ArrayList<ExcelSheet>();
					for(int i=0;i<sheetCount;i++){
						int titleRow = getOptionValue(excelEntity.getTitleRowMap(),i,ExcelSheet.DEFAULT_TITLEROWNUM);
						int dataRow = getOptionValue(excelEntity.getContentRowMap(),i,ExcelSheet.DEFAULT_CONTENTROWNUM);
						ExcelSheet excelSheet = parseSheet(book.getSheetAt(i),titleRow,dataRow);
						sheetList.add(excelSheet);
					}
					excelEntity.setSheetCount(sheetCount);
					excelEntity.setSheetList(sheetList);
					
				}else{
					log.error("parseExcel book为空！");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else{
			log.error("parseExcel excelEntity为空！");
		}
		return null;
	}
	
	/**
	 * 解析sheet
	 * @param sheet
	 * @param titleRowIndex
	 * @param dataRowIndex
	 * @return
	 */
	public static ExcelSheet parseSheet(Sheet sheet,int titleRowIndex,int dataRowIndex){
		ExcelSheet excelSheet = new ExcelSheet();
		excelSheet.setContentRowNum(dataRowIndex);
		excelSheet.setTitleRowNum(titleRowIndex);
		
		if(null != sheet){
			excelSheet.setSheetName(sheet.getSheetName());
			int lastRowNum = sheet.getLastRowNum();
			excelSheet.setRecordCount(lastRowNum-dataRowIndex+1);
			
			//解析标题行
			List<String> titleList = parseTitleRow(sheet.getRow(0));
		
			excelSheet.setTitleList(titleList);
			if(null != titleList){
				excelSheet.setColumnCount(titleList.size());
				//解析数据行
				List<List<String>> contentList = new ArrayList<List<String>>();
				Row row = null;
				
				for(int i=dataRowIndex;i<=lastRowNum;i++){
					row = sheet.getRow(i);
				    List<String> rowList =parseSheetRow(row,titleList.size());
				    contentList.add(rowList);
				    excelSheet.setContentList(contentList);
				   
				  }
			}
			  
		}else{
			log.error("parseSheet sheet为空！");
		}
		return excelSheet;
	}
	
	/**
	 * 解析excel中的sheet中的一行row
	 * @param row
	 * @return
	 */
	public static List<String> parseTitleRow(Row row){
		List<String> rowList = new ArrayList<String>();
	    if(row != null){
	    	int lastCellNum = row.getLastCellNum();
	    	Cell cell = null;
	    	for(int j=0;j<lastCellNum;j++){
	    		cell = row.getCell(j);
	    		 String value = "";
			    if(cell != null){
			      int type = cell.getCellType();
			      switch (type) {
			        case 0:
			          if(DateUtil.isCellDateFormatted(cell)){
			            Date date = cell.getDateCellValue();
			            value = sFormat.format(date);
			          }else {
			            double tempValue = cell.getNumericCellValue();
			            value = String.valueOf(tempValue);
			          }
			          break;
			        case 1:
			          value = cell.getStringCellValue();
			          break;
			        case 2:
			          value = cell.getCellFormula();
			          break;
			        case 3:
			          value = cell.getStringCellValue();
			          break;
			        case 4:
			          boolean tempValue = cell.getBooleanCellValue();
			          value = String.valueOf(tempValue);
			          break;
			        case 5:
			          byte b = cell.getErrorCellValue();
			          value = String.valueOf(b);
			        default:
			          break;
			      }
			     
	        }//if
			    rowList.add(value.trim());   
	      }//for
	    }
		return rowList;	
	}
	
	

	/**
	 * 解析excel中的sheet中的一行row
	 * @param row
	 * @return
	 */
	public static List<String> parseSheetRow(Row row,int columnSize){
		List<String> rowList = new ArrayList<String>();
	    if(row != null){
	    	int lastCellNum = row.getLastCellNum();
	    	Cell cell = null;
	    	for(int j=0;j<lastCellNum;j++){
	    		
	    		cell = row.getCell(j);
	    		 String value = "";
			    if(cell != null){
			      int type = cell.getCellType();
			      switch (type) {
			        case 0:
			          if(DateUtil.isCellDateFormatted(cell)){
			            Date date = cell.getDateCellValue();
			            value = sFormat.format(date);
			          }else {
			            //double tempValue = cell.getNumericCellValue();
			           // value = String.valueOf(tempValue);
			        	  BigDecimal bd = new BigDecimal(cell.getNumericCellValue());
			        	  value = bd.toString();
			          }
			          break;
			        case 1:
			          value = cell.getStringCellValue();
			          break;
			        case 2:
			          value = cell.getCellFormula();
			          break;
			        case 3:
			          value = cell.getStringCellValue();
			          break;
			        case 4:
			          boolean tempValue = cell.getBooleanCellValue();
			          value = String.valueOf(tempValue);
			          break;
			        case 5:
			          byte b = cell.getErrorCellValue();
			          value = String.valueOf(b);
			        default:
			          break;
			      }
			     
	        }//if
			    rowList.add(value.trim());   
	      }//for
	    	 
	    	if(lastCellNum<columnSize){
	 	    	for(int i=lastCellNum;i<columnSize;i++){
	 	    		rowList.add("");
	 	    	}
	 	    }
	 	    if(lastCellNum>columnSize){
	 	    	rowList = rowList.subList(0, columnSize);
	 	    }
	    	
	    }
		return rowList;	
	}
	

	  /**
	   * 写文件2003
	   * @param excelEntity
	   * @return
	   */
	  public static boolean writeExcel2003(ExcelEntity excelEntity){
		  Workbook book =  new HSSFWorkbook();
		  //return writeExcel(book,excelEntity);
		  return create(book, excelEntity);
	  }
	  
	  /**
	   * 写文件2007
	   * @param excelEntity
	   * @return
	   */
	  public static boolean writeExcel2007(ExcelEntity excelEntity){
		  Workbook book =  new XSSFWorkbook();
		  //return writeExcel(book,excelEntity);
		  return create(book, excelEntity);
	  }
	  
	  /**
	   * 写入excel
	   * @param book
	   * @param excelEntity
	   * @return
	   */
	  public static boolean writeExcel(Workbook book,ExcelEntity excelEntity){
		  boolean isSuccess = false;
		  if(null != excelEntity && null != excelEntity.getSheetList() && excelEntity.getSheetList().size()>0 ){
			  FileOutputStream out = null; 
			  try {
				File file = new File(excelEntity.getFilePathAndName());
				out = new FileOutputStream(file);  
				List<ExcelSheet>  sheetList = excelEntity.getSheetList();
				if(null != sheetList && sheetList.size()>0){
					for(ExcelSheet excelSheet:sheetList){
						
						Sheet sheet = book.createSheet(excelSheet.getSheetName());
						writeSheet(sheet,excelSheet);
					}
					
				}
				book.write(out);
				isSuccess = true;
			} catch (Exception e) {
				e.printStackTrace();
				isSuccess = false;
			}finally {
			      try {
				       out.close();
				      } catch (IOException e) {
				        e.printStackTrace();
				      }
				    }
		  }else{
			  log.error("excelEntity为空！");
		  }
		  
		  return isSuccess;
	  }
	  
	  /**
	   * 创建excel
	   * @param workbook
	   * @param excelEntity
	   * @return
	   */
	  public static boolean create(Workbook workbook,ExcelEntity excelEntity){
		  boolean isSuccess = false;
		  if(null != excelEntity && null != excelEntity.getSheetList() && excelEntity.getSheetList().size()>0 ){
			  FileOutputStream out = null;
			  try {
				File file = new File(excelEntity.getFilePathAndName());
				out = new FileOutputStream(file);  
				List<ExcelSheet>  sheetList = excelEntity.getSheetList();
				List<String> titleList = sheetList.get(0).getTitleList();
				List<List<String>> contentList = sheetList.get(0).getContentList();
				int sheetsNum = getSheetSize(contentList.size(), DEFAULT_PER_SHEET_COUNT);// 获取总页数
				CellStyle bodyCellStyle = getBodyCellStyle(workbook);// 设置单元格样式
				CellStyle headerCellStyle = getHeaderCellStyle(workbook);
				for (int i = 1; i <= sheetsNum; i++) {
					/************************* 创建工作表 ******************/
					int cells = titleList.size();// 总列数
					int begin = (i - 1) * DEFAULT_PER_SHEET_COUNT;// 元素开始位置
					int end = (begin + DEFAULT_PER_SHEET_COUNT) > contentList.size() ? contentList.size() : (begin + DEFAULT_PER_SHEET_COUNT);// 元素结束位置
					
					Sheet sheet = workbook.createSheet(sheetList.get(0).getSheetName() + begin + "-" + end);// 创建sheet
					sheet.setDefaultColumnWidth(30);// 设置默认列宽
					/************************* 创建表头 ******************/
					Row row = sheet.createRow(0);// 创建表头
					row.setHeight((short) (15.625f * 30));// 设置默认行高30px
					for (int headersCount = 0; headersCount < titleList.size(); headersCount++) {
						Cell headCell = row.createCell(headersCount);
						headCell.setCellStyle(headerCellStyle);
						headCell.setCellValue(titleList.get(headersCount));// 设置表头内容
					}
					/************************* 创建表体 ******************/
					if(contentList.size() > 0){
						int rowCount = 1;
						for (int j = begin; j < end; j++) {
							Row curRow = sheet.createRow(rowCount++);
							List<String> t = contentList.get(j);
							for (int k = 0; k < cells; k++) {
								Cell curCell = curRow.createCell(k);// 创建单元格
								String obj = t.get(k);// 通过名称获取当前单元格的值
								if (obj != null) {
									curCell.setCellValue(obj);
								} else {
									curCell.setCellValue("");
								}
								curCell.setCellStyle(bodyCellStyle);
							}
						}
					}
				}
				log.debug("workbook对象构建成功，开始写入文件......");
				workbook.write(out);
				log.debug("已成功写入文件，写入记录数：" + contentList.size() + "条");
				isSuccess = true;
			} catch (Exception e) {
				log.error("写入文件失败");
				e.printStackTrace();
			}finally{
				try {
					if(out != null)out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		  }else{
			  log.error("excelEntity为空！");
		  }
		  return isSuccess;
	  }
	  
	  private static int getSheetSize(int totalCount, int perSheetCount) {
		if (perSheetCount > 0 && totalCount > 0)
			return totalCount % perSheetCount == 0 ? totalCount / perSheetCount : totalCount / perSheetCount + 1;
		return 1;
	  }
	  
	  /**
		 * 设置单元格格式
		 * 
		 * @param cellStyle
		 * @return
		 */
		private static CellStyle getBodyCellStyle(Workbook wb) {
			CellStyle cellStyle = wb.createCellStyle();
			cellStyle.setBorderTop(CellStyle.BORDER_THIN);// 设置边框为实线
			cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			cellStyle.setBorderRight(CellStyle.BORDER_THIN);
			cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
			cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			// cellStyle.setWrapText(true); 设置自动换行
			return cellStyle;
		}

		/**
		 * 设置头部单元格格式
		 * 
		 * @param cellStyle
		 * @return
		 */
		private static CellStyle getHeaderCellStyle(Workbook wb) {
			CellStyle cellStyle = getBodyCellStyle(wb);
			cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
			Font font = wb.createFont();
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			font.setFontHeightInPoints((short) 10);
			cellStyle.setFont(font);
			return cellStyle;
		}
	  
	  /**
	   * 写一个sheet
	   * @param sheet
	   * @param excelSheet
	   */
	  public static void writeSheet(Sheet sheet,ExcelSheet excelSheet){
		  Row row = null;
		  Cell cell = null;
		  //标题
		  if( null != excelSheet.getTitleList() && excelSheet.getTitleList().size()>0){
			  int titleRowNum = excelSheet.getTitleRowNum();
			  if(titleRowNum==0){
				  titleRowNum = ExcelSheet.DEFAULT_TITLEROWNUM;  
			  }
			  row = sheet.createRow(excelSheet.getTitleRowNum());
			  
			  for(int i=0;i<excelSheet.getTitleList().size();i++){
				  cell = row.createCell(i);
				  //获取宽度，如果没有设置则采用自动
				  if(null !=excelSheet.getColumnWidth() && excelSheet.getColumnWidth().length>0 && excelSheet.getColumnWidth().length >=excelSheet.getTitleList().size()){
					  int columnWidth = excelSheet.getColumnWidth()[i];
					  if(columnWidth>0){
						  sheet.setColumnWidth(i, columnWidth);
					  }
				  }else{
					  sheet.autoSizeColumn(i);
				  }
				  cell.setCellValue(excelSheet.getTitleList().get(i));
			  }
		  }

		  
		  //内容
		  if(null != excelSheet.getContentList() && excelSheet.getContentList().size()>0){
			  int dataRowNum = excelSheet.getContentRowNum();
			  if(dataRowNum==0){
				  dataRowNum = ExcelSheet.DEFAULT_CONTENTROWNUM;
			  }
			  for(List<String> contentItem:excelSheet.getContentList()){
				  row = sheet.createRow(dataRowNum++);
				  if(null !=contentItem && contentItem.size()>0){
					  for(int i=0;i<contentItem.size();i++){
						  cell = row.createCell(i);
						  cell.setCellValue(contentItem.get(i));
					  }
				  }
				  
			  }
		  }
		  
		 
	  }

	  /**
	   * 根据文件路径，获取excel的Workbook
	   * @throws IOException 
	   */
	  public static Workbook getExcelWorkbook(String filePath) throws IOException{
	    Workbook book = null;
	    File file  = null;
	    FileInputStream fis = null;	
	    
	    try {
	      file = new File(filePath);
	      if(!file.exists()){
	    	 log.error("文件"+filePath+"不存在!");
	      }else{
	        fis = new FileInputStream(file);
	        book = WorkbookFactory.create(fis);
	      }
	    } catch (Exception e) {
	      throw new RuntimeException(e.getMessage());
	    } finally {
	      if(fis != null){
	        fis.close();
	      }
	    }
	    return book;
	  }
	  
	  
	 /**
	  * 根据输入流，获取excel的Workbook
	  * @param inputStream
	  * @return
	  * @throws IOException
	  */
	  public static Workbook getExcelWorkbook(InputStream inputStream) throws IOException{
	    Workbook book = null;
	    if(null != inputStream){
	    	 try {
				book = WorkbookFactory.create(inputStream);
	    		 
	    		// book= new XSSFWorkbook(inputStream);
			} catch (Exception e) {
				e.printStackTrace();
			}finally{
				 if(inputStream != null){
					 inputStream.close();
			 	 }
			}
	    	
	    }else{
	    	 log.error("输入流inputStream为空!");
	    }
	    return book;
	  }
	  
	  
	  
	  /**
	   * 根据索引 返回Sheet
	   * @param number
	   */
	  public static Sheet getSheetByNum(Workbook book,int number){
	    Sheet sheet = null;
	    try {
	      sheet = book.getSheetAt(number-1);
	    } catch (Exception e) {
	      throw new RuntimeException(e.getMessage());
	    }
	    return sheet;
	  }

	  /**
	   * 从map中获取key对应的值，并且设置默认值
	   * @param map
	   * @param key
	   * @param defaultValue
	   * @return
	   */
	  @SuppressWarnings("rawtypes")
	public static int getOptionValue(Map map,int key,int defaultValue){
		  int result = defaultValue;
		  if(null != map){
			   Object obj = map.get(key);
			   if(null != obj){
				   result = (Integer)obj;
			   }
		  }
		  return result;
	  }
	  
	  public static void main(String[] args) throws Exception{
	  	/*String filePathAndName = "D:/a.xlsx";
	    ExcelEntity excelEntity = new ExcelEntity();
	    InputStream in  = new FileInputStream(new File(filePathAndName));
	    excelEntity.setInputStream(in);
	    Map titleRowMap = new HashMap();
	    titleRowMap.put(0, 0);
	    titleRowMap.put(1, 0);
	    excelEntity.setTitleRowMap(titleRowMap);
	    Map contentRowMap = new HashMap();
	    contentRowMap.put(0, 1);
	    contentRowMap.put(1, 1);
	    excelEntity.setContentRowMap(contentRowMap);
	    ExcelUtils.parseExcel(excelEntity);
	    System.out.println("\n"+excelEntity);*/
	}
}
