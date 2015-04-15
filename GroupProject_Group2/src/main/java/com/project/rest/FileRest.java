package com.project.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.entities.FileInfo;
import com.project.service.FileService;

@Path("/file")
@Stateless
public class FileRest {

	@EJB
	private FileService service;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<FileInfo> getAllUploadedFilePaths() {
		return service.getAllUploadedFilePaths();
	}
	
	@GET
	@Path("/addFileInfo/{data}")
	public void addUploadedFilePath(@PathParam("data") String data) {
		String[] splitData = data.split("::");
		service.addUploadedFilePath(splitData[0], splitData[1], false);
	}
	
	@DELETE
	@Path("/delete/{encodedFileName}")
	public void removeFileFromDatabase(@PathParam("encodedFileName") String encodedFileName){
		try {
			String fileDecoded = URLDecoder.decode(encodedFileName, "UTF-8");
			service.removeFile(fileDecoded);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
