package com.project.service;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.project.dao.FileDAO;
import com.project.dao.UserDAO;
import com.project.entities.FileInfo;
import com.project.entities.User;

@Stateless
@Local
public class FileServiceEJB implements FileService{
	
	@EJB
	private FileDAO dao;

	@Override
	public Collection<FileInfo> getAllUploadedFilePaths() {
		return dao.getAllUploadedFilePaths();
	}
	
}