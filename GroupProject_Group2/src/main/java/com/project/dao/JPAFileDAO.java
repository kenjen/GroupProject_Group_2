package com.project.dao;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.project.entities.FileInfo;

@Stateless
@Local
public class JPAFileDAO implements FileDAO{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Collection<FileInfo> getAllUploadedFilePaths() {
		Query query = em.createQuery("from FileInfo");
		List<FileInfo> result = query.getResultList();
		return result;
	}

	@Override
	public void addUploadedFilePath(String name, String path) {
		FileInfo f = new FileInfo(name, path);
		em.persist(f);
	}

	
}