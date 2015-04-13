package com.project.rest;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.entities.BaseData;
import com.project.entities.EventCause;
import com.project.entities.FailureClass;
import com.project.entities.FileInfo;
import com.project.entities.UE;

@RunWith(Arquillian.class)
public class EventCauseRestTest {
	
	private static final Logger log = LoggerFactory.getLogger(EventCauseRestTest.class);
	FileInfo fileInfo = new FileInfo("filename.xml", "filepath");
	
		@Deployment
		public static WebArchive createDeployment() {
			
			PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeAndTestDependencies();
			
			File[] libraries = pom.resolve("org.apache.poi:poi").withTransitivity().asFile();
			
			return ShrinkWrap.create(WebArchive.class,"test.war")
					.addPackages(true, "com.project")
					.addAsLibraries(libraries)
					.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
	                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
		}
		
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
			em.createQuery("delete from EventCause").executeUpdate();
			tx.commit();
		}

		private void insertTestData() throws Exception, ParseException {
			BaseData b = new BaseData();
			EventCause eventCause = new EventCause(55, 5555, "eventCauseDescription");
			UE ue = new UE(5500, "uemarketingName", "uemanufacturer", "ueaccessCapability");
			FailureClass failureClass = new FailureClass(6, "test");
			b.setEventCauseFK(eventCause);
			b.setImsi(191911000023112L);
			b.setUeFK(ue);
			b.setFaliureClassFK(failureClass);
			b.setDate(new Date());
			tx.begin();
			em.joinTransaction();
			em.persist(eventCause);
			em.persist(ue);
			em.persist(failureClass);
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
		public void getEventCauseCombi(@ArquillianResteasyResource EventCauseREST evCauseRest) throws ParseException{
			final String code = "c00";
			final String imsi = "191911000023112";
			final String data = code + imsi;
			
			final List<String[]> info = evCauseRest.getEventCauseCombi(data);

			assertEquals(1, info.size());
			assertEquals(info.get(0)[1], "55");
			assertEquals(info.get(0)[5], "5555");
			assertEquals(info.get(0)[21], "eventCauseDescription");
			
			
		}
		@Test
		public void countUniqueEventCauseByModel(@ArquillianResteasyResource EventCauseREST evCauseRest) throws ParseException{
			final String code = "c00";
			final String model = "uemarketingName";
			final String data = code + model;
			
			final List<String[]> info = evCauseRest.countUniqueEventCauseByModel(data);

			assertEquals(1, info.size());
			assertEquals(info.get(0)[15], "1");
			assertEquals(info.get(0)[1], "55");
			assertEquals(info.get(0)[5], "5555");
			assertEquals(info.get(0)[21], "eventCauseDescription");
			
			
		}
		
		@Test
		public void getImsiByCauseClass(@ArquillianResteasyResource EventCauseREST evCauseRest) throws ParseException{
			final String code = "c00";
			final String causeClass = "6";
			final String data = code + causeClass;
			
			final List<String[]> info = evCauseRest.getImsiByCauseClass(data);

			assertEquals(1, info.size());
			assertEquals(info.get(0)[7], "191911000023112");
			assertEquals(info.get(0)[15], "1");
			
		}
//		@Test
//		public void countUniqueEventCauseByImsiDate(@ArquillianResteasyResource EventCauseREST evCauseRest) throws ParseException{
//			final String code = "c00";
//			final String imsi = "191911000023112";
//			final String dateS = "2015-04-01T09:00:00";
//			final String dateE = "3015-01-01T09:00:00";
//			final String data = code + imsi + dateS + dateE;
//						
//			final List<String[]> info = evCauseRest.countUniqueEventCauseByImsiDate(data);
//
//			assertEquals(1, info.size());
//			assertEquals(info.get(0)[7], "191911000023112");
//			assertEquals(info.get(0)[15], "1");
//			
//		}
//		@Test
//		public void getUniqueEventCauseByImsiByCauseCode(@ArquillianResteasyResource EventCauseREST evCauseRest) throws ParseException{
//			final String code = "c00";
//			final String imsi = "191911000023112";
//			final String causeCode = "55";
//			final String data = code + imsi + causeCode;
//						
//			final List<String[]> info = evCauseRest.getUniqueEventCauseByImsiByCauseCode(data);
//
//			assertEquals(1, info.size());
//			assertEquals(info.get(0)[7], "191911000023112");
//			assertEquals(info.get(0)[15], "1");
//			assertEquals(info.get(0)[21], "eventCauseDescription");
//			
//		}
		
	

}
