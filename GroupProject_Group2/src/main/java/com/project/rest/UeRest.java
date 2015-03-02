package com.project.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.entities.UE;
import com.project.service.UeService;

@Path("/ue")
public class UeRest {
	
	@EJB
	private UeService ueService;
	
	@GET
	@Path("/getAllModels")
	@Produces(MediaType.APPLICATION_JSON)
	public List<UE> getAllPhoneModels(){
		return ueService.getAllModels();
	}
	
	@GET
	@Path("/callfailures/{input}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String[]> getCallFailuresDateRange(@PathParam("input") String input) throws ParseException{
		if(input.length()<4){
			/*List<String[]> emptyCollection = Collections.emptyList();
			return emptyCollection;*/
			return null;
		}
		String dateS = input.substring(3, 22);
		String dateE = input.substring(22, 41);
		String tacStr = input.substring(41);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		Date start = sdf.parse(dateS);
		Date end = sdf.parse(dateE);
		Integer tac = Integer.parseInt(tacStr);
		
		List<Object[]> list = ueService.getCallFailuresDateRange(start, end, tac);
		ArrayList<String[]> aList = new ArrayList<String[]>();
		for(Object[] obj : list){
			
			String[] str = {"", "", "", "", "", "", "", "", "", "", "", Objects.toString(obj[1]), "", "", "", Objects.toString(obj[0]), "", "", Objects.toString(obj[2]), "", "", ""};
			aList.add(str);
		}
		return aList;
	}

}
