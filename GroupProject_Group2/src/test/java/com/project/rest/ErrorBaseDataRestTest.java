package com.project.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import com.project.entities.ErrorBaseData;

@RunWith(Arquillian.class)
public class ErrorBaseDataRestTest {
	
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
		em.createQuery("delete from ErrorBaseData").executeUpdate();
		tx.commit();
	}

	private void insertTestData() throws Exception, ParseException {
		ErrorBaseData b = new ErrorBaseData();
		tx.begin();
		b.setCellId(1);
		b.setDate(new Date());
		b.setDuration(1);
		b.setHier321Id("errorTest");
		b.setHier32Id("errorTest");
		b.setHier3Id("errorTest");
		Long l = System.currentTimeMillis();
		b.setImsi(l);
		em.joinTransaction();
		em.persist(b);
		tx.commit();
		em.clear();
		
		b = null;
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
	public void getEventCauseCombiTest(@ArquillianResteasyResource ErrorBaseDataRest errorBaseDataRest){
		List<ErrorBaseData> allErrorBaseData = errorBaseDataRest.getAllErrorBaseData();
		assertEquals(1, allErrorBaseData.size());
		assertTrue(allErrorBaseData.get(0).getCellId() == 1);
		assertEquals(allErrorBaseData.get(0).getHier321Id(), "errorTest");
		assertEquals(allErrorBaseData.get(0).getHier32Id(), "errorTest");
		assertEquals(allErrorBaseData.get(0).getHier3Id(), "errorTest");
		
		allErrorBaseData = null;
	}

	
}
