package com.project.dao;

import java.util.Collection;
import java.util.Date;

import com.project.entities.FileLog;

public interface FileLogDAO {	
	
	Collection<FileLog> getAllUploadedFilePaths();

	boolean addUploadedFilePath(String name, String path, Date date, Integer errorCount, boolean flush);

}
