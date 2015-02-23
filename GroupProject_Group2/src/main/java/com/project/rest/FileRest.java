package com.project.rest;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
}
