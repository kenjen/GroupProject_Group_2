package com.project.rest;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.project.entities.User;
import com.project.service.UserService;

@Path("/users")
@Stateless
public class UsersRest {

	@EJB
	private UserService service;

	@GET
	@Path("/getallusers")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<User> getAllUsers() {
		return service.getUsers();
	}
	
	@POST
	@Path("/getuser")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser(User user) {
		return service.getUser(user);
	}
	
	@POST
	@Path("/getuserbyid")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserById(User user) {
		return service.getUserById(user);
	}
	
	
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User addUser(User user){
		 return service.addUser(user);
	}
	
	@POST
	@Path("/updateuser")
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser(User user) {
		 service.updateUser(user);
	}
	
	@DELETE
	@Path("/deleteuser")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteUser(User user){
		service.deleteUser(user);
	}
	

}
