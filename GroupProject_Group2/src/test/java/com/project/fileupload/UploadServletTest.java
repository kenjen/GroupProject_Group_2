package com.project.fileupload;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
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
public class UploadServletTest extends Mockito{

	private static final Logger log = LoggerFactory.getLogger(UploadServletTest.class);
	
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
	
	
	@Test
	public void testDoPost() throws IOException, ServletException{
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		ServletContext context = mock(ServletContext.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		FileDAO dao = mock(FileDAO.class);
		Part part = mock(Part.class);
		
		when(request.getServletContext()).thenReturn(context);
		when(context.getRealPath("")).thenReturn("C:" + File.separator + "file" + File.separator + "path");
		when(request.getPart("file")).thenReturn(part);
		when(part.getHeader("content-disposition")).thenReturn("some useless data ; filename=:excell.xls");
		when(dao.addUploadedFilePath(anyString(), anyString(), anyBoolean())).thenReturn(true);
		when(request.getRequestDispatcher("/message.jsp")).thenReturn(dispatcher);
		
		servlet.doPost(request, response);
		
		//TODO test not entering loop
		/*verify(part, times(0)).write(anyString());
		verify(request, times(0)).setAttribute("message", "Upload to server completed successfully!");
		verify(request, times(1)).setAttribute("message", "Upload to server failed<br>Must end in .xls");
		verify(dao, times(0)).addUploadedFilePath(anyString(), anyString(), anyBoolean());*/
		
		verify(response, times(1)).sendRedirect("/GroupProject_Group2/upload.html#Upload Successful");
	}
	
	@Test
	public void testDoGet() throws ServletException, IOException{
		HttpServletRequest request = mock(HttpServletRequest.class);
		HttpServletResponse response = mock(HttpServletResponse.class);
		ExcelLookupDataRead lookupReader = mock(ExcelLookupDataRead.class);
		ExcelBaseDataRead baseReader = mock(ExcelBaseDataRead.class);
		RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		
		servlet.setBaseDataReader(baseReader);
		servlet.setLookupDataReader(lookupReader);
		
		when(request.getParameter("fileSelection")).thenReturn("C:/filename/test.xls");
		when(baseReader.getInvalidRowCount()).thenReturn(30);
		when(request.getRequestDispatcher("/message.jsp")).thenReturn(dispatcher);
		
		servlet.doGet(request, response);
		
		verify(response, times(1)).sendRedirect("/GroupProject_Group2/upload.html#Transfer to database completed successfully!"
				+ "<br>There were 30 invalid rows in the base data");
	}
	
	@Test
	public void testGetFileExtension() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		assertNotNull(servlet);
		Class[] cArg = new Class[1];
        cArg[0] = String.class;
		Method privateGetFileExtension = UploadServlet.class.getDeclaredMethod("getFileExtension", cArg);
		privateGetFileExtension.setAccessible(true);
		
		assertTrue(".xls".equals(privateGetFileExtension.invoke(servlet, "test.xls")));
		assertTrue(".xls".equals(privateGetFileExtension.invoke(servlet, "group_project/test.xls")));
		assertTrue(".xls".equals(privateGetFileExtension.invoke(servlet, "group_project\test.txt.xls")));
		
		privateGetFileExtension.setAccessible(false);
	}
	
	@Test
	public void testUploadAndTransferSpeed() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		File f = new File("testfiles");
		f.mkdir();
		String filePath = f.getAbsolutePath();
		String finalfilePath = filePath + File.separator + "Dataset 3A.xls";
		Long start = System.currentTimeMillis();
		
		Class[] cArg = new Class[1];
        cArg[0] = String.class;
		Method privateParse = UploadServlet.class.getDeclaredMethod("parse", cArg);
		privateParse.setAccessible(true);
		int invalidRecords = (int) privateParse.invoke(servlet, finalfilePath);
		
		Long end = System.currentTimeMillis();
		log.info("time taken = " + (end-start));
		assert(end-start<105000); // 1 min 45 seconds, 15 second reduction to be certain less than 2 minutes every time
		assertEquals(3, invalidRecords);
	}
}