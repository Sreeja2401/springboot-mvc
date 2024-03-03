package com.epam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.db.QuestionsLibraryOperationsDaoImpl;
import com.epam.entity.QuestionsLibrary;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

@ExtendWith(MockitoExtension.class)
 class QuestionsLibraryDaoImplTest {
    @Mock
    EntityManager entityManager;

    @Mock
    EntityTransaction transaction;
    @InjectMocks
    QuestionsLibraryOperationsDaoImpl questionsLibraryOperationsDao;
    @Mock
    TypedQuery query;
    @Test
     void saveTest()
    {
        QuestionsLibrary question=new QuestionsLibrary("vjit location", Arrays.asList("a","b","c"),"low","collage","a");
        Mockito.when(entityManager.getTransaction()).thenReturn(transaction);
        doNothing().when(entityManager).persist(question);
        QuestionsLibrary savedQuestion=questionsLibraryOperationsDao.save(question);
        assertEquals(savedQuestion,question);
        Mockito.verify(entityManager).persist(question);
        Mockito.verify(entityManager,times(2)).getTransaction();
        Mockito.verify(entityManager.getTransaction()).begin();
        Mockito.verify(entityManager.getTransaction()).commit();
    }
    @Test
     void deleteTest()
    {
        QuestionsLibrary question=new QuestionsLibrary("vjit location", Arrays.asList("a","b","c"),"low","collage","a");
        question.setQuestionNumber(1);
        Mockito.when(entityManager.getTransaction()).thenReturn(transaction);
        Mockito.when(entityManager.find(QuestionsLibrary.class,question.getQuestionNumber())).thenReturn(question);
        doNothing().when(entityManager).remove(question);
        questionsLibraryOperationsDao.delete(1);
        Mockito.verify(entityManager).remove(question);
        Mockito.verify(entityManager,times(2)).getTransaction();
        Mockito.verify(entityManager.getTransaction()).begin();
        Mockito.verify(entityManager.getTransaction()).commit();
    }
    @Test
     void viewAllTest()
    {
        QuestionsLibrary question1=new QuestionsLibrary("vjit location?", Arrays.asList("aziznagar","b","c"),"low","collage","aziznagar");
        QuestionsLibrary question2=new QuestionsLibrary("cbit location?", Arrays.asList("gandipet","b","c"),"low","collage","gandipet");
        List<QuestionsLibrary> availableQuestionList=new ArrayList<>(Arrays.asList(question1,question2));
        Mockito.when(entityManager.createQuery("Select t from QuestionsLibrary t",QuestionsLibrary.class)).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(availableQuestionList);
   	   List<QuestionsLibrary> retrievedQuestionList =questionsLibraryOperationsDao.viewAll();
   	   assertEquals(availableQuestionList,retrievedQuestionList);

    }
    @Test
     void viewByIdTest()
    {
         QuestionsLibrary question=new QuestionsLibrary("vjit location", Arrays.asList("a","b","c"),"low","collage","a");
        question.setQuestionNumber(1);
        Mockito.when(entityManager.find(QuestionsLibrary.class,question.getQuestionNumber())).thenReturn(question);
        QuestionsLibrary retrievedQuestion=questionsLibraryOperationsDao.viewById(1);
        assertEquals(question,retrievedQuestion);
    }
    @Test
     void updateTest()
    {
        QuestionsLibrary updatedQuestion=new QuestionsLibrary("cbit location", Arrays.asList("a","b","c"),"low","collage","a");
        Mockito.when(entityManager.getTransaction()).thenReturn(transaction);
        doNothing().when(entityManager).persist(updatedQuestion);
        QuestionsLibrary retrievedQuestion =questionsLibraryOperationsDao.update(updatedQuestion);
        assertEquals(updatedQuestion,retrievedQuestion);
    }
    @Test
    void checkForExistingTitleTitle()
    {
    	 QuestionsLibrary question=new QuestionsLibrary("vjit location", Arrays.asList("a","b","c"),"low","collage","a");
    	Mockito.when(entityManager.createQuery("Select t from QuestionsLibrary t where t.questionTitle =:title", QuestionsLibrary.class)
		).thenReturn(query);
    	 Mockito.when(query.getSingleResult()).thenReturn(question);
    	 QuestionsLibrary retrievedQuestion =questionsLibraryOperationsDao.checkForExistingTitle(question.getQuestionTitle());
    	 assertEquals(question,retrievedQuestion);
    }
    @Test
    void checkForNonExistingTitle()
    {
    	 QuestionsLibrary question=new QuestionsLibrary("vjit location", Arrays.asList("a","b","c"),"low","collage","a");
    	Mockito.when(entityManager.createQuery("Select t from QuestionsLibrary t where t.questionTitle =:title", QuestionsLibrary.class)
		).thenReturn(query);
    	 Mockito.when(query.getSingleResult()).thenThrow(new NoResultException());
    	 assertNull(questionsLibraryOperationsDao.checkForExistingTitle(null));
    	 }
    
}
