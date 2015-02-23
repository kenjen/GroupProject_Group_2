package com.project.servlets;

import static org.junit.Assert.*;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class UploadServletTest {

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(ZipImporter.class, "test.war")
				.importFrom(new File("target/GroupProject_Group2.war"))
				.as(WebArchive.class);
	}
	
	@Inject
	private UploadServlet servlet;
	
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