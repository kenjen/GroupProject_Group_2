package persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.project.entities.FailureClass;



public class PersistenceUtil implements Serializable 
{
	private static final long serialVersionUID = 1L;

	protected static EntityManagerFactory emf = 
			Persistence.createEntityManagerFactory("GroupProject_Group2"); 	
	
	
	public static void persist(Object entity)
	{
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(entity);
		em.getTransaction().commit();
		em.close();
	}

	public static void remove(Object entity) 
	{
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		Object mergedEntity = em.merge(entity);
		em.remove(mergedEntity);
		em.getTransaction().commit();
		em.close();
	}
	
	public static Object merge(Object entity) 
	{
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		entity = em.merge(entity);
		em.getTransaction().commit();		
		em.close();
		return entity;
	}

	public static EntityManager createEM()
	{
		return emf.createEntityManager();
	}
	
	
	public static List<FailureClass> findAllFailures()
	{
		EntityManager em = emf.createEntityManager();
		List<FailureClass> failures = (List<FailureClass>) 
				em.createNamedQuery("FailureClass.findAll").getResultList();
		em.close();
		
		return failures;
	}
	

	public static FailureClass findFailureClassByID(int failure)
	{
		EntityManager em = emf.createEntityManager();
		List<FailureClass> failures = (List<FailureClass>) 
				em.createNamedQuery("FailureClass.findByID").
				setParameter("FailureClass", failure).getResultList();
		em.close();
		
		if (failures.size() == 0)
			return null;
		else 
			return failures.get(0);
	}
	

	
}

