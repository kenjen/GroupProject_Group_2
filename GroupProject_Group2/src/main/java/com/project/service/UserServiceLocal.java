package com.project.service;

import java.util.Collection;

import javax.ejb.Local;

import com.project.entities.User;

@Local
public interface UserServiceLocal {
	
	Collection<User> getUsers();
	User getUser(User user);
	void addUser(User user);

}
