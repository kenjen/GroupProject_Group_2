package com.project.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.extension.rest.client.ArquillianResteasyResource;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.project.entities.User;

@RunWith(Arquillian.class)
public class UsersRestTest {
	
	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction tx;

	@Before
	public void setUpPersistenceModuleForTest() throws Exception {
		clearDataFromPersistenceModule();
		insertTestData();
		beginTransaction();
	}

	private void clearDataFromPersistenceModule() throws Exception {
		tx.begin();
		em.joinTransaction();
		em.createQuery("delete from User").executeUpdate();
		tx.commit();
	}

	private void insertTestData() throws Exception, ParseException {
		User user = new User("test", "test", 0);
		tx.begin();
		em.joinTransaction();
		em.persist(user);
		tx.commit();
		em.clear();
	}

	private void beginTransaction() throws Exception {
		tx.begin();
		em.joinTransaction();
	}

	@After
	public void endTransaction() throws Exception {
		tx.commit();
	}
	
	@Test
	public void getAllUsersTest(@ArquillianResteasyResource UsersRest usersRest){
		List<User> allUsers = (List<User>) usersRest.getAllUsers();
		assertEquals(1, allUsers.size());
		assertTrue(allUsers.get(0).getUserType() == 0);
		assertEquals(allUsers.get(0).getUsername(), "test");
		assertEquals(allUsers.get(0).getPassword(), "test");
		allUsers = null;
	}
	
	@Test
	public void getUserTest(@ArquillianResteasyResource UsersRest usersRest){
		User user = new User("test", "test", 0);
		User testUser = usersRest.getUser(user);
		assertTrue(testUser.getUserType() == 0);
		assertEquals(testUser.getUsername(), "test");
		assertEquals(testUser.getPassword(), "test");
		user = null;
		testUser = null;
	}
	
	@Test
	public void getUserByIdTest(@ArquillianResteasyResource UsersRest usersRest){
		User user = new User("test", "test", 0);
		User testUserCheck = usersRest.getUser(user);
		User testUser = usersRest.getUserById(testUserCheck);
		assertTrue(testUser.getUserType() == 0);
		assertEquals(testUser.getUsername(), "test");
		assertEquals(testUser.getPassword(), "test");
		user = null;
		testUserCheck = null;
		testUser = null;
	}
	
	@Test
	public void addUserTest(@ArquillianResteasyResource UsersRest usersRest){
		User user = new User("testAdd", "testAdd", 0);
		User testUser = usersRest.addUser(user);
		assertTrue(testUser.getUserType() == 0);
		assertEquals(testUser.getUsername(), "testAdd");
		assertEquals(testUser.getPassword(), "testAdd");
		user = null;
		testUser = null;
	}
	
	@Test
	public void updateUserTest(@ArquillianResteasyResource UsersRest usersRest){
		User user = new User("test", "test", 0);
		User testUser = usersRest.getUser(user);
		testUser.setPassword("testUpdate");
		testUser.setUsername("testUpdate");
		usersRest.updateUser(testUser);
		User testUserUpdated = usersRest.getUser(testUser);
		assertTrue(testUserUpdated.getUserType() == 0);
		assertEquals(testUserUpdated.getUsername(), "testUpdate");
		assertEquals(testUserUpdated.getPassword(), "testUpdate");
		testUserUpdated.setPassword("test");
		testUserUpdated.setUsername("test");
		usersRest.updateUser(testUserUpdated);
		user = null;
		testUser = null;
		testUserUpdated = null;
	}
	
	@Test
	public void deleteUserTest(@ArquillianResteasyResource UsersRest usersRest){
		List<User> allUsers = (List<User>) usersRest.getAllUsers();
		int originalSize = allUsers.size();
		User user = new User("test", "test", 0);
		User testUser = usersRest.getUser(user);
		usersRest.deleteUser(testUser);
		List<User> newAllUsers = (List<User>) usersRest.getAllUsers();
		assertTrue(originalSize-1 == newAllUsers.size());	
		allUsers = null;
		user = null;
		testUser = null;
		newAllUsers = null;
	}
	
}
