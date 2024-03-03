package com.epam.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.entity.AdminAndUser;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@Repository
public class AdminAndUserDaoImpl implements AdminAndUserDao {

	@Autowired
	EntityManager em;

	@Override
	public AdminAndUser findMatchingUser(String userType, String userName, String password) {
		try {
			TypedQuery<AdminAndUser> query = em.createQuery(
					"SELECT u FROM AdminAndUser u WHERE u.userType = :userType AND u.userName = :userName AND u.password = :password",
					AdminAndUser.class);
			query.setParameter("userType", userType);
			query.setParameter("userName", userName);
			query.setParameter("password", password);
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public AdminAndUser saveUsers(AdminAndUser t) {
		em.getTransaction().begin();
		em.persist(t);
		em.getTransaction().commit();
		return t;
	}

	@Override
	public AdminAndUser checkExistingUserName(String userName) {
		try {
			TypedQuery<AdminAndUser> query = em.createQuery("SELECT u FROM AdminAndUser u WHERE u.userName = :userName",
					AdminAndUser.class);
			query.setParameter("userName", userName);

			return query.getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}
}
