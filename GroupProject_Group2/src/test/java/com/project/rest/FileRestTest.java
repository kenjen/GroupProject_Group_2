package com.project.rest;

import java.io.File;
import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.project.entities.FileInfo;

@RunWith(Arquillian.class)
public class FileRestTest {
	
	private static final Logger log = LoggerFactory.getLogger(FileRestTest.class);
	FileInfo fileInfo = new FileInfo("filename.xml", "filepath");
	
	/*@ArquillianResource
	private URL deploymentURL;*/

	@Deployment
	public static WebArchive createDeployment() {
		
		PomEquippedResolveStage pom = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeAndTestDependencies();
		
		File[] libraries = pom.resolve("org.apache.poi:poi").withTransitivity().asFile();
		
		return ShrinkWrap.create(WebArchive.class,"test.war")
				.addPackages(true, "com.project")
				.addAsLibraries(libraries)
				.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction tx;
	
	@Inject
	FileRest fileRest;

	@Before
	public void setUpPersistenceModuleForTest() throws Exception {
		clearDataFromPersistenceModule();
		insertTestData();
		beginTransaction();
	}

	private void clearDataFromPersistenceModule() throws Exception {
		tx.begin();
		em.joinTransaction();
		em.createQuery("delete from FileInfo").executeUpdate();
		tx.commit();
	}

	private void insertTestData() throws Exception, ParseException {
		tx.begin();
		em.joinTransaction();
		em.persist(fileInfo);
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
	
	@Test
	public void testGetAllUploadedFilePaths(@ArquillianResteasyResource FileRest fileRest){
		
		final List<FileInfo> info = (List<FileInfo>) fileRest.getAllUploadedFilePaths();
		
		assert(info.size()==1);
		assert(info.get(0).getFilename().equals("filename.xml"));
		assert(info.get(0).getFilepath().equals("filepath"));
		assert(info.get(0).equals(fileInfo));
	}
	
	@Test
	public void addAndRemoveUploadedFilePath(@ArquillianResteasyResource FileRest fileRest){
		final String fileName = "/addedFileName.xls";
		final String filePath = "addedFilePath";
		
		fileRest.addUploadedFilePath(fileName + "::" + filePath);
		
		final List<FileInfo> info = (List<FileInfo>) fileRest.getAllUploadedFilePaths();
		
		assert(info.size()==2);
		assert(info.get(1).getFilename().equals("/addedFileName.xls"));
		assert(info.get(1).getFilepath().equals("addedFilePath"));
		
		fileRest.removeFileFromDatabase(fileName);
		
		final List<FileInfo> info2 = (List<FileInfo>) fileRest.getAllUploadedFilePaths();
		
		assert(info2.size()==1);
		assert(!info2.get(1).getFilename().equals("addedFileName.xls"));
		assert(!info2.get(1).getFilepath().equals("addedFilePath"));
	}
}
