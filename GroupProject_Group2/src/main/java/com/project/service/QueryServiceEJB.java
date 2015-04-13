package com.project.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import com.project.dao.QueryDAO;
import com.project.entities.Query;

@Stateless
@Remote(QueryService.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class QueryServiceEJB implements QueryService {
	
	@EJB
	private QueryDAO queryDAO;

	@Override
	public List<Query> getQueriesByUserType(int userType) {
		return queryDAO.getQueriesByUserType(userType);
	}

}
