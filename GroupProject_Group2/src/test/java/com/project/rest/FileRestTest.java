package com.project.rest;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.arquillian.container.test.api.Deployment;
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

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.*;
import static com.jayway.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import com.project.entities.FileInfo;

@RunWith(Arquillian.class)
public class FileRestTest {
	
	private static final Logger log = LoggerFactory.getLogger(FileRestTest.class);

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

	@Before
	public void setUpPersistenceModuleForTest() throws Exception {
		
	}

	@After
	public void endTransaction() throws Exception {
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testGetAllUploadedFilePaths() throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException{
		FileInfo fileInfo = new FileInfo("filename.xml", "filepath");
		tx.begin();
		em.joinTransaction();
		em.persist(fileInfo);
		tx.commit();
		
		/*Response response = get("http://localhost:8080/GroupProject_Group2/rest/file");
		log.info("response = " + response.asString());*/
		
		FileInfo[] files = given().when().get("http://localhost:8080/GroupProject_Group2/rest/file").as(FileInfo[].class);
		assert(files.length==1);
		assert(files[0].getFilename().equals("filename.xml"));
		assert(files[0].getFilepath().equals("filepath"));
		assert(files[0].equals(fileInfo));
	}
}
