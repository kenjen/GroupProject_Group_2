package com.project.dao;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.project.entities.Query;


@Stateless
@Local
public class JPAQueryDAO implements QueryDAO{
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void createQueries() {
		em.createNativeQuery("truncate table queries").executeUpdate();
		ArrayList<Query> list = new ArrayList<Query>();
		list.add(new Query(1, 3, "EventId, CauseCode from IMSI"));
		list.add(new Query(2, 2, "IMSI by Date Range"));
		list.add(new Query(3, 1, "Network Engineer Query"));
		list.add(new Query(4, 0, "Sys Admin Query"));
		for(Object o : list){
			em.merge(o);
		}
	}

	@Override
	public List<Query> getQueriesByUserType(int userType) {
		javax.persistence.Query query = em.createQuery("from Query q where q.permission >= " + userType);
		List<Query> result = query.getResultList();
		if(result.size()==0){
			createQueries();
			result = query.getResultList();
		}
		return result;
	}

}
