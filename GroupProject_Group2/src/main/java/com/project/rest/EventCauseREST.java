package com.project.rest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.entities.EventCause;
import com.project.service.EventCauseService;

@Path("/base_data")
public class EventCauseREST {
	@EJB
	private EventCauseService service;

	@GET
	@Path("/{IMSI}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection getEventCauseCombi(@PathParam("IMSI") Long imsi) {
//		List<Object[]> ids = (List<Object[]>) service.getFailuresIdsByIMSI(imsi);
//		return ids;
		Collection ids = service.getFailuresIdsByIMSI(imsi);
		return ids;
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection getEventCause() {
		Collection ids = service.getFailuresIds();
		return ids;
	}
	
}
