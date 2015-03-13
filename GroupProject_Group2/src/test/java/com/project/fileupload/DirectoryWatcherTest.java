package com.project.fileupload;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.jboss.util.file.Files;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

@RunWith(Arquillian.class)
public class DirectoryWatcherTest extends Mockito{
	
	@Deployment
	public static WebArchive createDeployment() {
		
		PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeAndTestDependencies();
		
		File[] libraries = pom.resolve("org.apache.poi:poi").withTransitivity().asFile();
		
		return ShrinkWrap.create(WebArchive.class,"test.war")
				.addPackages(true, "com.project")
				.addAsLibraries(libraries)
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
	}
	
	/*@Mock
	DirectoryWatcherTransactionInterface dirWatchTransaction;
	
	@InjectMocks
	DirectoryWatcher watcher;
	
	@Test
	public void testPoll(){
		
		String fileSystemPath;
		String excellName = "excell.xls";
		String nonExcellName = "anything.txt";
		if((System.getProperty("os.name").substring(0, 7).toLowerCase()).equals("windows")){
			fileSystemPath = "c:/upload/";
		}else{
			fileSystemPath = "/upload/";
		}
		
		try {
			when(dirWatchTransaction.addFilePath(fileSystemPath, excellName)).thenReturn(true);
			when(dirWatchTransaction.addFilePath(fileSystemPath, nonExcellName)).thenReturn(false);
		} catch (IOException e) {
			fail("IOException thrown by method addFilePath");
		}
		when(dirWatchTransaction.removeFileFromDatabase(excellName)).thenReturn(true);
		when(dirWatchTransaction.removeFileFromDatabase(nonExcellName)).thenReturn(false);
		
		
		try {
			watcher.poll(fileSystemPath);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}*/
	
	// mock DirectoryWatcherTransaction to stop calls to the database
	/*@Test
	public void testPoll() throws NoSuchFieldException, SecurityException{
		String fileSystemPath;
		String excellName = "excell.xls";
		String nonExcellName = "anything.txt";
		if((System.getProperty("os.name").substring(0, 7).toLowerCase()).equals("windows")){
			fileSystemPath = "c:/upload/";
		}else{
			fileSystemPath = "/upload/";
		}
		File excellFile = new File(fileSystemPath + excellName);
		File nonExcellFile = new File(fileSystemPath + nonExcellName);
		File dummy = new File(fileSystemPath + "dummy.doc");
		DirectoryWatcherTransaction dirWatchTransaction = mock(DirectoryWatcherTransaction.class);
		
		DirectoryWatcher watcher = new DirectoryWatcher();
		
		Field privateField = DirectoryWatcher.class.getDeclaredField("dirWatchTransaction");
		privateField.setAccessible(true);
		watcher.dirWatchTransaction = dirWatchTransaction;
		privateField.setAccessible(false);
		
		try {
			watcher.poll(fileSystemPath);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			when(dirWatchTransaction.addFilePath(fileSystemPath, excellName)).thenReturn(true);
			when(dirWatchTransaction.addFilePath(fileSystemPath, nonExcellName)).thenReturn(false);
		} catch (IOException e) {
			fail("IOException thrown by method addFilePath");
		}
		when(dirWatchTransaction.removeFileFromDatabase(excellName)).thenReturn(true);
		when(dirWatchTransaction.removeFileFromDatabase(nonExcellName)).thenReturn(false);
		
		try{
			dummy.createNewFile();
			Thread.sleep(1100);
		}catch(Exception e){
			
		}
		
		try {
			excellFile.createNewFile();
			Thread.sleep(1100);
			verify(dirWatchTransaction, times(1)).addFilePath(fileSystemPath, excellName);
			verify(dirWatchTransaction, times(0)).addFilePath(fileSystemPath, nonExcellName);
			verify(dirWatchTransaction, times(1)).addFilePath(anyString(), anyString());
			verify(dirWatchTransaction, times(0)).removeFileFromDatabase(anyString());
			Files.delete(excellFile);
			verify(dirWatchTransaction, times(1)).addFilePath(fileSystemPath, excellName);
			verify(dirWatchTransaction, times(0)).addFilePath(fileSystemPath, nonExcellName);
			verify(dirWatchTransaction, times(1)).addFilePath(anyString(), anyString());
			verify(dirWatchTransaction, times(1)).removeFileFromDatabase(excellName);
			verify(dirWatchTransaction, times(0)).removeFileFromDatabase(nonExcellName);
			verify(dirWatchTransaction, times(1)).removeFileFromDatabase(anyString());
			nonExcellFile.createNewFile();
			verify(dirWatchTransaction, times(1)).addFilePath(fileSystemPath, excellName);
			verify(dirWatchTransaction, times(1)).addFilePath(fileSystemPath, nonExcellName);
			verify(dirWatchTransaction, times(2)).addFilePath(anyString(), anyString());
			verify(dirWatchTransaction, times(1)).removeFileFromDatabase(excellName);
			verify(dirWatchTransaction, times(0)).removeFileFromDatabase(nonExcellName);
			verify(dirWatchTransaction, times(1)).removeFileFromDatabase(anyString());
			Files.delete(nonExcellFile);
			verify(dirWatchTransaction, times(1)).addFilePath(fileSystemPath, excellName);
			verify(dirWatchTransaction, times(1)).addFilePath(fileSystemPath, nonExcellName);
			verify(dirWatchTransaction, times(2)).addFilePath(anyString(), anyString());
			verify(dirWatchTransaction, times(1)).removeFileFromDatabase(excellName);
			verify(dirWatchTransaction, times(1)).removeFileFromDatabase(nonExcellName);
			verify(dirWatchTransaction, times(2)).removeFileFromDatabase(anyString());
		} catch (Exception e) {
			fail("Exception thrown by method verifying");
		}
		
	}*/
}
