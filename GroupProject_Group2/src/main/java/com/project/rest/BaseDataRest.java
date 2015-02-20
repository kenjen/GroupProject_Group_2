package com.project.rest;

import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.entities.BaseData;
import com.project.entities.FailureClass;
import com.project.entities.UE;
import com.project.service.BaseDataService;

@Path("/base_data")
public class BaseDataRest {
	@EJB
	private BaseDataService baseDataService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<BaseData> getAllBaseData() {
		return (List) baseDataService.getAllBaseData();
	}
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void addAllBaseData(){		
	}
	
	@GET
	@Path("/testue")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<UE> addUEKeys() {
		return baseDataService.addUEKeys();
	}
	
	@GET
	@Path("/testfc")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<FailureClass> addFailureClassKeys() {
		return baseDataService.addFailureClassKeys();
	}

}
