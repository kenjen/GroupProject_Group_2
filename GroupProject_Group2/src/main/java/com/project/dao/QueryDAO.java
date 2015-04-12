package com.project.dao;

import java.util.List;

import com.project.entities.Query;

public interface QueryDAO {
	void createQueries();
	List<Query> getQueriesByUserType(int userType);
}
