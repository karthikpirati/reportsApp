package com.tutorial.TutorialAppReports.DAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import com.mongodb.client.gridfs.model.GridFSFile;
import com.tutorial.TutorialAppReports.model.Reports;

@Service
public class QueryUtil {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Autowired
	private GridFsTemplate gridFsTemplate;
	
	public void storeFileInGridFS(Path path,String fileName,String reportId) {
		try {
			FileInputStream fis=new FileInputStream(path.normalize().toFile());
			Object gridFSFile=gridFsTemplate.store(fis, fileName);
			Reports report=mongoTemplate.findById(reportId, Reports.class);
			report.setLinkId(gridFSFile.toString());
			report.setStatus("success");
			mongoTemplate.save(report);
		} catch (Exception e) {
		}finally {
			try {
//				Files.delete(path);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	public GridFSFile loadFile(String fileId) {
		Query query=new Query(Criteria.where("_id").is(fileId));
		return gridFsTemplate.findOne(query);
	}
	
	public void saveReport(Reports reports) {
		mongoTemplate.save(reports);
	}
	
	public List<Reports> getAllReports(){
		return mongoTemplate.findAll(Reports.class);
	}

}
