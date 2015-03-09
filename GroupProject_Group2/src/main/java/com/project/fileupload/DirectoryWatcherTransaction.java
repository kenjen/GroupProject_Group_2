package com.project.fileupload;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.project.entities.FileInfo;
import com.project.service.FileService;

@Stateless
public class DirectoryWatcherTransaction implements DirectoryWatcherTransactionInterface{
	
	@EJB
	FileService fileService;
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void addFilePath(FileInfo file){
		fileService.addUploadedFilePath(file.getFilename(), "/upload/" + file.getFilepath(), true);
	}

}
