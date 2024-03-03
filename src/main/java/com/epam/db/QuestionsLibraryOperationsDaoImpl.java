package com.epam.db;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.epam.entity.QuestionsLibrary;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Repository
public class QuestionsLibraryOperationsDaoImpl implements QuestionsLibraryOperationsDao {

	@Autowired
	EntityManager em;

	@Override
	public QuestionsLibrary save(QuestionsLibrary q) {
		em.getTransaction().begin();
		em.persist(q);
		em.getTransaction().commit();
		return q;
	}

	@Override
	public boolean delete(int questionId) {
		em.getTransaction().begin();
		em.remove(em.find(QuestionsLibrary.class, questionId));
		em.getTransaction().commit();
		return true;
	}

	@Override
	public List<QuestionsLibrary> viewAll() {
		TypedQuery<QuestionsLibrary> query = em.createQuery("Select t from QuestionsLibrary t",QuestionsLibrary.class);
		return query.getResultList();

	}

	@Override
	public QuestionsLibrary viewById(int id) {
		return em.find(QuestionsLibrary.class, id);

	}

	@Override
	public QuestionsLibrary update(QuestionsLibrary question) {
		em.getTransaction().begin();
		em.persist(question);
		em.getTransaction().commit();
		return question;
	}

	@Override
	public QuestionsLibrary checkForExistingTitle(String title) {
		try{
			TypedQuery<QuestionsLibrary> query = em
					.createQuery("Select t from QuestionsLibrary t where t.questionTitle =:title", QuestionsLibrary.class);
			query.setParameter("title", title);

			return query.getSingleResult();
		}catch(NoResultException e) {
			return null;
		}
	}
	

}
