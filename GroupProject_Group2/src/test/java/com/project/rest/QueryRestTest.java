package com.project.rest;

import static org.junit.Assert.assertEquals;
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
		beginTransaction();
	}

	private void clearDataFromPersistenceModule() throws Exception {
		tx.begin();
		em.joinTransaction();
		em.createQuery("delete from Query").executeUpdate();
		tx.commit();
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
	public void getQueriesByUserTypeTest(@ArquillianResteasyResource QueryREST queryRest){
		List<Query> allqueries = queryRest.getQueriesByUserType(1);
		assertEquals(10, allqueries.size());
		assertEquals(4, (int)allqueries.get(3).getId());
		assertEquals(1, (int)allqueries.get(3).getPermission());
		assertEquals("Count failures and duration for each IMSI by Date", allqueries.get(3).getDisplayName());
		allqueries = null;
		
		List<Query> suppqueries = queryRest.getQueriesByUserType(2);
		assertEquals(6, suppqueries.size());
		assertEquals(2, (int)suppqueries.get(1).getId());
		assertEquals(2, (int)suppqueries.get(1).getPermission());
		assertEquals("List IMSI by Date Range", suppqueries.get(1).getDisplayName());
		suppqueries = null;
		
		List<Query> repqueries = queryRest.getQueriesByUserType(3);
		assertEquals(3, repqueries.size());
		assertEquals(1, (int)repqueries.get(0).getId());
		assertEquals(3, (int)repqueries.get(0).getPermission());
		assertEquals("EventId, CauseCode for IMSI", repqueries.get(0).getDisplayName());
		repqueries = null;
	}

	
}
