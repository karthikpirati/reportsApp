package com.tutorial.TutorialAppReports.service.impl;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.TutorialAppReports.DAO.QueryUtil;
import com.tutorial.TutorialAppReports.model.Reports;
import com.tutorial.TutorialAppReports.util.DateUtil;
import com.tutorial.TutorialAppReports.util.ReportGenerationUtil;

@Service
public class ReportGenerationServiceImpl {
	
	String[] headers=new String[] {"SN","FirstName","LastName"};
	public static final String TMP_DIR="D:\\AllTutorials\\Projects\\springboot\\files\\";
	
	@Autowired
	private ReportGenerationUtil reportUtil;
	
	@Autowired
	private QueryUtil queryUtil;
	
	public void generateReport(String reportType) {
		Workbook workbook=new SXSSFWorkbook();
		Sheet sheet=workbook.createSheet(reportType);
		reportUtil.createHeader(workbook, headers, reportType);
		String filePath=TMP_DIR+LocalDate.now()+"-"+UUID.randomUUID()+".csv";
		Path path=reportUtil.writeExcelDatatoFile(filePath, workbook);
		Reports reports=new Reports();
		reports.setFileName(reportUtil.buildFileName(reportType));
		queryUtil.saveReport(reports);
		reportUtil.storeInGridFs(path, reportType, reports.getId());
	}
}
