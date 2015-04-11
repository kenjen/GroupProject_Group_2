package com.project.rest;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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

	@GET
	@Path("/specificFailuresForTopTenCombi/{data}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String[]> specificFailuresForTopTenCombi(
			@PathParam("data") String data) {
		String url = "";
		try {
			url = java.net.URLDecoder.decode(data, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		try {
			String[] requestData = url.split(",");

			String country = requestData[0];
			String operator = requestData[1];
			Integer cellId = Integer.parseInt(requestData[2]);
			String requestStartDate = requestData[3].substring(3, 22);
			String requestEndDate = requestData[3].substring(22);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date startDate = sdf.parse(requestStartDate);
			Date endDate = sdf.parse(requestEndDate);

			List<Object[]> dbQueryData = service.specificFailuresForTopTenCombi(
					country, operator, cellId, startDate, endDate);

			ArrayList<String[]> dataResponse = new ArrayList<String[]>();
			for (Object[] obj : dbQueryData) {
				String[] str = { Objects.toString(obj[0]),
						Objects.toString(obj[1]), Objects.toString(obj[2]) };
				dataResponse.add(str);
			}
			return dataResponse;
		} catch (ParseException e) {
		}
		return null;
	}

}
