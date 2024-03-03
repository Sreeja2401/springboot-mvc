package com.epam;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.epam.db.QuizLibraryOperationsDaoImpl;
import com.epam.entity.QuestionsLibrary;
import com.epam.entity.QuizLibrary;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

@ExtendWith(MockitoExtension.class)
 class QuizLibraryDaoImplTest {
    @Mock
    EntityManager entityManager;
    @Mock
    EntityTransaction transaction;
    @Mock
    TypedQuery query;
    @InjectMocks
    QuizLibraryOperationsDaoImpl quizLibraryOperationsDao;
    @Test
     void saveTest()
    {
        QuizLibrary quiz=new QuizLibrary("colleges",65, Arrays.asList(new QuestionsLibrary("vjit location", Arrays.asList("a","b","c"),"low","collage","a")
       ));
        Mockito.when(entityManager.getTransaction()).thenReturn(transaction);
        Mockito.doNothing().when(entityManager).persist(quiz);
        QuizLibrary savedQuiz=quizLibraryOperationsDao.save(quiz);
        Mockito.verify(entityManager,Mockito.times(2)).getTransaction();
        Mockito.verify(entityManager.getTransaction()).begin();
        Mockito.verify(entityManager.getTransaction()).commit();
    }
    @Test
     void viewAllQuiz()
    {
        QuizLibrary quiz1=new QuizLibrary("colleges",65, Arrays.asList(
                new QuestionsLibrary("vjit location", Arrays.asList("a","b","c"),"low","collage","a"),
                new QuestionsLibrary("cbit location?", Arrays.asList("gandipet","b","c"),"low","collage","gandipet")
                                                                            ));
        QuizLibrary quiz2=new QuizLibrary("vjit",65, Arrays.asList(
                new QuestionsLibrary("vjit location", Arrays.asList("a","b","c"),"low","collage","a")
                ));
        List<QuizLibrary> availableQuizList=new ArrayList<>(Arrays.asList(quiz1,quiz2));
        Mockito.when(entityManager.createQuery("Select t from QuizLibrary t",QuizLibrary.class)).thenReturn(query);
        Mockito.when(query.getResultList()).thenReturn(availableQuizList);
        List<QuizLibrary> retrievedQuizList= quizLibraryOperationsDao.viewAll();
        assertEquals(availableQuizList,retrievedQuizList);
    }
    @Test
    void viewQuizByTitle()
    {
    	 QuizLibrary quiz2=new QuizLibrary("vjit",65, Arrays.asList(
                 new QuestionsLibrary("vjit location", Arrays.asList("a","b","c"),"low","collage","a")
                 ));
    	Mockito.when(entityManager.createQuery("Select t from QuizLibrary t where t.title =:domain",
				QuizLibrary.class)).thenReturn(query);
    	Mockito.when(query.getSingleResult()).thenReturn(quiz2);
    	QuizLibrary retrievedQuiz= quizLibraryOperationsDao.viewByQuiztitle(null);
        assertEquals(quiz2,retrievedQuiz);
    	
    }
    @Test
     void updateTest()
    {
        QuizLibrary updatedQuiz=new QuizLibrary("college",65, Arrays.asList(
                new QuestionsLibrary("vjit location", Arrays.asList("a","b","c"),"low","collage","a")
        ));
        Mockito.when(entityManager.getTransaction()).thenReturn(transaction);
        doNothing().when(entityManager).persist(updatedQuiz);
        QuizLibrary retrievedQuiz =quizLibraryOperationsDao.update(updatedQuiz);
        assertEquals(updatedQuiz,retrievedQuiz);
    }
    @Test
     void deleteTest()
    {
        QuizLibrary quiz=new QuizLibrary("colleges",65, Arrays.asList(
                new QuestionsLibrary("vjit location", Arrays.asList("a","b","c"),"low","collage","a"),
                new QuestionsLibrary("cbit location?", Arrays.asList("gandipet","b","c"),"low","collage","gandipet")
        ));
        quiz.setId(90);
        Mockito.when(entityManager.getTransaction()).thenReturn(transaction);
        Mockito.when(entityManager.find(QuizLibrary.class,90)).thenReturn(quiz);
        doNothing().when(entityManager).remove(quiz);
        quizLibraryOperationsDao.delete(90);
        Mockito.verify(entityManager).remove(quiz);
        Mockito.verify(entityManager,times(2)).getTransaction();
        Mockito.verify(entityManager.getTransaction()).begin();
        Mockito.verify(entityManager.getTransaction()).commit();
    }
}
