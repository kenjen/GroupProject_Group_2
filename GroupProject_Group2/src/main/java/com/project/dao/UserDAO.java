package com.project.dao;

import java.util.Collection;

import com.project.entities.User;

public interface UserDAO {
	
	Collection<User> getAllUsers();
	User getUser(User user);
	void addUser(User user);
}
