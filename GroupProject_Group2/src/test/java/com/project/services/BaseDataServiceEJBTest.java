package com.project.services;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.PomEquippedResolveStage;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.project.entities.BaseData;
import com.project.service.BaseDataService;

@RunWith(Arquillian.class)
public class BaseDataServiceEJBTest {

	@EJB
	private BaseDataService baseDataService;

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction tx;

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

	@Before
	public void setUpPersistenceModuleForTest() throws Exception {
		clearDataFromPersistenceModule();
		insertTestData();
		beginTransaction();
	}

	private void clearDataFromPersistenceModule() throws Exception {
		tx.begin();
		em.joinTransaction();
		em.createQuery("delete from BaseData").executeUpdate();
		tx.commit();
	}

	private void insertTestData() throws Exception {
		tx.begin();
		em.joinTransaction();
		BaseData baseDataRecord = new BaseData();
		baseDataRecord.setDate(new Date());
		baseDataRecord.setDuration(1000);
		baseDataRecord.setNeVersion("11B");
		em.persist(baseDataRecord);
		tx.commit();
		em.clear();
	}

	private void beginTransaction() throws Exception {
		tx.begin();
		em.joinTransaction();
	}

	@SuppressWarnings("unused")
	private Collection<BaseData> bulkLoader() throws Exception {
		Collection<BaseData> list = new ArrayList<BaseData>();
		for (int i = 0; i < 10; ++i) {
			BaseData b = new BaseData();
			b.setDate(new Date());
			list.add(b);
		}
		return list;
	}

	@After
	public void endTransaction() throws Exception {
		tx.commit();
	}

	@Test
	public void testGetAllTestTableData() {

		List<BaseData> baseDataList = (List<BaseData>) baseDataService
				.getAllBaseData();

		Assert.assertEquals(1, baseDataList.size());
		int duration = baseDataList.get(0).getDuration();
		Assert.assertEquals(1000, duration);

	}

}
