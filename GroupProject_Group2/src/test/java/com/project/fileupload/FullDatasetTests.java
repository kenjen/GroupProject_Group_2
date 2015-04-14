package com.project.fileupload;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import javax.ejb.EJB;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.entities.BaseData;
import com.project.entities.FailureClass;
import com.project.entities.UE;
import com.project.rest.BaseDataRest;
import com.project.rest.EventCauseREST;
import com.project.rest.FailureClassRest;
import com.project.rest.UeRest;

@RunWith(Arquillian.class)
public class FullDatasetTests {
	
	@EJB
	private static DirectoryWatcherInterface directoryWatcher;
	@Inject
	private UploadServlet servlet;
	@PersistenceContext
	EntityManager em;
	@Inject
	UserTransaction tx;
	private static final Logger log = LoggerFactory
			.getLogger(FullDatasetTests.class);
	
	@Before
	public void clearDataFromDatabase() throws Exception {
		tx.begin();
		em.joinTransaction();
		//em.createNativeQuery("SET FOREIGN_KEY_CHECKS=0");
		em.createQuery("delete from BaseData").executeUpdate();
		em.createQuery("delete from ErrorBaseData").executeUpdate();
		em.createQuery("delete from EventCause").executeUpdate();
		em.createQuery("delete from FailureClass").executeUpdate();
		em.createQuery("delete from UE").executeUpdate();
		em.createQuery("delete from MccMnc").executeUpdate();
		tx.commit();
		
		tx.begin();
		em.joinTransaction();

		/*em.createNativeQuery("SET FOREIGN_KEY_CHECKS=1");
		tx.commit();
		tx.begin();
		em.joinTransaction();*/
	}

	@After
	public void endTransaction() throws Exception {
		tx.commit();
	}
	
	@Test
	public void UploadTests(
			@ArquillianResteasyResource BaseDataRest baseDataRest, @ArquillianResteasyResource EventCauseREST eventCauseREST,
			@ArquillianResteasyResource UeRest ueRest, @ArquillianResteasyResource FailureClassRest failureClassRest)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {
		
		File f = new File("testfiles");
		f.mkdir();
		String filePath = f.getAbsolutePath();
		String finalfilePath = filePath + File.separator + "Dataset 3B.xls";
		Long start = System.currentTimeMillis();

		Class[] cArg = new Class[1];
		cArg[0] = String.class;
		Method privateParse = UploadServlet.class.getDeclaredMethod("parse", cArg);
		privateParse.setAccessible(true);
		int invalidRecords = (int) privateParse.invoke(servlet, finalfilePath);

		Long end = System.currentTimeMillis();
		log.info("time taken = " + (end - start));
		assert (end - start < 105000); // 1 min 45 seconds, 15 second reduction
										// to be certain less than 2 minutes
										// every time
		assertEquals(3, invalidRecords);
		
		List<BaseData> allBaseData =  baseDataRest.getAllBaseData();
		List<UE> allModels = ueRest.getAllPhoneModels();
		Long imsiLong = allBaseData.get(0).getImsi();
		List<FailureClass> failureClassInt = (List<FailureClass>) failureClassRest.getAllFailures();
		
		final String imsi = imsiLong.toString();
		final String code = "c00";
		final String startDate = "2000-01-01T09:00:00";
		final String endDate = "2025-01-01T09:00:00";

		String data = code + imsi;
		start = System.currentTimeMillis();
		eventCauseREST.getEventCauseCombi(data);
		end = System.currentTimeMillis();
		log.info("dropdown 1 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
		
		data = code+startDate + endDate;
		start = System.currentTimeMillis();
		baseDataRest.getImsiByDateRange(data);
		end = System.currentTimeMillis();
		log.info("dropdown 2 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
		
		Integer tacInt = allModels.get(0).getTac();
		final String tac = tacInt.toString();
		data = code+startDate + endDate+tac;
		start = System.currentTimeMillis();
		ueRest.countCallFailuresDateRange(data);
		end = System.currentTimeMillis();
		log.info("dropdown 3 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
		
		data = code+startDate + endDate;
		start = System.currentTimeMillis();
		baseDataRest.getCountImsiByDateRange(data);
		end = System.currentTimeMillis();
		log.info("dropdown 4 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
		
		String model = allModels.get(0).getMarketingName();
		data = code+model;
		start = System.currentTimeMillis();
		eventCauseREST.countUniqueEventCauseByModel(data);
		end = System.currentTimeMillis();
		log.info("dropdown 5 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
		
		data = code+startDate+endDate+imsi;
		start = System.currentTimeMillis();
		baseDataRest.getCountSingleImsiByDateRange(data);
		end = System.currentTimeMillis();
		log.info("dropdown 6 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
		
		data = code+startDate+endDate;
		start = System.currentTimeMillis();
		baseDataRest.getCountTop10ComboBetweenDates(data);
		end = System.currentTimeMillis();
		log.info("dropdown 7 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
		
		data = code+imsi;
		start = System.currentTimeMillis();
		baseDataRest.getCauseCodeByIMSI(data);
		end = System.currentTimeMillis();
		log.info("dropdown 8 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
		
		data = code+startDate+endDate;
		start = System.currentTimeMillis();
		baseDataRest.getCountTop10ImsiByDateRange(data);
		end = System.currentTimeMillis();
		log.info("dropdown 9 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
		
		Integer failureInt = failureClassInt.get(0).getFailureClass();
		final String failureClass = failureInt.toString();
		data = code+failureClass;
		start = System.currentTimeMillis();
		eventCauseREST.getImsiByCauseClass(data);
		end = System.currentTimeMillis();
		log.info("dropdown 10 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
		
		
		File directory;
		if((System.getProperty("os.name").substring(0, 7).toLowerCase()).equals("windows")){
			String fileSystemPath = "c:/upload/";
			directory = new File(fileSystemPath);
		}else{
			String fileSystemPath = "/upload/";
			directory = new File(fileSystemPath);
		}
		
		File watcherFile = new File("testfiles");
		watcherFile.mkdir();
		String watcherfilePath = f.getAbsolutePath();
		String watcherfinalfilePath = watcherfilePath + File.separator + "SampleDataset.xls";
		File sourceFile = new File(watcherfinalfilePath);
		
		File destFile = new File(directory + File.separator + "SampleDataset.xls");
		log.info("srcFile = " + sourceFile.getAbsolutePath());
		log.info("destFile = " + destFile.getAbsolutePath());
		sourceFile.renameTo(destFile);
		//log.info(file.getAbsolutePath());
		
		try {
			//Files.copy(sourceFile, destFile);
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("test finished");
		destFile.renameTo(sourceFile);
		assertEquals(1, directoryWatcher.getUploadsThisSession());
	}

}
