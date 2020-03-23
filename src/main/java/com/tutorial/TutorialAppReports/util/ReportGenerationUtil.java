package com.tutorial.TutorialAppReports.util;

import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.TutorialAppReports.DAO.QueryUtil;

@Service
public class ReportGenerationUtil {
	
	ThreadLocal<CellStyle> cellStyleLocal=new ThreadLocal<>();
	
	@Autowired
	private QueryUtil queryUtil;
	
	public void createHeader(Workbook workbook,String[] headers,String sheetName) {
		Sheet sheet=workbook.getSheet(sheetName);
		Font font=workbook.createFont();
		font.setBold(true);
		font.setColor(IndexedColors.BLUE.getIndex());
		CellStyle cellstyle=workbook.createCellStyle();
		cellStyleLocal.set(cellstyle);
		
		Row row=sheet.createRow(sheet.getLastRowNum());
		int colId=0;
		for(String header:headers) {
			Cell cell=row.createCell(colId++);
			cell.setCellValue(header);
			cell.setCellStyle(cellstyle);
		}
	}
	
	
	public Path writeExcelDatatoFile(String filePath,Workbook workbook) {
		FileOutputStream fos=null;
		try {
			fos = new FileOutputStream(filePath);
			workbook.write(fos);
		} catch (Exception e) {
			// TODO Auto-generaterd catch block
			e.printStackTrace();
		}
	
		return Paths.get(filePath).normalize();
	}
	
	public String buildFileName(String reportType){
		return reportType+DateUtil.dateTostring(new Date(), DateUtil.DDMMYYY);	
	}
	
	public void storeInGridFs(Path path,String reportType,String reportId) {
		queryUtil.storeFileInGridFS(path, buildFileName(reportType), reportId);
	}

}
