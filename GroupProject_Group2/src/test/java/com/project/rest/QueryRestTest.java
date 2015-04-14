package com.project.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

import com.project.entities.Query;

@RunWith(Arquillian.class)
public class QueryRestTest {
	
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
		em.createQuery("delete from Query").executeUpdate();
		tx.commit();
	}

	private void insertTestData() throws Exception, ParseException {
		Query query = new Query(1,1,"test");
		tx.begin();
		em.joinTransaction();
		em.persist(query);
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
	public void getEventCauseCombiTest(@ArquillianResteasyResource QueryREST queryRest){
		List<Query> allqueries = queryRest.getEventCauseCombi(1);
		assertEquals(1, allqueries.size());
		assertTrue(allqueries.get(0).getId() == 1);
		assertTrue(allqueries.get(0).getPermission() == 1);
		assertEquals(allqueries.get(0).getDisplayName(), "test");
		allqueries = null;
	}

	
}
