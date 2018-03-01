/**
 * 
 */
package com.kivi.framework.excel.util;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFormulaEvaluator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kivi.framework.excel.bean.ExcelCellData;
import com.kivi.framework.excel.bean.ExcelCellData.CellStyleEnums;
import com.kivi.framework.exception.ToolBoxException;
import com.kivi.framework.util.kit.StrKit;


/**
 * Excel操作工具类
 * @author Eric.Lu
 *
 */
public class ExcelUtils {
	
	private static final Logger log = LoggerFactory.getLogger(ExcelUtils.class);
	/**
	 * 生成.xlsx文件格式
	 * @param outFileName
	 * @param title
	 * @param headers
	 * @param dataList
	 */
	public static void createX(String outFileName, String title, List<String> headers, 
			List<ExcelCellData> dataList){

		XSSFWorkbook wb = new XSSFWorkbook();
		//创建一个工作表
		Sheet sheet = wb.createSheet() ;
		wb.setSheetName(0, StrKit.isBlank(title)?"Sheet1":title );
		
		int iRow = 0 , iCol = 0 ;
		//设置title
		if(StrKit.notBlank(title)&&!headers.isEmpty()){
			CellStyle styleTitle = createTitleCellStyle(wb);
			Row row = sheet.createRow(iRow);
			row.setHeight((short) 500);// 设定行的高度  
			// 创建一个Excel的单元格  
			Cell cell = row.createCell(0) ;
			// 合并单元格(startRow，endRow，startColumn，endColumn)  
			sheet.addMergedRegion(new CellRangeAddress(iRow, iCol, 0, headers.size()-1)); 
			cell.setCellValue(title);
			cell.setCellStyle(styleTitle);
			iRow ++;
		}
		
		CellStyle styleHead = createHeaderCellStyle(wb);
		CellStyle styleCell = createCellStyle(wb);
		
		// 设置headers
		Iterator<String> ithd = headers.iterator();
		Row rowhead = sheet.createRow(iRow++);
		iCol = 0 ;
		while(ithd.hasNext()){
			String text = ithd.next();
			//rowhead.setHeight((short) 300);// 设定行的高度  
			Cell cell = rowhead.createCell(iCol++) ;
			cell.setCellValue(text);
			cell.setCellStyle(styleHead);
		}
		
		FileOutputStream fout = null;
		try {
			File outFile = new File(outFileName);
			if(!outFile.exists()){
			outFile.getParentFile().mkdirs();
			outFile.createNewFile();
		}
		else{
			outFile.delete();
		}
		
		fout = new FileOutputStream(outFile);
		
		
		Map<Integer,Integer> colSizeMap = new HashMap<Integer,Integer>();
		
		Iterator<ExcelCellData> it = dataList.iterator() ;
		while( it.hasNext() ){
			ExcelCellData data = it.next() ;
			
			if(colSizeMap.containsKey( data.getCol())){
				int width = colSizeMap.get( data.getCol()) ;
				if(width<data.getWidth())
					colSizeMap.put(data.getCol(), data.getWidth() );
			}
			else{
				if(data.getWidth() >0)
					colSizeMap.put(data.getCol(), data.getWidth() );
			}
			
			Row row = null ;
			
			row = sheet.getRow( data.getRow() ) ;
			if(row== null)
				row = sheet.createRow(data.getRow() ) ;
				
			Cell cell = row.getCell( data.getCol() ) ;
			if(cell == null|| data.isNeedCreate()){
				cell = row.createCell(data.getCol());
			}
			
			
			if(data.getStyle() == CellStyleEnums.cell_header.code)
				cell.setCellStyle(styleHead);
			else
				cell.setCellStyle(styleCell);
			
			
			if( data.getValue() == null ){
				cell.setCellValue("") ;
			}else{
				if( data.getValue() instanceof Double ){
					cell.setCellValue( ((Double)data.getValue()).doubleValue() ) ;
				}
				else if( data.getValue() instanceof String ){
					cell.setCellValue( (String)data.getValue() );
				}else if( data.getValue() instanceof Integer ){
					cell.setCellValue( ((Integer)data.getValue()).intValue() );
				}else if( data.getValue() instanceof Long ){
					cell.setCellValue( (Long)data.getValue() );
				}else if( data.getValue() instanceof BigDecimal ){
					cell.setCellValue( ((BigDecimal)data.getValue()).doubleValue() );
				}else if( data.getValue() instanceof Date ){
					cell.setCellValue( (Date)data.getValue() );
				}else{
					cell.setCellValue( (String)data.getValue() );
				}//if
			}// if
		}// while
		
		Iterator<Entry<Integer,Integer>> itcol = colSizeMap.entrySet().iterator();
		while( itcol.hasNext()){
			Entry<Integer,Integer> ent = itcol.next();
			sheet.setColumnWidth(ent.getKey(), ent.getValue()*256);
		}
		
		
		// 计算全部公式
		HSSFFormulaEvaluator.evaluateAllFormulaCells(wb ) ;
		
		wb.write(fout) ;
		fout.flush();
		
		
		} catch (IOException e) {
		log.error("生成excel文件异常",e);
			throw new ToolBoxException(e);
		} catch (Exception e) {
			log.error("生成excel文件异常",e);
			throw new ToolBoxException(e);
		}
		finally{
			try {
			if(fout!=null)
				fout.close();
			} catch (IOException e) {
				log.error("关闭文件异常",e);
				throw new ToolBoxException(e);
			}
		}
	}
	/**
	 * 生成.xls文件格式
	 * @param outFileName
	 * @param title
	 * @param headers
	 * @param dataList
	 */
	public static void create(String outFileName, String title, List<String> headers, 
							List<ExcelCellData> dataList){
		
		HSSFWorkbook wb = new HSSFWorkbook();
		//创建一个工作表
		Sheet sheet = wb.createSheet() ;
		wb.setSheetName(0, StrKit.isBlank(title)?"Sheet1":title );
		
		int iRow = 0 , iCol = 0 ;
		//设置title
		if(StrKit.notBlank(title)&&!headers.isEmpty()){
			CellStyle styleTitle = createTitleCellStyle(wb);
			Row row = sheet.createRow(iRow);
			row.setHeight((short) 500);// 设定行的高度  
		    // 创建一个Excel的单元格  
		    Cell cell = row.createCell(0) ;
		    // 合并单元格(startRow，endRow，startColumn，endColumn)  
		    sheet.addMergedRegion(new CellRangeAddress(iRow, iCol, 0, headers.size()-1)); 
	        cell.setCellValue(title);
	        cell.setCellStyle(styleTitle);
	        iRow ++;
		}
		
		CellStyle styleHead = createHeaderCellStyle(wb);
		CellStyle styleCell = createCellStyle(wb);
		
		// 设置headers
		Iterator<String> ithd = headers.iterator();
		Row rowhead = sheet.createRow(iRow++);
		iCol = 0 ;
		while(ithd.hasNext()){
			String text = ithd.next();
			//rowhead.setHeight((short) 300);// 设定行的高度  
			Cell cell = rowhead.createCell(iCol++) ;
			cell.setCellValue(text);
	        cell.setCellStyle(styleHead);
		}
		
		FileOutputStream fout = null;
		try {
			File outFile = new File(outFileName);
			if(!outFile.exists()){
				outFile.getParentFile().mkdirs();
				outFile.createNewFile();
			}
			else{
				outFile.delete();
			}
			
			fout = new FileOutputStream(outFile);
			
			
			Map<Integer,Integer> colSizeMap = new HashMap<Integer,Integer>();
			
			Iterator<ExcelCellData> it = dataList.iterator() ;
			while( it.hasNext() ){
				ExcelCellData data = it.next() ;
				
				if(colSizeMap.containsKey( data.getCol())){
					int width = colSizeMap.get( data.getCol()) ;
					if(width<data.getWidth())
						colSizeMap.put(data.getCol(), data.getWidth() );
				}
				else{
					if(data.getWidth() >0)
						colSizeMap.put(data.getCol(), data.getWidth() );
				}
				
				Row row = null ;
				
				row = sheet.getRow( data.getRow() ) ;
				if(row== null)
					row = sheet.createRow(data.getRow() ) ;
					
				Cell cell = row.getCell( data.getCol() ) ;
				if(cell == null|| data.isNeedCreate()){
					cell = row.createCell(data.getCol());
				}
				
				
				if(data.getStyle() == CellStyleEnums.cell_header.code)
					cell.setCellStyle(styleHead);
				else
					cell.setCellStyle(styleCell);
				
				
				if( data.getValue() == null ){
					cell.setCellValue("") ;
				}else{
					if( data.getValue() instanceof Double ){
						cell.setCellValue( ((Double)data.getValue()).doubleValue() ) ;
					}
					else if( data.getValue() instanceof String ){
						cell.setCellValue( (String)data.getValue() );
					}else if( data.getValue() instanceof Integer ){
						cell.setCellValue( ((Integer)data.getValue()).intValue() );
					}else if( data.getValue() instanceof BigDecimal ){
						cell.setCellValue( ((BigDecimal)data.getValue()).doubleValue() );
					}else if( data.getValue() instanceof Date ){
						cell.setCellValue( (Date)data.getValue() );
					}else{
						cell.setCellValue( (String)data.getValue() );
					}//if
				}// if
			}// while
			
			Iterator<Entry<Integer,Integer>> itcol = colSizeMap.entrySet().iterator();
			while( itcol.hasNext()){
				Entry<Integer,Integer> ent = itcol.next();
				sheet.setColumnWidth(ent.getKey(), ent.getValue()*256);
			}
			
			
			// 计算全部公式
			HSSFFormulaEvaluator.evaluateAllFormulaCells(wb ) ;
			
			wb.write(fout) ;
			fout.flush();
			
			
		} catch (IOException e) {
			log.error("生成excel文件异常",e);
			throw new ToolBoxException(e);
		} catch (Exception e) {
			log.error("生成excel文件异常",e);
			throw new ToolBoxException(e);
		}
		finally{
			try {
				if(fout!=null)
					fout.close();
			} catch (IOException e) {
				log.error("关闭文件异常",e);
				throw new ToolBoxException(e);
			}
		}
	}

	/**
	 * 根据excel模板生成数据文件
	 * @param templateFile
	 * @param outFileName
	 * @param dataList
	 * @return
	 */
	public static boolean create( String templateFile, String outFileName, List<ExcelCellData> dataList ){
		
		if (templateFile == null || "".equals(templateFile)) {
			log.error("读取excel文件失败：路径或文件名为空");
			return false ;
		}
		File tempFile = new File(templateFile);
		if (!tempFile.exists()) {
			log.error("读取excel文件失败：路径或文件名不存在");
			return false;
		}
		
		FileOutputStream outFile = null ;
		try {
			outFile = new FileOutputStream(outFileName);
		} catch (FileNotFoundException e1) {
			log.error("输出文件路["+outFileName+"]径无效", e1) ;
			return false ;
		}
		
		Workbook workBook = null;
		try {
			workBook = WorkbookFactory.create(tempFile);
			Sheet sheet = workBook.getSheetAt(0) ;
			
			Iterator<ExcelCellData> it = dataList.iterator() ;
			while( it.hasNext() ){
				ExcelCellData data = it.next() ;
				Row row = null ;
				if(data.isNeedCreate())
					row = sheet.createRow(data.getRow() ) ;
				else
					row = sheet.getRow( data.getRow() ) ;
				
					
				Cell cell = row.getCell( data.getCol() ) ;
				if(cell == null|| data.isNeedCreate()){
					cell = row.createCell(data.getCol());
				}
				if( data.getValue() == null ){
					cell.setCellValue("") ;
				}else{
					if( data.getValue() instanceof Double ){
						cell.setCellValue( ((Double)data.getValue()).doubleValue() ) ;
					}
					else if( data.getValue() instanceof String ){
						cell.setCellValue( (String)data.getValue() );
					}else if( data.getValue() instanceof Integer ){
						cell.setCellValue( ((Integer)data.getValue()).intValue() );
					}else if( data.getValue() instanceof BigDecimal ){
						cell.setCellValue( ((BigDecimal)data.getValue()).doubleValue() );
					}else if( data.getValue() instanceof Date ){
						cell.setCellValue( (Date)data.getValue() );
					}else{
						cell.setCellValue( (String)data.getValue() );
					}//if
				}// if
			}// while
			
			// 计算全部公式
			HSSFFormulaEvaluator.evaluateAllFormulaCells(workBook ) ;
			
			workBook.write(outFile) ;
			outFile.flush();
			
			
		} catch (InvalidFormatException e) {
			log.error("错误的公式表达式",e);
			return false ;
		} catch (IOException e) {
			log.error("IO错误",e);
			return false ;
		} catch (Exception e) {
			log.error("IO错误",e);
			return false ;
		}
		finally{
			try {
				outFile.close();
			} catch (IOException e) {
				log.error("IO错误",e);
				return false ;
			}
		}
		
		return true ;
	}
	
	/**
	 * 创建excel单元样式
	 * <p> Create Date: 2014-7-30 </p>
	 * @param wb
	 * @return
	 */
	public static CellStyle createCellStyle( Workbook wb ){
		CellStyle style = wb.createCellStyle() ;
		
		// 创建字体样式  
	    Font font = wb.createFont() ;
	    font.setFontName("Verdana");  
	    font.setBoldweight((short) 80);  
	    font.setFontHeight((short) 240);  
	    font.setColor(HSSFColor.BLUE.index);  
	  
	    // 创建单元格样式  
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
	    style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);  
	    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
	  
	    // 设置边框  
	    style.setBottomBorderColor(HSSFColor.RED.index);  
	    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
	    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
	    style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
	    style.setBorderTop(HSSFCellStyle.BORDER_THIN); 
	    
	    style.setFont(font);// 设置字体  
		
		return style ;
	}
	
	public static CellStyle createHeaderCellStyle( Workbook wb ){
		CellStyle style = wb.createCellStyle() ;
		
		// 创建字体样式  
	    Font font = wb.createFont() ;
	    font.setFontName("Verdana");  
	    font.setBoldweight((short) 100);  
	    font.setFontHeight((short) 300);  
	    //font.setColor(HSSFColor.BLUE.index);  
	  
	    // 创建单元格样式  
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);  
	    style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);  
	    style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);  
	  
	    // 设置边框  
	    //style.setBottomBorderColor(HSSFColor.RED.index);  
	    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
	    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
	    style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
	    style.setBorderTop(HSSFCellStyle.BORDER_THIN); 
	    
	    style.setFont(font);// 设置字体  
		
		return style ;
	}
	
	public static CellStyle createTitleCellStyle( Workbook wb ){
		Font font = wb.createFont() ;
	    font.setFontName("Verdana");  
	    font.setBoldweight((short) 120);  
	    font.setFontHeight((short) 360);  
	    
	    CellStyle style = wb.createCellStyle() ;
	    style.setAlignment(HSSFCellStyle.ALIGN_CENTER);  
	    style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER); 
	    style.setFont(font) ;
	    
	    // 设置边框  
	    style.setBorderBottom(HSSFCellStyle.BORDER_THIN);  
	    style.setBorderLeft(HSSFCellStyle.BORDER_THIN);  
	    style.setBorderRight(HSSFCellStyle.BORDER_THIN);  
	    style.setBorderTop(HSSFCellStyle.BORDER_THIN); 
	    
	    return style;
	}
	
	public static String getCellValue( Cell cell ){
		if(cell == null )
			return null ;
		
		if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC ){
			 return String.valueOf(cell.getNumericCellValue());
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_STRING ){
			return cell.getStringCellValue().trim();
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
			return String.valueOf(cell.getBooleanCellValue()); 
		}
		else if(cell.getCellType() == Cell.CELL_TYPE_BLANK){
			return ""; 
		}
		
		try {
			return cell.getStringCellValue().trim();
		} catch (Exception e) {
		}
		try {
			return String.valueOf(cell.getNumericCellValue());
		} catch (Exception e) {
		}
		try {
			return String.valueOf(cell.getBooleanCellValue());
		} catch (Exception e) {
		}
		
		return null;
	}
}

