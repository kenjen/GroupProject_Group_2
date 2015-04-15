package com.project.dao;

import java.io.File;
import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.entities.FileInfo;

@Stateless
@Local
public class JPAFileDAO implements FileDAO{
	
	@PersistenceContext
	private EntityManager em;
	
	private static final Logger log = LoggerFactory.getLogger(JPAFileDAO.class);

	@Override
	public Collection<FileInfo> getAllUploadedFilePaths() {
		Query query = em.createQuery("from FileInfo");
		@SuppressWarnings("unchecked")
		List<FileInfo> result = query.getResultList();
		return result;
	}

	@Override
	public boolean addUploadedFilePath(String name, String path, boolean flush) {
		FileInfo f = new FileInfo(name, path);
		em.persist(f);
		log.info("File Persisted: ../" + f.getFilepath() + f.getFilename());
		return true;
	}

	@Override
	public void removeFileFromDatabase(String fileName) {
		Query query = em.createQuery("delete from FileInfo f where f.filename = :fileName");
		query.setParameter("fileName", fileName);
		query.executeUpdate();
	}

	@Override
	public void removeFileFromServer(String fileName) {
		FileInfo fileInfo = em.find(FileInfo.class, fileName);
		File file = new File(fileInfo.getFilepath());
		file.delete();
	}
}