package com.project.service;

import java.util.Collection;

import javax.ejb.Local;

import com.project.entities.FileLog;

@Local
public interface FileLogService {
	Collection<FileLog> getAllUploadedFilePaths();
}
