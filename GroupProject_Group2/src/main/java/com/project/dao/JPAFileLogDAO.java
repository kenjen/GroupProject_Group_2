package com.project.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.entities.FileLog;

@Stateless
@Local
public class JPAFileLogDAO implements FileLogDAO{
	
	@PersistenceContext
	private EntityManager em;
	
	private static final Logger log = LoggerFactory.getLogger(JPAFileLogDAO.class);

	@Override
	public Collection<FileLog> getAllUploadedFilePaths() {
		Query query = em.createQuery("from FileLog ORDER BY dateUploaded DESC");
		@SuppressWarnings("unchecked")
		List<FileLog> result = query.getResultList();
		return result;
	}

	@Override
	public boolean addUploadedFilePath(String name, String path, Date date, Integer errorCount, boolean flush) {
		FileLog f = new FileLog(name, path, date, errorCount);
		em.persist(f);
		return true;
	}

}
