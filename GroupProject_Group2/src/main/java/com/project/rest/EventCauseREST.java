package com.project.rest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
	@Path("/eventCauseByImsi/{IMSI}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String[]> getEventCauseCombi(@PathParam("IMSI") String input) {
		String imsiString = input.substring(3);
		Long imsi = Long.parseLong(imsiString);
		List<Object[]> list = service.getFailuresIdsByIMSI(imsi);
		
		ArrayList<String[]> aList = new ArrayList<String[]>();
		for(Object[] obj : list){
			String[] str = {"", Objects.toString(obj[1]), "", "", "", Objects.toString(obj[0]), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
			aList.add(str);
		}
		return aList;
	}
	
	@GET
	@Path("/countEventCauseByModel/{phoneModel}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String[]> countUniqueEventCauseByModel(@PathParam("phoneModel") String model) {
		String phoneModel = model.substring(3);
		List<Object[]> list = service.countUniqueEventCauseByModel(phoneModel);
		
		ArrayList<String[]> aList = new ArrayList<String[]>();
		for(Object[] obj : list){
			String[] str = {"", Objects.toString(obj[1]), "", "", "", Objects.toString(obj[0]), "", "", "", "", "", "", "", "", "", Objects.toString(obj[2]), "", "", "", "", "", ""};
			aList.add(str);
		}
		return aList;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Collection getEventCause() {
		Collection ids = service.getFailuresIds();
		return ids;
	}
	
}
