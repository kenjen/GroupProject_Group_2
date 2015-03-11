package com.project.fileupload;

import static org.junit.Assert.*;

import java.io.File;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class DirectoryWatcherCreatorTest{
	
	@Deployment
	public static WebArchive createDeployment() {
		
		PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeAndTestDependencies();
		
		File[] libraries = pom.resolve("org.apache.poi:poi").withTransitivity().asFile();
		
		return ShrinkWrap.create(WebArchive.class,"test.war")
				.addPackages(true, "com.project")
				.addAsLibraries(libraries)
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml");
	}
	
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
