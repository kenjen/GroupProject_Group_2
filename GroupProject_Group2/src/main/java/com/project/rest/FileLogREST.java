package com.project.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.entities.FileLog;
import com.project.service.FileLogService;

@Path("/file_log")
@Stateless
public class FileLogREST {
	
	@EJB
	private FileLogService service;

	
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
		
	@GET
	@Path("/getlog")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String[]> getAllUploadedFilePaths() throws ParseException {
		List<FileLog> list =  (List<FileLog>) service.getAllUploadedFilePaths();
		ArrayList<String[]> aList = new ArrayList<String[]>();
		if(list == null || list.isEmpty()){
			String[] str = {"none", "", ""};
			aList.add(str);
			return aList;
		}
		for(FileLog fileLog: list){
			Date d = fileLog.getDateUploaded();
			String date = d.toString().substring(0, 19);
			String name = fileLog.getFilename();
			String errorCount = fileLog.getErrorCount().toString();
			String[] str = {date, name, errorCount};
			aList.add(str);
		}
		return aList;
	}
	
	@GET
	@Path("/addFileInfo/{data}")
	public void addUploadedFilePath(@PathParam("data") String data) throws ParseException{
		String[] splitData = data.split("::");
		sdf.setLenient(false);
		Date date = sdf.parse(splitData[2]);
		int count = Integer.parseInt(splitData[3]);
		service.addUploadedFilePath(splitData[1], splitData[0], date, count, false);
	}

}
