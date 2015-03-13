package com.project.rest;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.entities.FailureClass;
import com.project.service.FailureClassService;

@Path("/failureclass")
@Stateless
public class FailureClassRest {
	
	@EJB
	private FailureClassService service;
	
	@GET
	@Path("/getallfailureclasses")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<FailureClass> getAllFailures() {
		return service.getAllFailureClasses();
	}

}
