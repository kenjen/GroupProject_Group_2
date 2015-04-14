package com.project.service;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.project.dao.FileLogDAO;
import com.project.entities.FileLog;

@Stateless
@Local
public class FileLogServiceEJB implements FileLogService {
	
	@EJB
	private FileLogDAO dao;

	@Override
	public Collection<FileLog> getAllUploadedFilePaths() {
		return dao.getAllUploadedFilePaths();
	}

}
