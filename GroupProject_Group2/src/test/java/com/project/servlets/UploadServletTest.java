package com.project.servlets;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.project.servlets.UploadServlet;

@RunWith(Arquillian.class)
public class UploadServletTest {
	
	@Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
            .addClass(UploadServlet.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
    }
	/*
	
	@Test
	public void testDoPost(){
		
	}
	*/
}
