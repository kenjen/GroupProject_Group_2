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
@SuppressWarnings("unchecked")
public class JPAQueryDAO implements QueryDAO{
	
	@PersistenceContext
	private EntityManager em;
	
	@Override
	public void createQueries() {
		em.createNativeQuery("truncate table queries").executeUpdate();
		ArrayList<Query> list = new ArrayList<Query>();
		/*
		 * 0 = sysadin
		 * 1 = network engineer
		 * 2 = suport engineer
		 * 3 = customer rep
		 */
		list.add(new Query(1, 3, "EventId, CauseCode for IMSI"));
		list.add(new Query(2, 2, "List IMSI by Date Range"));
		list.add(new Query(3, 2, "Count failures For Model Of Phone"));
		list.add(new Query(4, 1, "Count failures and duration for each IMSI by Date"));
		list.add(new Query(5, 1, "Unique EventId/CauseCode and Count Occurrences For Model Of Phone"));
		list.add(new Query(6, 3, "Count failures for IMSI by Date"));
		list.add(new Query(7, 1, "Top 10 Market/Operator/CellId Combinations by Date"));
		list.add(new Query(8, 3, "Unique Cause Codes for IMSI"));
		list.add(new Query(9, 1, "Top 10 IMSI by Date"));
		list.add(new Query(10, 2, "IMSI for Failure Cause Class"));
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
