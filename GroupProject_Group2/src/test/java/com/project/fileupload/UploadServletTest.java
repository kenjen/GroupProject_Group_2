package com.project.fileupload;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.dao.BaseDataDAO;
import com.project.dao.ErrorBaseDataDAO;
import com.project.dao.FileDAO;
import com.project.dao.LookUpDataDAO;
import com.project.entities.BaseData;
import com.project.entities.FailureClass;
import com.project.entities.UE;
import com.project.fileupload.UploadServlet;
import com.project.reader.ReadBase;
import com.project.reader.ReadLookup;
import com.project.reader.excel.ExcelBaseDataRead;
import com.project.reader.excel.ExcelLookupDataRead;
import com.project.rest.BaseDataRest;
import com.project.rest.EventCauseREST;
import com.project.rest.FailureClassRest;
import com.project.rest.UeRest;
import com.project.service.FileService;

@RunWith(Arquillian.class)
public class UploadServletTest extends Mockito {

	private static final Logger log = LoggerFactory
			.getLogger(UploadServletTest.class);

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

	@Test
	public void testDoPost() throws IOException, ServletException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		ServletContext context = mock(ServletContext.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		FileDAO dao = mock(FileDAO.class);
		Part part = mock(Part.class);

		when(request.getServletContext()).thenReturn(context);
		when(context.getRealPath("")).thenReturn(
				"C:" + File.separator + "file" + File.separator + "path");
		when(request.getPart("file")).thenReturn(part);
		when(part.getHeader("content-disposition")).thenReturn(
				"some useless data ; filename=:excell.xls");
		when(dao.addUploadedFilePath(anyString(), anyString(), anyBoolean()))
				.thenReturn(true);
		when(request.getRequestDispatcher("/message.jsp")).thenReturn(
				dispatcher);

		servlet.doPost(request, response);

		// TODO test not entering loop
		/*
		 * verify(part, times(0)).write(anyString()); verify(request,
		 * times(0)).setAttribute("message",
		 * "Upload to server completed successfully!"); verify(request,
		 * times(1)).setAttribute("message",
		 * "Upload to server failed<br>Must end in .xls"); verify(dao,
		 * times(0)).addUploadedFilePath(anyString(), anyString(),
		 * anyBoolean());
		 */

		verify(response, times(1)).sendRedirect(
				"/GroupProject_Group2/upload.html#Upload Successful");
	}

	@Test
	public void testDoGet() throws ServletException, IOException {
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		ExcelLookupDataRead lookupReader = mock(ExcelLookupDataRead.class);
		ExcelBaseDataRead baseReader = mock(ExcelBaseDataRead.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);

		servlet.setBaseDataReader(baseReader);
		servlet.setLookupDataReader(lookupReader);

		when(request.getParameter("fileSelection")).thenReturn(
				"C:/filename/test.xls");
		when(baseReader.getInvalidRowCount()).thenReturn(30);
		when(request.getRequestDispatcher("/message.jsp")).thenReturn(
				dispatcher);

		servlet.doGet(request, response);

		verify(response, times(1)).sendRedirect(
				"/GroupProject_Group2/upload.html#Transfer to database completed successfully!"
						+ "<br>There were 30 invalid rows in the base data");
	}

	@Test
	public void testGetFileExtension() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		// assertNotNull(servlet);
		Class[] cArg = new Class[1];
		cArg[0] = String.class;
		Method privateGetFileExtension = UploadServlet.class.getDeclaredMethod(
				"getFileExtension", cArg);
		privateGetFileExtension.setAccessible(true);

		assertTrue(".xls".equals(privateGetFileExtension.invoke(servlet,
				"test.xls")));
		assertTrue(".xls".equals(privateGetFileExtension.invoke(servlet,
				"group_project/test.xls")));
		assertTrue(".xls".equals(privateGetFileExtension.invoke(servlet,
				"group_project\test.txt.xls")));

		privateGetFileExtension.setAccessible(false);
	}

	@Test
	public void SpeedTests(
			@ArquillianResteasyResource BaseDataRest baseDataRest, @ArquillianResteasyResource EventCauseREST eventCauseREST,
			@ArquillianResteasyResource UeRest ueRest, @ArquillianResteasyResource FailureClassRest failureClassRest)
			throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ParseException {
		
		File f = new File("testfiles");
		f.mkdir();
		String filePath = f.getAbsolutePath();
		String finalfilePath = filePath + File.separator + "Dataset 3A.xls";
		Long start = System.currentTimeMillis();

		Class[] cArg = new Class[1];
		cArg[0] = String.class;
		Method privateParse = UploadServlet.class.getDeclaredMethod("parse",
				cArg);
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
	}
}