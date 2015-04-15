package com.project.dao;

import java.util.Collection;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.project.entities.User;

@Stateless
@Local
@SuppressWarnings("unchecked")
public class JPAUserDAO implements UserDAO{
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public Collection<User> getAllUsers() {
		Query query = em.createQuery("from User");
		List<User> result = query.getResultList();
		return result;
	}

	
	@Override
	public User getUser(User user) {
		Query q = em.createQuery("from User");
		List<User> res = q.getResultList();
		if(res.isEmpty()){
			User admin = new User("admin", "admin", 0);
			em.persist(admin);
		}
		Query query = em.createQuery("from User u where u.username = :username and u.password = :password");
		query.setParameter("username", user.getUsername());
		query.setParameter("password", user.getPassword());
		List<User> result = query.getResultList();
		
		if(result.isEmpty()){
			return new User("ERROR", "ERROR", -1);
		}
		
		return result.get(0);
	}
	
	@Override
	public User addUser(User user){
		Query query = em.createQuery("from User");
		List<User> users = query.getResultList();
		if(user.getUserType()>3){
			user.setUserType(3);
		}else if(user.getUserType()<0){
			user.setUserType(0);
		}
		if (!users.contains(user)){
			em.persist(user);
			return user;
		}
		return null;
		//return "User Not Added";
	}


	@Override
	public void updateUser(User user) {
		Query query = em.createQuery("from User");
		List<User> users = query.getResultList();
		for(User u : users){
			if(u.getId().equals(user.getId())){
				u.setPassword(user.getPassword());
				u.setUsername(user.getUsername());
				u.setUserType(user.getUserType());
				em.merge(u);
				break;
			}
		}
	}


	@Override
	public void deleteUser(User user) {
		User deletedUser = em.find(User.class, user.getId());
		em.remove(deletedUser);
		
	}


	@Override
	public User getUserById(User user) {
		Query query = em.createQuery("from User u where u.id = :id");
		query.setParameter("id", user.getId());
		List<User> result = query.getResultList();
		return result.get(0);
	}
	

}
