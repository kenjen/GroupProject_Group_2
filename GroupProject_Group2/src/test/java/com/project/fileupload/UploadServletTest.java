package com.project.fileupload;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.dao.BaseDataDAO;
import com.project.dao.ErrorBaseDataDAO;
import com.project.dao.FileDAO;
import com.project.dao.LookUpDataDAO;
import com.project.fileupload.UploadServlet;
import com.project.reader.ReadBase;
import com.project.reader.ReadLookup;
import com.project.reader.excel.ExcelBaseDataRead;
import com.project.reader.excel.ExcelLookupDataRead;
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
	
	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction tx;
	
	@Before
	public void clearDataFromDatabase() throws Exception {
		tx.begin();
		em.joinTransaction();
		em.createNativeQuery("SET FOREIGN_KEY_CHECKS=0");
		em.createQuery("delete from BaseData").executeUpdate();
		em.createQuery("delete from ErrorBaseData").executeUpdate();
		/*tx.commit();
		tx.begin();*/
		//em.joinTransaction();
		em.createQuery("delete from EventCause").executeUpdate();
		/*tx.commit();
		tx.begin();*/
		//em.joinTransaction();
		em.createQuery("delete from FailureClass").executeUpdate();
		/*tx.commit();
		tx.begin();*/
		//em.joinTransaction();
		em.createQuery("delete from UE").executeUpdate();
		/*tx.commit();
		tx.begin();*/
		//em.joinTransaction();
		em.createQuery("delete from MccMnc").executeUpdate();
		tx.commit();
		
		tx.begin();
		em.joinTransaction();

		em.createNativeQuery("SET FOREIGN_KEY_CHECKS=1");
		tx.commit();
		tx.begin();
		em.joinTransaction();
	}

	@After
	public void endTransaction() throws Exception {
		tx.commit();
	}

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
}