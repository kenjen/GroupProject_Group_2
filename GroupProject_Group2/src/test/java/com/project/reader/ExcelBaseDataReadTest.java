package com.project.reader;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.logging.Logger;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import com.project.dao.BaseDataDAO;
import com.project.dao.EventCauseDAO;
import com.project.dao.LookUpDataDAO;
import com.project.dao.MccMncDAO;
import com.project.dao.UeDAO;
import com.project.entities.BaseData;
import com.project.entities.EventCause;
import com.project.entities.FailureClass;
import com.project.entities.MccMnc;
import com.project.entities.UE;
import com.project.reader.excel.ExcelBaseDataRead;
import com.project.reader.excel.ExcelLookupDataRead;
import com.project.reader.excel.ExcellValidator;

@RunWith(Arquillian.class)
public class ExcelBaseDataReadTest {

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(ZipImporter.class, "test.war")
				.importFrom(new File("target/GroupProject_Group2.war"))
				.as(WebArchive.class);
	}

	private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm";
	private static final SimpleDateFormat sdf = new SimpleDateFormat(
			DATE_FORMAT);

	private static final String OUTPUT_FILE = "C:/jboss-as-7.1.1.Final/test_workbook.xls";
	private static final String INPUT_FILE = "C:/jboss-as-7.1.1.Final/test_workbook.xls";
	private static final int sheetNumber = 0;

	private static ReadLookup mockLookup;
	private static Validator mockValidator;
	private static ReadBase mockBaseDataRead;
	private static EventCauseDAO eventCauseRecord;

	@EJB
	private ReadBase readBase;

	@EJB
	private BaseDataDAO baseDataDao;

	@EJB
	private EventCauseDAO eventCauseDao;

	@EJB
	private UeDAO ueDao;

	@EJB
	private MccMncDAO mccMncDao;

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction tx;

	@BeforeClass
	public static void setUp() throws IOException {
		mockLookup = mock(ExcelLookupDataRead.class);
		mockValidator = mock(ExcellValidator.class);
		mockBaseDataRead = mock(ExcelBaseDataRead.class);
		eventCauseRecord = mock(EventCauseDAO.class);

		//eventCauseRecord.
		
		//when(eventCauseRecord.equals(obj);)
		//when(mockValidator.isValid(eventCauseRecord)).thenReturn(true);
	}

	@Before
	public void setUpPersistenceModuleForTest() throws Exception {
		// removeXlsWorkbook();
		createXlsWorkbook();
		clearLookupDataFromPersistenceModule();
		insertLookupTablesTestData();
		beginTransaction();
	}

	private void clearLookupDataFromPersistenceModule() throws Exception {
		tx.begin();
		em.joinTransaction();
		em.createQuery("delete from BaseData").executeUpdate();
		tx.commit();
		tx.begin();
		em.joinTransaction();
		em.createQuery("delete from EventCause").executeUpdate();
		tx.commit();
		tx.begin();
		em.joinTransaction();
		em.createQuery("delete from FailureClass").executeUpdate();
		tx.commit();
		tx.begin();
		em.joinTransaction();
		em.createQuery("delete from UE").executeUpdate();
		tx.commit();
		tx.begin();
		em.joinTransaction();
		em.createQuery("delete from MccMnc").executeUpdate();
		tx.commit();
	}

	private void insertLookupTablesTestData() throws Exception, ParseException {
		tx.begin();
		em.joinTransaction();
		em.persist(anEventCauseRecord());
		tx.commit();
		em.clear();
		tx.begin();
		em.joinTransaction();
		em.persist(aFailureClassRecord());
		tx.commit();
		em.clear();
		tx.begin();
		em.joinTransaction();
		em.persist(aUeRecord());
		tx.commit();
		em.clear();
		tx.begin();
		em.joinTransaction();
		em.persist(anMccMncRecord());
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

	private EventCause anEventCauseRecord() {
		EventCause eventCauseRecord = new EventCause();
		eventCauseRecord.setCauseCode(10);
		eventCauseRecord.setEventId(1);
		eventCauseRecord.setDescription("TestEvent");
		return eventCauseRecord;
	}

	private FailureClass aFailureClassRecord() {
		FailureClass failureClassRecord = new FailureClass();
		failureClassRecord.setFailureClass(10);
		failureClassRecord.setDescription("TestFailureClass");
		return failureClassRecord;
	}

	private MccMnc anMccMncRecord() {
		MccMnc mccMncRecord = new MccMnc();
		mccMncRecord.setMcc(1);
		mccMncRecord.setMnc(10);
		mccMncRecord.setCountry("TestCountry");
		mccMncRecord.setOperator("TestOperator");
		return mccMncRecord;
	}

	private UE aUeRecord() {
		UE ueRecord = new UE();
		ueRecord.setTac(100);
		ueRecord.setManufacturer("TestManufacturer");
		ueRecord.setMarketingName("TestMarketingName");
		ueRecord.setAccessCapability("TestAccessCapability");
		return ueRecord;
	}

	private void removeXlsWorkbook() {
		Path path = Paths.get(OUTPUT_FILE);
		try {
			Files.delete(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void createXlsWorkbook() throws IOException, ParseException {
		@SuppressWarnings("resource")
		Workbook workbook = new HSSFWorkbook();
		Sheet sheet = workbook.createSheet("base_data");
		// the label row
		HSSFRow row0 = (HSSFRow) sheet.createRow(0);
		HSSFCell cel000 = row0.createCell(0, HSSFCell.CELL_TYPE_STRING);
		cel000.setCellValue("Date / Time");
		HSSFCell cel001 = row0.createCell(1, HSSFCell.CELL_TYPE_STRING);
		cel001.setCellValue("Event Id");
		HSSFCell cel002 = row0.createCell(2, HSSFCell.CELL_TYPE_STRING);
		cel002.setCellValue("Failure Class");
		HSSFCell cel003 = row0.createCell(3, HSSFCell.CELL_TYPE_STRING);
		cel003.setCellValue("UE Type");
		HSSFCell cel004 = row0.createCell(4, HSSFCell.CELL_TYPE_STRING);
		cel004.setCellValue("Market");
		HSSFCell cel005 = row0.createCell(5, HSSFCell.CELL_TYPE_STRING);
		cel005.setCellValue("Operator");
		HSSFCell cel006 = row0.createCell(6, HSSFCell.CELL_TYPE_STRING);
		cel006.setCellValue("Cell Id");
		HSSFCell cel007 = row0.createCell(7, HSSFCell.CELL_TYPE_STRING);
		cel007.setCellValue("Duration");
		HSSFCell cel008 = row0.createCell(8, HSSFCell.CELL_TYPE_STRING);
		cel008.setCellValue("Cause Code");
		HSSFCell cel009 = row0.createCell(9, HSSFCell.CELL_TYPE_STRING);
		cel009.setCellValue("NE Version");
		HSSFCell cel010 = row0.createCell(10, HSSFCell.CELL_TYPE_STRING);
		cel010.setCellValue("IMSI");
		HSSFCell cel011 = row0.createCell(11, HSSFCell.CELL_TYPE_STRING);
		cel011.setCellValue("HISER3_ID");
		HSSFCell cel012 = row0.createCell(12, HSSFCell.CELL_TYPE_STRING);
		cel012.setCellValue("HISER32_ID");
		HSSFCell cel013 = row0.createCell(13, HSSFCell.CELL_TYPE_STRING);
		cel013.setCellValue("HISER321_ID");

		// the first valid data row
		HSSFRow row1 = (HSSFRow) sheet.createRow(1);
		// date
		HSSFCell cel100 = row1.createCell(0, HSSFCell.CELL_TYPE_NUMERIC);
		Date cell100Value = sdf.parse("01/10/2013 09:00:00");
		cel100.setCellValue(cell100Value);
		HSSFCellStyle dateCellStyle = (HSSFCellStyle) workbook
				.createCellStyle();
		short df = workbook.createDataFormat().getFormat(DATE_FORMAT);
		dateCellStyle.setDataFormat(df);
		cel100.setCellStyle(dateCellStyle);
		// event id
		HSSFCell cel101 = row1.createCell(1, HSSFCell.CELL_TYPE_NUMERIC);
		cel101.setCellValue(1);
		// failure class
		HSSFCell cel102 = row1.createCell(2, HSSFCell.CELL_TYPE_NUMERIC);
		cel102.setCellValue(10);
		// tac
		HSSFCell cel103 = row1.createCell(3, HSSFCell.CELL_TYPE_NUMERIC);
		cel103.setCellValue(100);
		// mcc
		HSSFCell cel104 = row1.createCell(4, HSSFCell.CELL_TYPE_NUMERIC);
		cel104.setCellValue(1);
		// mnc
		HSSFCell cel105 = row1.createCell(5, HSSFCell.CELL_TYPE_NUMERIC);
		cel105.setCellValue(10);
		// cell id
		HSSFCell cel106 = row1.createCell(6, HSSFCell.CELL_TYPE_NUMERIC);
		cel106.setCellValue(1);
		// duration
		HSSFCell cel107 = row1.createCell(7, HSSFCell.CELL_TYPE_NUMERIC);
		cel107.setCellValue(1000);
		// cause code
		HSSFCell cel108 = row1.createCell(8, HSSFCell.CELL_TYPE_NUMERIC);
		cel108.setCellValue(10);
		// ne version
		HSSFCell cel109 = row1.createCell(9, HSSFCell.CELL_TYPE_STRING);
		cel109.setCellValue("13A");
		// imsi
		HSSFCell cel110 = row1.createCell(10, HSSFCell.CELL_TYPE_NUMERIC);
		HSSFCellStyle style = (HSSFCellStyle) workbook.createCellStyle();
		style.setDataFormat(HSSFDataFormat.getBuiltinFormat("0"));
		cel110.setCellStyle(style);
		cel110.setCellValue(100000000000000L);
		// hier3
		HSSFCell cel111 = row1.createCell(11, HSSFCell.CELL_TYPE_NUMERIC);
		cel111.setCellStyle(style);
		cel111.setCellValue(12345678910111213L);
		// hier32
		HSSFCell cel112 = row1.createCell(12, HSSFCell.CELL_TYPE_NUMERIC);
		cel112.setCellStyle(style);
		cel112.setCellValue(12345678910111213L);
		// hier321
		HSSFCell cel113 = row1.createCell(13, HSSFCell.CELL_TYPE_NUMERIC);
		cel113.setCellStyle(style);
		cel113.setCellValue(12345678910111213L);

		// create the file
		FileOutputStream fileOut = new FileOutputStream(OUTPUT_FILE);
		workbook.write(fileOut);
		fileOut.close();
	}

	@Test
	public void testSetInputFile() {
	}

	@Test
	public void testSetSheetNumber() {
	}

	@Test
	public void testRead() throws Exception {
		ExcelBaseDataRead baseDataReader = new ExcelBaseDataRead();
		ExcellValidator excelValidator = new ExcellValidator();
		baseDataReader.setInputFile(INPUT_FILE);
		baseDataReader.setSheetNumber(sheetNumber);

		Logger log = Logger.getLogger(ExcelBaseDataReadTest.class);
		
		EventCause eventCauseRecord = ExcelLookupDataRead.getEventCause(10, 1);
		
		log.info("EVENT_CAUSE RETURNED FROM LOOKUP: " + eventCauseRecord);

		List<BaseData> baseDataList = (List<BaseData>) baseDataDao
				.getAllBaseData();

		assertEquals("C:/jboss-as-7.1.1.Final/test_workbook.xls", INPUT_FILE);

	}

}
