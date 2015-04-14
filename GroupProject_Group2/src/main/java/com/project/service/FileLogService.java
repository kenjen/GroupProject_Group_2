package com.project.service;

import java.util.Collection;
import java.util.Date;

import javax.ejb.Local;

import com.project.entities.FileLog;

@Local
public interface FileLogService {
	Collection<FileLog> getAllUploadedFilePaths();
	boolean addUploadedFilePath(String name, String path, Date date, Integer errorCount, boolean flush);
}
