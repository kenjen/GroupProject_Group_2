package com.project.service;

import java.util.Collection;

import javax.ejb.Local;

import com.project.entities.User;

@Local
public interface UserService {
	
	Collection<User> getUsers();
	User getUser(User user);
	User getUserById(User user);
	User addUser(User user);
	void updateUser(User user);
	void deleteUser(User user);

}
