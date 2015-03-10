package com.project.rest;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.entities.BaseData;
import com.project.entities.EventCause;
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
	
	@GET
	@Path("/testallue")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<UE> getUEs() {
		return baseDataService.getUEs();
	}
	
	@GET
	@Path("/testallfc")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<FailureClass> getFailureClasses() {
		return baseDataService.getFailureClasses();
	}
	
	@GET
	@Path("/eventcause")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<EventCause> getAllEventCause() {
		 Collection<BaseData> base = baseDataService.getAllBaseData();
		 List<EventCause> ec = new ArrayList<EventCause>();
		 for(BaseData bd : base){
			 ec.add(bd.getEventCauseFK());
		 }
		 
		 return ec;
	}
	
	@GET
	@Path("/eventcause/{IMSI}")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<EventCause> getAllEventCause(@PathParam("IMSI") Long imsi) {
		 Collection<BaseData> base = baseDataService.getAllBaseData();
		 List<EventCause> ec = new ArrayList<EventCause>();
		 for(BaseData bd : base){
			 if(bd.getImsi().equals(imsi)){
				 ec.add(bd.getEventCauseFK());
			 }
		 }
		 return ec;
	}
	
	@GET
	@Path("/imsibetweendates/{dates}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String[]> getImsiByDateRange(@PathParam("dates") String dates) throws ParseException{
		if(dates.length()==0){
			/*List<String[]> emptyCollection = Collections.emptyList();
			return emptyCollection;*/
			return null;
		}
		String s = dates.substring(3, 22);
		String e = dates.substring(22);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date start = sdf.parse(s);
		Date end = sdf.parse(e);
		
		List<Object[]> list = baseDataService.getImsiByDateRange(start, end);
		
		ArrayList<String[]> aList = new ArrayList<String[]>();
		for(Object[] obj : list){
			Date d = new Date( ((Timestamp)obj[0]).getTime() );
			String[] str = {"", "", "", d.toString(), "", "", "", Objects.toString(obj[1]), "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
			aList.add(str);
		}
		return aList;
	}
	
	@GET
	@Path("/countimsibetweendates/{dates}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String[]> getCountImsiByDateRange(@PathParam("dates") String dates) throws ParseException{
		if(dates.length()==0){
			return null;
		}
		String s = dates.substring(3, 22);
		String e = dates.substring(22);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date start = sdf.parse(s);
		Date end = sdf.parse(e);
		
		List<Object[]> list = baseDataService.getCountImsiBetweenDates(start, end);
		
		ArrayList<String[]> aList = new ArrayList<String[]>();
		for(Object[] obj : list){
			String[] str = {"", "", "", "", Objects.toString(obj[1]), "", "", Objects.toString(obj[2]), "", "", "", "", "", "", "", Objects.toString(obj[0]), "", "", "", "", "", ""};
			aList.add(str);
		}
		return aList;
	}
	
	@GET
	@Path("/countsingleimsibetweendates/{dates}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String[]> getCountSingleImsiByDateRange(@PathParam("dates") String dates) throws ParseException{
		if(dates.length()<23){
			return null;
		}
		String s = dates.substring(3, 22);
		String e = dates.substring(22, 41);
		String i = dates.substring(41);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date start = sdf.parse(s);
		Date end = sdf.parse(e);
		long imsi = Long.parseLong(i);
		
		List<Object[]> list = baseDataService.getCountSingleImsiBetweenDates(start, end, imsi);
		
		ArrayList<String[]> aList = new ArrayList<String[]>();
		for(Object[] obj : list){
			String[] str = {"", "", "", "", "", "", "", Objects.toString(obj[1]), "", "", "", "", "", "", "", Objects.toString(obj[0]), "", "", "", "", "", ""};
			aList.add(str);
		}
		return aList;
	}
	
	@GET
	@Path("/counttop10imsibetweendates/{dates}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String[]> getCountTop10ImsiByDateRange(@PathParam("dates") String dates) throws ParseException{
		if(dates.length()==0){
			return null;
		}
		String s = dates.substring(3, 22);
		String e = dates.substring(22);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date start = sdf.parse(s);
		Date end = sdf.parse(e);
		
		List<Object[]> list = baseDataService.getCountTop10ImsiBetweenDates(start, end);
		
		ArrayList<String[]> aList = new ArrayList<String[]>();
		for(Object[] obj : list){
			String[] str = {"", "", "", "", "", "", "", Objects.toString(obj[1]), "", "", "", "", "", "", "", Objects.toString(obj[0]), "", "", "", "", "", ""};
			aList.add(str);
		}
		return aList;
	}
	
	@GET
	@Path("/counttop10mccmnccelldid_bydate/{dates}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String[]> getCountTop10ComboBetweenDates(@PathParam("dates") String dates) throws ParseException{
		if(dates.length()==0){
			return null;
		}
		String s = dates.substring(3, 22);
		String e = dates.substring(22);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date start = sdf.parse(s);
		Date end = sdf.parse(e);
		
		List<Object[]> list = baseDataService.getCountTop10ComboBetweenDates(start, end);
		
		ArrayList<String[]> aList = new ArrayList<String[]>();
		for(Object[] obj : list){
			String[] str = {"", "", Objects.toString(obj[3]), "", "", "", "", "", "", "", "", "", "", "", "", Objects.toString(obj[0]), Objects.toString(obj[1]), Objects.toString(obj[2]), "", "", "", ""};
			aList.add(str);
		}
		return aList;
	}
	
	@Path("/uniqueCauseByImsi/{IMSI}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String[]> getCauseCodeByIMSI(@PathParam("IMSI") String input) {
		String imsiString = input.substring(3);
		Long imsi = Long.parseLong(imsiString);
		List<Object[]> list = baseDataService.getfindUniqueCauseByIMSI(imsi);
		
		ArrayList<String[]> aList = new ArrayList<String[]>();
		for(Object[] obj : list){
			String[] str = {"", Objects.toString(obj[1]), "", "", "","", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
			aList.add(str);
		}
		return aList;
	}

}
