package com.project.rest;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
			String[] str = {"", Objects.toString(obj[1]), "", Objects.toString(obj[3]), "", Objects.toString(obj[0]), "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", Objects.toString(obj[2])};
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
			String[] str = {"", Objects.toString(obj[1]), "", "", "", Objects.toString(obj[0]), "", "", "", "", "", "", "", "", "", Objects.toString(obj[2]), "", "", "", "", "", Objects.toString(obj[3])};
			aList.add(str);
		}
		return aList;
	}
	
	@GET
	@Path("/causeByImsi/{IMSI}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String[]> getCauseCodeByIMSI(@PathParam("IMSI") String input) {
		String imsiString = input.substring(3);
		Long imsi = Long.parseLong(imsiString);
		List<Object[]> list = service.getFailuresIdsByIMSI(imsi);
		
		ArrayList<String[]> aList = new ArrayList<String[]>();
		for(Object[] obj : list){
			String[] str = {"", Objects.toString(obj[1]), "", "", "","", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
			aList.add(str);
		}
		return aList;
	}
	
	@GET
	@Path("/imsiByCauseClass/{failureClass}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String[]> getImsiByCauseClass(@PathParam("failureClass") String input) throws ParseException{
		String failureClassString = input.substring(3);
		int failureClass = Integer.parseInt(failureClassString);
		List<Object[]> list = service.getImsiByCauseClass(failureClass);
		
		ArrayList<String[]> aList = new ArrayList<String[]>();
		for(Object[] obj : list){
			String[] str = {"", "", "", "", "","", "", Objects.toString(obj[0]), "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
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
	
	@GET
	@Path("/causeByImsiDate/{data}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String[]> countUniqueEventCauseByImsiDate(@PathParam("data") String data){
		try{
			String[] splitData = data.split("::");
			Long imsi = Long.parseLong(splitData[0]);
			String s = splitData[1].substring(3, 22);
			String e = splitData[1].substring(22);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date start = sdf.parse(s);
			Date end = sdf.parse(e);
			
			List<Object[]> list = service.countUniqueEventCauseByImsiDate(imsi, start, end);
			
			ArrayList<String[]> aList = new ArrayList<String[]>();
			for(Object[] obj : list){
				String[] str = {Objects.toString(obj[0]), Objects.toString(obj[1]), Objects.toString(obj[2])};
				aList.add(str);
			}
			return aList;
		}catch(ParseException e1){
			
		}
		return null;
	}
	
	@GET
	@Path("/causeByImsiByCauseCode/{data}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String[]> getUniqueEventCauseByImsiByCauseCode(@PathParam("data") String data) throws ParseException{
	
			String[] splitData = data.split("::");
			Integer causeCode = Integer.parseInt(splitData[0]);
			Long imsi = Long.parseLong(splitData[1].substring(3));

			
			List<Object[]> list = service.getUniqueEventCauseByImsiByCauseCode(imsi, causeCode);
			
			ArrayList<String[]> aList = new ArrayList<String[]>();
			for(Object[] obj : list){
				String[] str = {Objects.toString(obj[0]), Objects.toString(obj[1]), Objects.toString(obj[2])};
				aList.add(str);
			}
			return aList;

	}
	
}
