package com.project.services;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.ejb.EJB;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.project.entities.BaseData;
import com.project.service.BaseDataService;

@RunWith(Arquillian.class)
public class BaseDataServiceEJBTest {

	@EJB
	private BaseDataService baseDataService;

	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(ZipImporter.class, "test.war")
				.importFrom(new File("target/GroupProject_Group2.war"))
				.as(WebArchive.class);
	}

	private Collection bulkLoader() throws Exception {
		Collection list = new ArrayList<BaseData>();
		for (int i = 0; i < 10; ++i) {
			BaseData b = new BaseData();
			b.setDate(new Date());
			b.setDuration(1000);
			list.add(b);
		}
		return list;
	}

	@Test
	public void testGetAllTestTableData() {

		assertTrue(baseDataService.getAllBaseData().isEmpty());

		try {
			baseDataService.addAllBaseData(bulkLoader());
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(10, baseDataService.getAllBaseData().size());

	}

}
