package com.project.fileupload;

import static org.junit.Assert.*;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class DirectoryWatcherCreatorTest{
	
	@Test
	public void testCreatedAtStartup(){
		assertTrue("DirectoryWatcherCreator did not start", DirectoryWatcherCreator.isStarted());
	}
	
	@Test
	public void testCorrectFileSystemLocation(){
		assertNotNull("File system path not created", DirectoryWatcherCreator.getFileSystemPath());
		if(System.getProperty("os.name").substring(0, 7).toLowerCase().equals("windows")){
			assertEquals("Windows file system path not created successfully",  "c:/upload/", DirectoryWatcherCreator.getFileSystemPath());
		}else{
			assertEquals("Linux file system path not created successfully",  "/upload/", DirectoryWatcherCreator.getFileSystemPath());
		}
	}
	
	@Test
	public void testDirectoryExistsOnSystem(){
		assertTrue("Directory " + DirectoryWatcherCreator.getFileSystemPath() + " is not available", DirectoryWatcherCreator.isDirectoryAvailable());
	}
}
