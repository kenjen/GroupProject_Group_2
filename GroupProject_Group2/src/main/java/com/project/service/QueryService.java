package com.project.service;

import java.util.List;

import javax.ejb.Remote;
import javax.jws.WebService;

import com.project.entities.Query;

@WebService
@Remote
public interface QueryService {
	List<Query> getQueriesByUserType(int userType);
}
