package com.project.service;

import java.util.Collection;

import javax.ejb.Local;

import com.project.entities.FileInfo;

@Local
public interface FileService {
	Collection<FileInfo> getAllUploadedFilePaths();

	boolean addUploadedFilePath(String name, String path, boolean flush);

	void removeFileFromDatabase(String fileName);

	void removeFile(String fileName);
}
