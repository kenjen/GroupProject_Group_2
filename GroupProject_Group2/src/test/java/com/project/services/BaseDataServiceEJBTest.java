package com.project.services;

import static org.junit.Assert.*;

import javax.ejb.EJB;
import javax.jws.WebMethod;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
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
	public BaseDataService getBaseDataService(){
		return baseDataService;
	}
	@WebMethod(exclude = true)
	public void setBaseDataServic(BaseDataService baseDataService) {
		this.baseDataService = baseDataService;
	}

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap
				.create(JavaArchive.class, "test.jar")
				.addClasses(BaseDataServiceEJB.class, BaseDataService.class,
						BaseData.class, BaseDataDAO.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	public void testGetAllTestTableData() {
		assertNotNull(baseDataService);
	}

	@Test
	public void testAddAllBaseData() {
	}

}
