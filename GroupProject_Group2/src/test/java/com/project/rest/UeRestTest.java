package com.project.rest;

import static org.junit.Assert.*;

import java.text.ParseException;
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

import com.project.entities.BaseData;
import com.project.entities.FileInfo;
import com.project.entities.UE;

@RunWith(Arquillian.class)
public class UeRestTest {
	
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
		em.createQuery("delete from BaseData").executeUpdate();
		tx.commit();
		tx.begin();
		em.joinTransaction();
		em.createQuery("delete from UE").executeUpdate();
		tx.commit();
	}

	private void insertTestData() throws Exception, ParseException {
		UE ue = new UE(4231, "uemarketingName", "uemanufacturer", "ueaccessCapability");
		BaseData b = new BaseData();
		b.setUeFK(ue);
		b.setDate(new Date());
		tx.begin();
		em.joinTransaction();
		em.persist(ue);
		em.persist(b);
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
	public void countCallFailuresDateRange(@ArquillianResteasyResource UeRest ueRest) throws ParseException{
		String code = "c00";
		String dateS = "2015-04-01T09:00:00";
		String dateE = "3015-01-01T09:00:00";
		String tac = "4231";
		String data = code + dateS + dateE + tac;
		
		List<String[]> info = ueRest.countCallFailuresDateRange(data);

		assertEquals(1, info.size());
		assertEquals(info.get(0)[15], "1");
		assertEquals(info.get(0)[11], "4231");
		assertEquals(info.get(0)[18], "uemarketingName");
		assertEquals(info.get(0)[19], "uemanufacturer");
		assertEquals(info.get(0)[20], "ueaccessCapability");
		info = null;
	}
}
