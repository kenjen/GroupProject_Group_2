package com.project.dao;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.project.entities.UE;
import com.project.entities.User;

@Stateless
@Local
public class JPAUserEquipmentDAO implements UserEquipmentDAO{
	
	@PersistenceContext
	private EntityManager em;

	public Collection<UE> getAllUEs() {
		Query query = em.createQuery("from UE");
		List<UE> result = query.getResultList();
		return result;
	}

}
