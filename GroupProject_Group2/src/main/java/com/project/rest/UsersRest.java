package com.project.rest;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.entities.User;
import com.project.service.UserServiceLocal;

@Path("/users")
@Stateless
public class UsersRest {

	@EJB
	private UserServiceLocal service;

	@GET
	@Path("/getallusers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAllFailures() {
		return service.getUsers();
	}
	
	@POST
	@Path("/getuser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(User user) {
		return service.getUser(user);
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public void addUser(User user){
		 service .addUser(user);
	}
	
	

}
