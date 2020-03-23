package com.tutorial.TutorialAppReports.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.tutorial.TutorialAppReports.DAO.QueryUtil;
import com.tutorial.TutorialAppReports.model.Reports;
import com.tutorial.TutorialAppReports.service.impl.ReportGenerationServiceImpl;

@RestController
@RequestMapping("/tutorial")
@CrossOrigin("*")
public class ReportGenerationController {
	
	@Autowired
	private QueryUtil queryUtil;
	
	@Autowired
	private GridFsOperations operations;
	
	@Autowired
	private ReportGenerationServiceImpl reportService;
	
	@GetMapping("/report/getFile")
	public ResponseEntity<Resource> getFile(@RequestParam("fileid") String fileId) {
		GridFSFile fsFile=queryUtil.loadFile(fileId);
		return ResponseEntity.ok().contentLength(fsFile.getLength()).header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fsFile.getFilename()).contentType(MediaType.parseMediaType("application/vnd.ms-excel")).body(operations.getResource(fsFile));
		
	
	}
	
	@GetMapping("/generate")
	public void generate(@RequestParam("reportType") String reportType) {
		reportService.generateReport(reportType);	
	}
	
	@GetMapping("/report")
	public Map<String,Object> getAll() {
		Map<String,Object> response=new HashMap<>();
		List<Reports> data=queryUtil.getAllReports();
		response.put("data", data);
		response.put("size", data.size());
		return response;
		}

}
