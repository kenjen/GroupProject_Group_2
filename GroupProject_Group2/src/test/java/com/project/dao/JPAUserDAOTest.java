package com.project.dao;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.importer.ZipImporter;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.project.entities.BaseData;
import com.project.entities.User;

@RunWith(Arquillian.class)
public class JPAUserDAOTest {
	
	@Deployment
	public static WebArchive createDeployment() {
		return ShrinkWrap.create(ZipImporter.class, "test.war")
				.importFrom(new File("target/GroupProject_Group2.war"))
				.as(WebArchive.class);
	}

	@EJB
	private UserDAO userDao;

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

	private void insertTestData() throws Exception {
		tx.begin();
		em.joinTransaction();
		User user = new User();
		user.setUsername("Arquillian Test Username 1");
		user.setPassword("Arquillian Test Password 1");
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
	public void testAddUser() {
		User user = new User();
		user.setUsername("Arquillian Test Username 2");
		user.setPassword("Arquillian Test Password 2");
		user.setUserType(0);
		try {
			userDao.addUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<User> userList = (List<User>) userDao.getAllUsers();
		assertEquals(2, userList.size());
	}
	
	@Test
	public void testGetUser(){
		List<User> userList = (List<User>) userDao.getAllUsers();
		User testUser = null;
		try {
			testUser = userDao.getUser(userList.get(userList.size()-1));
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals("Arquillian Test Username 1", testUser.getUsername());
	}
	
	@Test
	public void testUpdateUser(){
		List<User> userList = (List<User>) userDao.getAllUsers();
		User user = userList.get(userList.size()-1);
		user.setUsername("Arquillian Test Username Update");
		try {
			userDao.updateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<User> updatedUserList = (List<User>) userDao.getAllUsers();
		User testUpdatedUser = updatedUserList.get(updatedUserList.size()-1);
		assertEquals("Arquillian Test Username Update", testUpdatedUser.getUsername());
	}
	
	@Test
	public void testDeleteUser(){
		List<User> userList = (List<User>) userDao.getAllUsers();
		int originalListSize = userList.size();
		User user = userList.get(userList.size()-1);
		try {
			userDao.deleteUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<User> updatedUserList = (List<User>) userDao.getAllUsers();
		assertEquals(originalListSize-1, updatedUserList.size());
	}

}
