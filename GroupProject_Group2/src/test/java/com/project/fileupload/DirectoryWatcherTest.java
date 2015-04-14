package com.project.fileupload;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;

import javax.ejb.EJB;

import org.jboss.arquillian.junit.Arquillian;
import org.jboss.util.file.Files;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(Arquillian.class)
public class DirectoryWatcherTest {
	
	@EJB
	private static DirectoryWatcherInterface directoryWatcher;
	
	private static final Logger log = LoggerFactory.getLogger(DirectoryWatcherTest.class);
	
	@Test
	public void testDirectoryListener(){
		File directory;
		if((System.getProperty("os.name").substring(0, 7).toLowerCase()).equals("windows")){
			String fileSystemPath = "c:/upload/";
			directory = new File(fileSystemPath);
		}else{
			String fileSystemPath = "/upload/";
			directory = new File(fileSystemPath);
		}
		
		File f = new File("testfiles");
		f.mkdir();
		String filePath = f.getAbsolutePath();
		String finalfilePath = filePath + File.separator + "SampleDataset.xls";
		File sourceFile = new File(finalfilePath);
		
		File destFile = new File(directory + File.separator + "SampleDataset.xls");
		log.info("srcFile = " + sourceFile.getAbsolutePath());
		log.info("destFile = " + destFile.getAbsolutePath());
		sourceFile.renameTo(destFile);
		//log.info(file.getAbsolutePath());
		
		try {
			//Files.copy(sourceFile, destFile);
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("test finished");
		assertEquals(1, directoryWatcher.getUploadsThisSession());
	}
}
