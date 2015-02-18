package com.project.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.entities.BaseData;
import com.project.entities.ErrorBaseData;
import com.project.service.BaseDataService;
import com.project.service.ErrorBaseDataService;

@Path("/error_base_data")
public class ErrorBaseDataRest {
	@EJB
	private ErrorBaseDataService errorBaseDataService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ErrorBaseData> getAllErrorBaseData() {
		return (List) errorBaseDataService.getAllTestTableData();
	}

}
