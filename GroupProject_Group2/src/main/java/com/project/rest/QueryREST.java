package com.project.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.entities.Query;
import com.project.service.QueryService;

@Path("/queries")
public class QueryREST{
	
	@EJB
	private QueryService service;

	@GET
	@Path("/{userType}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Query> getQueriesByUserType(@PathParam("userType") int userType) {
		return service.getQueriesByUserType(userType);
	}
}
