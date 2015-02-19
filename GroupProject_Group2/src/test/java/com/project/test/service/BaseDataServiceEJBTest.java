package com.project.test.service;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.jws.WebMethod;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.runner.RunWith;

import com.project.dao.BaseDataDAO;
import com.project.entities.BaseData;
import com.project.service.BaseDataService;
import com.project.service.BaseDataServiceEJB;

@RunWith(Arquillian.class)
public class BaseDataServiceEJBTest {

	@EJB
	private BaseDataService baseDataService;
	@WebMethod(exclude = true)
	public BaseDataService getBaseDataService() {
		return baseDataService;
	}
	@WebMethod(exclude = true)
	public void setBaseDataService(BaseDataService baseDataService) {
		this.baseDataService = baseDataService;
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class, "test.jar")
				.addClasses(BaseDataDAO.class, BaseDataServiceEJB.class,
						BaseData.class, BaseDataService.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	public void testGetAllTestTableData() {
		BaseData baseDataRecord = new BaseData();
		List<BaseData> baseDataList = new ArrayList<BaseData>();
		baseDataRecord.setDate(new Date());
		baseDataList.add(baseDataRecord);
		baseDataService.addAllBaseData(baseDataList);
		assertTrue(baseDataService.getAllBaseData().size() > 0);
	}

	@Test
	public void testAddAllBaseData() {
		BaseData baseDataRecord = new BaseData();
		List<BaseData> baseDataList = new ArrayList<BaseData>();
		baseDataRecord.setDate(new Date());
		baseDataList.add(baseDataRecord);
		baseDataService.addAllBaseData(baseDataList);
		assertTrue(!baseDataService.getAllBaseData().isEmpty());
	}

}
