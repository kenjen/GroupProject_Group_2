package com.project.dao;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.project.dao.JPABaseDataDAO;

@RunWith(Arquillian.class)
public class JPAErrorBaseDataDAOTest {
	
	@Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClass(JPABaseDataDAO.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
	/*
	@Inject
	JPABaseDataDAO dao;
	
	@Test
	public void testEntityManager(){
		assertNotEquals(null, dao.entityManager);
	}*/

}
