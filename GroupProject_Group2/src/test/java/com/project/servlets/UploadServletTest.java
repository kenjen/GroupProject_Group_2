package com.project.servlets;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import com.project.reader.excel.ExcelBaseDataRead;
import com.project.reader.excel.ExcelLookupDataRead;

@RunWith(Arquillian.class)
public class UploadServletTest extends Mockito{

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(ZipImporter.class, "test.war")
				.importFrom(new File("target/GroupProject_Group2.war"))
				.as(WebArchive.class);
	}
	
	@Inject
	private UploadServlet servlet;
	
	@Test
	public void testDoGet() throws ServletException, IOException{
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		ExcelLookupDataRead lookupReader = mock(ExcelLookupDataRead.class);
		ExcelBaseDataRead baseReader = mock(ExcelBaseDataRead.class);
		//ServletContext context = mock(ServletContext.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		
		servlet.setBaseDataReader(baseReader);
		servlet.setLookupDataReader(lookupReader);
		
		when(request.getParameter("fileSelection")).thenReturn("C:/filename/test.xls");
		when(baseReader.getInvalidRowCount()).thenReturn(30);
		//when(servlet.getServletContext()).thenReturn(context);
		when(request.getRequestDispatcher("/message.jsp")).thenReturn(dispatcher);
		
		servlet.doGet(request, response);
		
		verify(request, times(1)).getParameter("fileSelection");
		verify(request, times(1)).setAttribute("message", "Transfer to database completed successfully!<br>There were 30 invalid rows in the base data");
	}
	
	@Test
	public void testGetFileExtension() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		assertNotNull(servlet);
		Class[] cArg = new Class[1];
        cArg[0] = String.class;
		Method privateGetFileExtension = UploadServlet.class.getDeclaredMethod("getFileExtension", cArg);
		privateGetFileExtension.setAccessible(true);
		
		assertTrue(".xls".equals((String)privateGetFileExtension.invoke(servlet, "test.xls")));
		assertTrue(".xls".equals((String)privateGetFileExtension.invoke(servlet, "group_project/test.xls")));
		assertTrue(".xls".equals((String)privateGetFileExtension.invoke(servlet, "group_project\test.txt.xls")));
		
	}
	
	
	
	
}