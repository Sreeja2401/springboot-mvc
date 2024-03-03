package com.epam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.db.AdminAndUserDaoImpl;
import com.epam.entity.AdminAndUser;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ExtendWith(MockitoExtension.class)
class AdminAndUserDaoImplTest {

	@Mock
	EntityManager entityManager;
	@InjectMocks
	AdminAndUserDaoImpl adminAndUserDaoImpl;
	@Mock
	TypedQuery query;
	@Mock
	EntityTransaction transaction;

	@Test

	void findMatchingUserTest()throws NoResultException {
		AdminAndUser admin = new AdminAndUser("admin", "pinky", "123");
		Mockito.when(entityManager.createQuery(
				"SELECT u FROM AdminAndUser u WHERE u.userType = :userType AND u.userName = :userName AND u.password = :password",
				AdminAndUser.class)).thenReturn(query);
		Mockito.when(query.getSingleResult()).thenReturn(admin);
		AdminAndUser retrievedData = adminAndUserDaoImpl.findMatchingUser(admin.getUserType(), admin.getUserName(),
				admin.getPassword());
		assertEquals(admin, retrievedData);
	}
	@Test
	void findNonMatchingUserTest() throws NoResultException {
		AdminAndUser admin = new AdminAndUser("admin", "pinky", "123");
		Mockito.when(entityManager.createQuery(
				"SELECT u FROM AdminAndUser u WHERE u.userType = :userType AND u.userName = :userName AND u.password = :password",
				AdminAndUser.class)).thenReturn(query);
		Mockito.when(query.getSingleResult()).thenThrow(new NoResultException());
   	    assertNull(adminAndUserDaoImpl.findMatchingUser(admin.getUserType(), admin.getUserName(),
				admin.getPassword()));
		
	}

	@Test
	void saveUsersTest() {
		AdminAndUser user = new AdminAndUser("user", "sree", "abc");
		Mockito.when(entityManager.getTransaction()).thenReturn(transaction);
		doNothing().when(entityManager).persist(user);
		AdminAndUser savedUser = adminAndUserDaoImpl.saveUsers(user);
		assertEquals(user, savedUser);

	}

	@Test
	void checkExistingUserNameTest() {
		AdminAndUser user = new AdminAndUser("user", "sree", "abc");
		Mockito.when(entityManager.createQuery("SELECT u FROM AdminAndUser u WHERE u.userName = :userName",
				AdminAndUser.class)).thenReturn(query);
		Mockito.when(query.getSingleResult()).thenReturn(user);
		AdminAndUser savedUser = adminAndUserDaoImpl.checkExistingUserName(user.getUserName());
		assertEquals(user, savedUser);
	}

	@Test
	void checkNonExistingUserNameTest() throws NoResultException {
		AdminAndUser user = new AdminAndUser("user", "sree", "abc");
		Mockito.when(entityManager.createQuery("SELECT u FROM AdminAndUser u WHERE u.userName = :userName",
				AdminAndUser.class)).thenReturn(query);
		Mockito.when(query.getSingleResult()).thenThrow(new NoResultException());
   	    assertNull(adminAndUserDaoImpl.checkExistingUserName(user.getUserName()));
   	 
	}

}
