package com.project.dao;

import static org.junit.Assert.*;

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

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.project.entities.BaseData;

@RunWith(Arquillian.class)
public class JPABaseDataDAOTest {

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(ZipImporter.class, "test.war")
				.importFrom(new File("target/GroupProject_Group2.war"))
				.as(WebArchive.class);
	}

	@EJB
	private BaseDataDAO jpaBaseDataDao;

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction tx;

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
		em.persist(baseDataRecord);
		tx.commit();
		em.clear();
	}

	private Collection bulkLoader() throws Exception {
		Collection list = new ArrayList<BaseData>();
		for (int i = 0; i < 10; ++i) {
			BaseData b = new BaseData();
			b.setDate(new Date());
			list.add(b);
		}
		return list;
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
	public void testThatWeCanAddData() {
		List<BaseData> dbList = (List<BaseData>) jpaBaseDataDao
				.getAllBaseData();
		assertEquals(2, dbList.size());
	}

	@Test
	@Ignore
	public void testToAddData() {
		try {
			jpaBaseDataDao.addAllBaseData(bulkLoader());
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(11, jpaBaseDataDao.getAllBaseData().size());
	}
}
