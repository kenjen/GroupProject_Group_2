package com.project.dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

//@RunWith(MockitoJUnitRunner.class)
public class EventCauseEJBTest {
	@Mock
	private EntityManager mockedEntityManager;
	@EJB
	private EventCauseEJB eventCauseEjb;
	@Mock
	private Query mockedQuery;

	
	//@Test
	public void testGetFailuresIdsByIMSI() {
		List<String[]> eventCauseCombiIds= new ArrayList<String[]>(); 
		// check if it requested the named query
		when(mockedEntityManager.createNamedQuery("findEventCauseByIMSI")).thenReturn(mockedQuery);
		// check if it asked for the result list of the named query
	    when(mockedQuery.getResultList()).thenReturn(eventCauseCombiIds);
	    List<Object[]> failuresByImsi = eventCauseEjb.getFailuresIdsByIMSI(344930000000011L);
	 
       // check if it returned the result list of the named query
        assertSame(eventCauseCombiIds, failuresByImsi);
      //  assertEquals("the size expected is ",256,failuresByImsi.size());
	  
	}

	
	//@Test
	public void testGetImsiByCauseClass() {
		List<String[]> eventCauseCombiIds= new ArrayList<String[]>();
		// check if it requested the named query
		when(mockedEntityManager.createNamedQuery("FailureClass.getImsiByCauseClass")).thenReturn(mockedQuery);
		// check if it asked for the result list of the named query
		when(mockedQuery.getResultList()).thenReturn(eventCauseCombiIds);
		List<Object[]> failuresByImsi = eventCauseEjb.getImsiByCauseClass(1);
		// check if it returned the result list of the named query
		assertSame(eventCauseCombiIds, failuresByImsi);
		        
	}

	//@Test
	public void testCountUniqueEventCauseByModel() {
		List<String[]> eventCauseCombiIds= new ArrayList<String[]>();
		// check if it requested the named query
		when(mockedEntityManager.createNamedQuery("countUniqueEventCauseByModel")).thenReturn(mockedQuery);
		// check if it asked for the result list of the named query
		when(mockedQuery.getResultList()).thenReturn(eventCauseCombiIds);
		List<Object[]> failuresByImsi = eventCauseEjb.countUniqueEventCauseByModel("G410");
		// check if it returned the result list of the named query
		assertSame(eventCauseCombiIds, failuresByImsi);
		        
	}



}
