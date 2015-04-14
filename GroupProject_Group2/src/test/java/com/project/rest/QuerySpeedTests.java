package com.project.rest;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;

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
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.dao.BaseDataDAO;
import com.project.dao.ErrorBaseDataDAO;
import com.project.dao.LookUpDataDAO;
import com.project.entities.BaseData;
import com.project.entities.FailureClass;
import com.project.entities.UE;
import com.project.fileupload.UploadServlet;
import com.project.reader.ReadBase;
import com.project.reader.ReadLookup;
import com.project.reader.excel.ExcelBaseDataRead;
import com.project.reader.excel.ExcelLookupDataRead;
import com.project.service.FileService;

@RunWith(Arquillian.class)
public class QuerySpeedTests extends Mockito{

	private static final Logger log = LoggerFactory.getLogger(QuerySpeedTests.class);
	
	@EJB
	private BaseDataDAO baseDataDao;
	@EJB
	private ErrorBaseDataDAO errorDao;
	@EJB
	private LookUpDataDAO lookupDao;
	@EJB
	private FileService fileService;
	
	ReadLookup lookupDataReader = new ExcelLookupDataRead();
	ReadBase baseDataReader = new ExcelBaseDataRead();

	@Inject
	private UploadServlet servlet;
	
	@Deployment
	public static WebArchive createDeployment() {
		
		PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeAndTestDependencies();
		
		File[] libraries = pom.resolve("org.apache.poi:poi").withTransitivity().asFile();
		
		return ShrinkWrap.create(WebArchive.class,"test.war")
				.addPackages(true, "com.project")
				.addAsLibraries(libraries)
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@Before
	public void setUpPersistenceModuleForTest() throws Exception {
		 testUploadAndTransferSpeed();
	}
	
	@After
	public void endTransaction() throws Exception {
		
	}
		

	public void testUploadAndTransferSpeed() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		File f = new File("testfiles");
		f.mkdir();
		String filePath = f.getAbsolutePath();
		String finalfilePath = filePath + File.separator + "Dataset 3A.xls";
		Class[] cArg = new Class[1];
        cArg[0] = String.class;
		Method privateParse = UploadServlet.class.getDeclaredMethod("parse", cArg);
		privateParse.setAccessible(true);
		privateParse.invoke(servlet, finalfilePath);
		privateParse.setAccessible(false);
	}
	
	@Test
	public void dropdownQuery1Test(@ArquillianResteasyResource EventCauseREST eventCauseREST, @ArquillianResteasyResource BaseDataRest baseDataRest){
		List<BaseData> allBaseData =  baseDataRest.getAllBaseData();
		Long imsiLong = allBaseData.get(0).getImsi();
		final String imsi = imsiLong.toString();
		final String code = "c00";
		final String data = code + imsi;
		Long start = System.currentTimeMillis();
		eventCauseREST.getEventCauseCombi(data);
		Long end = System.currentTimeMillis();
		log.info("dropdown 1 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
	}
	
	@Test
	public void dropdownQuery2Test(@ArquillianResteasyResource BaseDataRest baseDataRest) throws ParseException{
		final String code = "c00";
		final String startDate = "2000-01-01T09:00:00";
		final String endDate = "2025-01-01T09:00:00";
		final String data = code+startDate + endDate;
		Long start = System.currentTimeMillis();
		baseDataRest.getImsiByDateRange(data);
		Long end = System.currentTimeMillis();
		log.info("dropdown 2 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
	}
	
	@Test
	public void dropdownQuery3Test(@ArquillianResteasyResource UeRest ueRest) throws ParseException{
		List<UE> allModels = ueRest.getAllPhoneModels();
		Integer tacInt = allModels.get(0).getTac();
		final  String tac = tacInt.toString();
		final String code = "c00";
		final String startDate = "2000-01-01T09:00:00";
		final String endDate = "2025-01-01T09:00:00";
		final String data = code+startDate + endDate+tac;
		Long start = System.currentTimeMillis();
		ueRest.countCallFailuresDateRange(data);
		Long end = System.currentTimeMillis();
		log.info("dropdown 3 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
	}
	
	@Test
	public void dropdownQuery4Test(@ArquillianResteasyResource BaseDataRest baseDataRest) throws ParseException{
		final String code = "c00";
		final String startDate = "2000-01-01T09:00:00";
		final String endDate = "2025-01-01T09:00:00";
		final String data = code+startDate + endDate;
		Long start = System.currentTimeMillis();
		baseDataRest.getCountImsiByDateRange(data);
		Long end = System.currentTimeMillis();
		log.info("dropdown 4 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
	}
	
	@Test
	public void dropdownQuery5Test(@ArquillianResteasyResource EventCauseREST eventCauseREST, @ArquillianResteasyResource UeRest ueRest) throws ParseException{
		List<UE> allModels = ueRest.getAllPhoneModels();
		String model = allModels.get(0).getMarketingName();
		final String code = "c00";
		final String data = code+model;
		Long start = System.currentTimeMillis();
		eventCauseREST.countUniqueEventCauseByModel(data);
		Long end = System.currentTimeMillis();
		log.info("dropdown 5 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
	}
	
	@Test
	public void dropdownQuery6Test(@ArquillianResteasyResource BaseDataRest baseDataRest) throws ParseException{
		List<BaseData> allBaseData =  baseDataRest.getAllBaseData();
		Long imsiLong = allBaseData.get(0).getImsi();
		final String imsi = imsiLong.toString();
		final String code = "c00";
		final String startDate = "2000-01-01T09:00:00";
		final String endDate = "2025-01-01T09:00:00";
		final String data = code+startDate+endDate+imsi;
		Long start = System.currentTimeMillis();
		baseDataRest.getCountSingleImsiByDateRange(data);
		Long end = System.currentTimeMillis();
		log.info("dropdown 6 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
	}
	
	@Test
	public void dropdownQuery7Test(@ArquillianResteasyResource BaseDataRest baseDataRest) throws ParseException{
		final String code = "c00";
		final String startDate = "2000-01-01T09:00:00";
		final String endDate = "2025-01-01T09:00:00";
		final String data = code+startDate+endDate;
		Long start = System.currentTimeMillis();
		baseDataRest.getCountTop10ComboBetweenDates(data);
		Long end = System.currentTimeMillis();
		log.info("dropdown 7 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
	}
	
	@Test
	public void dropdownQuery8Test(@ArquillianResteasyResource BaseDataRest baseDataRest) throws ParseException{
		final String code = "c00";
		List<BaseData> allBaseData =  baseDataRest.getAllBaseData();
		Long imsiLong = allBaseData.get(0).getImsi();
		final String imsi = imsiLong.toString();
		final String data = code+imsi;
		Long start = System.currentTimeMillis();
		baseDataRest.getCauseCodeByIMSI(data);
		Long end = System.currentTimeMillis();
		log.info("dropdown 8 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
	}
	
	@Test
	public void dropdownQuery9Test(@ArquillianResteasyResource BaseDataRest baseDataRest) throws ParseException{
		final String code = "c00";
		final String startDate = "2000-01-01T09:00:00";
		final String endDate = "2025-01-01T09:00:00";
		final String data = code+startDate+endDate;
		Long start = System.currentTimeMillis();
		baseDataRest.getCountTop10ImsiByDateRange(data);
		Long end = System.currentTimeMillis();
		log.info("dropdown 9 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
	}
	
	@Test
	public void dropdownQuery10Test(@ArquillianResteasyResource EventCauseREST eventCauseREST, @ArquillianResteasyResource FailureClassRest failureClassRest) throws ParseException{
		List<FailureClass> failureClassInt = (List<FailureClass>) failureClassRest.getAllFailures();
		Integer failureInt = failureClassInt.get(0).getFailureClass();
		final String failureClass = failureInt.toString();
		final String code = "c00";
		final String data = code+failureClass;
		Long start = System.currentTimeMillis();
		eventCauseREST.getImsiByCauseClass(data);
		Long end = System.currentTimeMillis();
		log.info("dropdown 10 time taken = " + (end-start));
		assertTrue(end-start<1000); // 1 second
	}
	
}