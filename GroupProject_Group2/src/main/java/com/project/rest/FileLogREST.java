package com.project.rest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.entities.FileLog;
import com.project.service.FileLogService;

@Path("/file_log")
@Stateless
public class FileLogREST {
	
	@EJB
	private FileLogService service;
		
	@GET
	@Path("/getlog")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String[]> getAllUploadedFilePaths() {
		List<FileLog> list =  (List<FileLog>) service.getAllUploadedFilePaths();
		ArrayList<String[]> aList = new ArrayList<String[]>();
		if(list == null || list.isEmpty()){
			String[] str = {"none", "", ""};
			aList.add(str);
			return aList;
		}
		for(FileLog fileLog: list){
			Date d = fileLog.getDateUploaded();
			String date = d.toString();
			String name = fileLog.getFilename();
			String errorCount = fileLog.getErrorCount().toString();
			String[] str = {date, name, errorCount};
			aList.add(str);
		}
		return aList;
	}

}
