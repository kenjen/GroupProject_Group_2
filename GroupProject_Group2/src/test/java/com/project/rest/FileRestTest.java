package com.project.rest;

import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.project.entities.FileInfo;

@RunWith(Arquillian.class)
public class FileRestTest {
	
	FileInfo fileInfo = new FileInfo("filename.xml", "filepath");
	
	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction tx;

	@Before
	public void setUpPersistenceModuleForTest() throws Exception {
		clearDataFromPersistenceModule();
		insertTestData();
		beginTransaction();
	}

	private void clearDataFromPersistenceModule() throws Exception {
		tx.begin();
		em.joinTransaction();
		em.createQuery("delete from FileInfo").executeUpdate();
		tx.commit();
	}

	private void insertTestData() throws Exception, ParseException {
		tx.begin();
		em.joinTransaction();
		em.persist(fileInfo);
		tx.commit();
		em.clear();
	}

	private void beginTransaction() throws Exception {
		tx.begin();
		em.joinTransaction();
	}

	@After
	public void endTransaction() throws Exception {
		tx.commit();
	}
	
	@Test
	public void testGetAllUploadedFilePaths(@ArquillianResteasyResource FileRest fileRest){
		
		List<FileInfo> info = (List<FileInfo>) fileRest.getAllUploadedFilePaths();
		
		assert(info.size()==1);
		assert(info.get(0).getFilename().equals("filename.xml"));
		assert(info.get(0).getFilepath().equals("filepath"));
		assert(info.get(0).equals(fileInfo));
		
		info = null;
	}
	
	@Test
	public void addAndRemoveUploadedFilePath(@ArquillianResteasyResource FileRest fileRest){
		String fileName = "/addedFileName.xls";
		String filePath = "addedFilePath";
		
		fileRest.addUploadedFilePath(fileName + "::" + filePath);
		
		List<FileInfo> info = (List<FileInfo>) fileRest.getAllUploadedFilePaths();
		
		assert(info.size()==2);
		assert(info.get(1).getFilename().equals("/addedFileName.xls"));
		assert(info.get(1).getFilepath().equals("addedFilePath"));
		
		fileRest.removeFileFromDatabase(fileName);
		
		List<FileInfo> info2 = (List<FileInfo>) fileRest.getAllUploadedFilePaths();
		
		assert(info2.size()==1);
		assert(!info2.get(1).getFilename().equals("addedFileName.xls"));
		assert(!info2.get(1).getFilepath().equals("addedFilePath"));
		
		info = null;
		info2 = null;
	}
}
