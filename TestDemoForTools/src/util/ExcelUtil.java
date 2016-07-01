package util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class ExcelUtil {
	
	private static String excelReportPath;
	private static Map<String, ArrayList<String>> titleAndValueList = new HashMap<String, ArrayList<String>>();
	
	public static void writeExcel() { 
		
		try {
			File excel = new File(excelReportPath);
			if (excel.exists()) {
				Workbook workbook = Workbook.getWorkbook(excel);
				Sheet sheet = workbook.getSheet(0);
				int columns = sheet.getColumns();
				WritableWorkbook wbook = Workbook.createWorkbook(excel, workbook);
				WritableSheet sh = wbook.getSheet(0);
				
				Set<String> keys = titleAndValueList.keySet();
	            
	            Label label;
	            int i = columns;
	            if (keys != null) {
					Iterator<String> iterator = keys.iterator();
					while (iterator.hasNext()) {
						
						Object key = iterator.next();
						label = new Label(i, 0, key.toString());
						sh.addCell(label);
						
						ArrayList<String> values = titleAndValueList.get(key);
						for (int j = 0; j < values.size(); j++) {
							label = new Label(i, j+1, values.get(j));
							sh.addCell(label);
						}
						
						i++;
					}
				}
				
				wbook.write();
				wbook.close();
				
				
			}else {
				excel.createNewFile();
				// 输出的excel的路径   
		        String filePath = excelReportPath; 
		        // 创建Excel工作薄   
		        WritableWorkbook workbook; 
		        OutputStream os = new FileOutputStream(filePath);   
		        workbook = Workbook.createWorkbook(os);
		        // 添加第一个工作表并设置第一个Sheet的名字   
	            WritableSheet sheet = workbook.createSheet("性能数据", 0);
	            Set<String> keys = titleAndValueList.keySet();
	            
	            Label label;
	            int i = 0;
	            if (keys != null) {
					Iterator<String> iterator = keys.iterator();
					while (iterator.hasNext()) {
						
						Object key = iterator.next();
						label = new Label(i, 0, key.toString());
						sheet.addCell(label);
						ArrayList<String> values = titleAndValueList.get(key);
						for (int j = 0; j < values.size(); j++) {
							label = new Label(i, j+1, values.get(j));
							sheet.addCell(label);
						}
						
						i++;
					}
				}
	            
	            workbook.write();
	            workbook.close();
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		 
	}
	
	public static String getExcelReportPath() {
		return excelReportPath;
	}
	
	public static void setExcelReportPath(String excelReportPath) {
		ExcelUtil.excelReportPath = excelReportPath;
	}
	
	public static Map<String, ArrayList<String>> getTitleAndValueList() {
		return titleAndValueList;
	}
	
	public static void setTitleAndValueList(Map<String, ArrayList<String>> titleAndValueList) {
		ExcelUtil.titleAndValueList = titleAndValueList;
	}
	
	public static void add(String title , ArrayList<String> values) {
		titleAndValueList.put(title, values);
	}
	
}
