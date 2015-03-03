package com.project.rest;

import java.util.Collection;
import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.service.EventCauseService;

@Path("/event_cause")
public class EventCauseREST{
	@EJB
	private EventCauseService service;

	@GET
	@Path("/{IMSI}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection getEventCauseCombi(@PathParam("IMSI") Long imsi) {
		Collection idsCombi = service.getFailuresIdsByIMSI(imsi);
		return idsCombi;
	}
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection getEventCause() {
		Collection ids = service.getFailuresIds();
		return ids;
	}
	
}
