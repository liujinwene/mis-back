package com.sec.mis.poi;

import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.sec.mis.lang.DateUtils;

public class POIUtils {
	
	private static final int DEFAULT_PER_SHEET_COUNT = 5000;//每个sheet可容纳总行数
	private static final float PX = 15.625f;//像素
	private static final int DEFAULT_COLUMN_WIDTH = 16;//列宽16字符
	//private static final String EXCEL_SUFFIX_2003 = ".xls";
	//private static final String EXCEL_SUFFIX_2007 = ".xlsx";
	private static final String TYPE_2003 = "xls";
	private static final String TYPE_2007 = "xlsx";
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static DecimalFormat decimalFormat = new DecimalFormat("#0.00");
	private static Logger logger = Logger.getLogger(POIUtils.class);
	/**
	 * 创建excel,根据数据数量自动拆分工作表
	 * @param headers 表头
	 * @param fields 字段名
	 * @param fileName 文件名
	 * @param data 数据集
	 * @param out 输出流
	 * @throws Exception
	 */
	public static void create(String[] headers, String[] fields, String fileName, List<Map<String, Object>> data, OutputStream out) throws Exception {
		if (fields.length != headers.length) 
			throw new UnsupportedOperationException("字段长度与表头列数不一致！！！");
		int dataSize = data == null ? 0 : data.size();//总行数
		if (dataSize == 0)  return;
		try {
			logger.debug("开始构建workbook对象.... ");
			Workbook workbook = getWorkBookByFileSuffix(fileName);//根据excel类型创建workbook
			int sheetsNum = getSheetSize(dataSize, DEFAULT_PER_SHEET_COUNT);// 获取总页数
			CellStyle bodyCellStyle = getBodyCellStyle(workbook);// 设置单元格样式
			CellStyle headerCellStyle = getHeaderCellStyle(workbook);
			for (int i = 1; i <= sheetsNum; i++) {
				/************************* 创建工作表 ******************/
				Sheet sheet = workbook.createSheet();// 创建sheet
				sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);// 设置默认列宽
				/************************* 创建表头 ******************/
				Row row = sheet.createRow(0);// 创建表头
				row.setHeight((short) (PX * 30));// 设置默认行高30px
				for (int headersCount = 0; headersCount < headers.length; headersCount++) {
					Cell headCell = row.createCell(headersCount);
					headCell.setCellStyle(headerCellStyle);
					headCell.setCellValue(headers[headersCount]);// 设置表头内容
				}
				/************************* 创建表体 ******************/
				int cells = fields.length;// 总列数
				int begin = (i - 1) * DEFAULT_PER_SHEET_COUNT;// 元素开始位置
				int end = (begin + DEFAULT_PER_SHEET_COUNT) > dataSize ? dataSize : (begin + DEFAULT_PER_SHEET_COUNT);// 元素结束位置
				int rowCount = 1;
				for (int j = begin; j < end; j++) {
					Row curRow = sheet.createRow(rowCount++);
					Map<String, Object> map = data.get(j);
					for (int k = 0; k < cells; k++) {
						Cell curCell = curRow.createCell(k);// 创建单元格
						Object obj = map.get(fields[k]);// 通过名称获取当前单元格的值
						if (obj != null) {
							if (obj instanceof Number) {
								String val = obj.toString();
								curCell.setCellValue(val.indexOf(".") != -1 ? decimalFormat.format(obj) : val);
							} else if (obj instanceof Date) {
								curCell.setCellValue(dateFormat.format(obj));
							} else {
								curCell.setCellValue(obj.toString());
							}
						} else {
							curCell.setCellValue("");
						}
						curCell.setCellStyle(bodyCellStyle);
					}
				}
			}
			logger.debug("workbook对象构建成功，开始写入  " + fileName);
			workbook.write(out);
			logger.debug("已成功写入 " + fileName + "，写入记录数：" + dataSize + "条");
		} catch (Exception e) {
			logger.debug("写入 " + fileName + " 失败");
			throw new UnsupportedOperationException("创建" + fileName + "失败！！！");
		} 
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
	 * 获取页数
	 * 
	 * @param totalCount
	 * @param perSheetCount
	 * @return
	 */
	private static int getSheetSize(int totalCount, int perSheetCount) {
		if (perSheetCount > 0 && totalCount > 0)
			return totalCount % perSheetCount == 0 ? totalCount / perSheetCount : totalCount / perSheetCount + 1;
		return 1;
	}
	
	private static Workbook getWorkBookByFileSuffix(String type){
		if(type != null || TYPE_2003.equals(type)){
			return new HSSFWorkbook();
		}else if(type != null || TYPE_2007.equals(type)){
			return new XSSFWorkbook();
		}else {
			return new XSSFWorkbook();
		}
	}
	
	
	public static <K> List<K> parser(InputStream is,Class<K> cls,String[] classFields) throws Exception{
		return read(WorkbookFactory.create(is),cls,classFields);
	}
	
	private static <T>List<T> read(Workbook worbook,Class<T> cls,String[] classFields) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException{
		List<T> tlist = new ArrayList<T>();
		Sheet sheet = worbook.getSheetAt(0);
		for(int i=1;i<sheet.getPhysicalNumberOfRows();i++) {
			T t = cls.newInstance();
			Row row = sheet.getRow(i);
			for(int j=0;j<classFields.length;j++) {
				Field field = t.getClass().getDeclaredField(classFields[j]);
				field.setAccessible(true); 
				Cell cell = row.getCell(j);
				if(cell != null) {
					if(field.getType().toString().endsWith("String")) {
						if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
							field.set(t, cell.getStringCellValue());  
						}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							field.set(t, String.valueOf(cell.getNumericCellValue()));  
						}
					}else if(field.getType().toString().endsWith("Double")){
						if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
							if(cell.getStringCellValue().endsWith("%")){
								field.set(t,Double.valueOf(cell.getStringCellValue().replaceAll("%", "")) / 100);
							}else {
								field.set(t, Double.valueOf(cell.getStringCellValue()));
							}
						}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							field.set(t, cell.getNumericCellValue());  
						}
					}else if(field.getType().toString().endsWith("Date")){
						if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
							field.set(t, DateUtils.parseDate(cell.getStringCellValue(),"yyyy-MM-dd")); 
						}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							if(HSSFDateUtil.isCellDateFormatted(cell)) {
								field.set(t, cell.getDateCellValue());  
							}
						}
					}else if(field.getType().toString().endsWith("Float")){
						if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
							field.set(t, Float.valueOf(cell.getStringCellValue())); 
						}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							field.set(t, Double.valueOf(cell.getNumericCellValue()).floatValue());
						}
					}else if(field.getType().toString().endsWith("Integer")) {
						if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
							field.set(t, Integer.valueOf(cell.getStringCellValue())); 
						}else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
							field.set(t, Double.valueOf(cell.getNumericCellValue()).intValue());
						}
					}
				}
				field.setAccessible(false);
			}
			tlist.add(t);
		}
		return tlist;
	}
}
