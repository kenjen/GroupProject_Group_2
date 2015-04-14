package com.project.rest;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

import com.project.entities.FileLog;

@RunWith(Arquillian.class)
public class FileLogRestTest {
	
	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction tx;
	
	private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

	@Before
	public void setUpPersistenceModuleForTest() throws Exception {
		clearDataFromPersistenceModule();
		insertTestData();
		beginTransaction();
	}

	private void clearDataFromPersistenceModule() throws Exception {
		tx.begin();
		em.joinTransaction();
		em.createQuery("delete from FileLog").executeUpdate();
		tx.commit();
	}

	private void insertTestData() throws Exception, ParseException {
		sdf.setLenient(false);
		Date date = sdf.parse("2013-10-10 09:00:00");
		FileLog fileLog = new FileLog("filename.xml", "filepath", date, 1);
		tx.begin();
		em.joinTransaction();
		em.persist(fileLog);
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
	public void testGetAllUploadedFilePaths(@ArquillianResteasyResource FileLogREST fileRest) throws ParseException{
		
		List<String[]> info =  fileRest.getAllUploadedFilePaths();
		
		assertEquals(info.get(0)[0], "2013-10-10 09:00:00");
		assertEquals(info.get(0)[1], "filename.xml");
		assertEquals(info.get(0)[2], "1");
	}
	
	@Test
	public void testAddUploadedFilePath(@ArquillianResteasyResource FileLogREST fileRest) throws ParseException{
		
		String data = "test::test.xml::2013-10-10 09:00:00::5";
		
		fileRest.addUploadedFilePath(data);
		
		List<String[]> info = fileRest.getAllUploadedFilePaths();

		assertEquals(info.get(1)[1], "test.xml");
		assertEquals(info.get(1)[2], "5");
	}
	
}
