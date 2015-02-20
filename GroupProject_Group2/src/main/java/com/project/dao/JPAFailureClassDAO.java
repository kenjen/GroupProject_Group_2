package com.project.dao;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.project.entities.FailureClass;
import com.project.entities.User;

@Stateless
@Local
public class JPAFailureClassDAO implements FailureClassDAOLocal{

	@PersistenceContext
	private EntityManager em;
	
	public Collection<FailureClass> getAllFailureClasses() {
			Query query = em.createQuery("from FailureClass");
			List<FailureClass> result = query.getResultList();
			return result;
	}


}
