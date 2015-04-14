package com.project.service;

import java.util.Collection;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import com.project.dao.UserDAO;
import com.project.entities.User;

@Stateless
@Local
public class UserServiceEJB implements UserService{
	
	@EJB
	private UserDAO dao;

	@Override
	public Collection<User> getUsers() {
		return dao.getAllUsers();
	}

	
	@Override
	public User getUser(User user) {
		return dao.getUser(user);
	}
	
	@Override
	public User addUser(User user){
		 return dao.addUser(user);
	}
	
	@Override
	public void updateUser(User user){
		 dao.updateUser(user);
	}


	@Override
	public void deleteUser(User user) {
		dao.deleteUser(user);
		
	}

	@Override
	public User getUserById(User user) {
		return dao.getUserById(user);
	}
	



}
