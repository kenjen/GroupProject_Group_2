package com.project.service;

import java.util.Collection;
import java.util.Date;

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

	@Override
	public boolean addUploadedFilePath(String name, String path, Date date,
			Integer errorCount, boolean flush) {
		return dao.addUploadedFilePath(name, path, date, errorCount, flush);
	}

}
