package com.project.dao;

import java.util.Collection;

import com.project.entities.FileInfo;

public interface FileDAO {
	
	Collection<FileInfo> getAllUploadedFilePaths();
	
	void addUploadedFilePath(String name, String path);

}
