package com.epam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.customexceptions.DuplicateQuizFoundException;
import com.epam.customexceptions.QuizNotFoundException;
import com.epam.db.QuestionsLibraryOperationsDao;
import com.epam.db.QuizLibraryOperationsDao;
import com.epam.entity.QuestionsLibrary;
import com.epam.entity.QuizLibrary;
import com.epam.sl.QuizLibraryOperationsService;

@ExtendWith(MockitoExtension.class)
class QuizLibraryOperationsServiceTest {
	@Mock
	QuizLibraryOperationsDao quizLibraryOperationsDao;
	@Mock
	QuestionsLibraryOperationsDao questionsLibraryOperationsDao;
	@InjectMocks
	QuizLibraryOperationsService quizLibraryOperationsService;

	List<QuizLibrary> quizList;
	QuizLibrary quiz1;
	QuizLibrary quiz2;

	@BeforeEach
	public void setUp() {
		quiz1 = new QuizLibrary("colleges", 65,
				Arrays.asList(
						new QuestionsLibrary("vjit location", Arrays.asList("a", "b", "c"), "low", "collage", "a"),
						new QuestionsLibrary("cbit location?", Arrays.asList("gandipet", "b", "c"), "low", "collage",
								"gandipet")));
		quiz2 = new QuizLibrary("vjit", 65, Arrays
				.asList(new QuestionsLibrary("vjit location", Arrays.asList("a", "b", "c"), "low", "collage", "a")));
		quizList = new ArrayList<>();
		quizList.add(quiz1);
		quizList.add(quiz2);
	}

	@Test
	void addQuiz() throws DuplicateQuizFoundException {
		QuizLibrary quiz3 = new QuizLibrary("cbit", 65, Arrays
				.asList(new QuestionsLibrary("vjit location", Arrays.asList("a", "b", "c"), "low", "collage", "a")));
		Mockito.when(quizLibraryOperationsDao.viewByQuiztitle(quiz3.getTitle())).thenReturn(null);
		Mockito.when(quizLibraryOperationsDao.save(quiz3)).thenReturn(quiz3);
		QuizLibrary retrievedQuiz = quizLibraryOperationsService.addQuizz(quiz3);
		assertEquals(retrievedQuiz, quiz3);
	}

	@Test
	void addQuizWithDuplicateTitle() throws DuplicateQuizFoundException {
		Mockito.when(quizLibraryOperationsDao.viewByQuiztitle(quiz1.getTitle())).thenReturn(quiz1);
		assertThrows(DuplicateQuizFoundException.class, () -> quizLibraryOperationsService.addQuizz(quiz1));
	}

	@Test
	    void testCreateQuiz() throws DuplicateQuizFoundException {
	       String title = "Test Quiz";
	       int marks = 10;
	       List<Integer> questionIds = Arrays.asList(1, 2);
	      QuestionsLibrary question1 = new QuestionsLibrary("vjit location?", Arrays.asList("aziznagar", "b", "c"), "low", "collage",
					"aziznagar");
		  QuestionsLibrary question2 = new QuestionsLibrary("cbit location?", Arrays.asList("gandipet", "b", "c"), "low", "collage",
					"gandipet");
	       List<QuestionsLibrary> questions = Arrays.asList(question1, question2 );
	       QuizLibrary expectedQuiz = new QuizLibrary(title, marks, questions);
	       when(questionsLibraryOperationsDao.viewById(1)).thenReturn(question1);
	       when(questionsLibraryOperationsDao.viewById(2)).thenReturn(question2);
	       QuizLibrary actualQuiz = quizLibraryOperationsService.createQuiz(title, marks, questionIds);
	       assertEquals(expectedQuiz.getTitle(), actualQuiz.getTitle());
	   }


	@Test
	void viewAllQuizTest() {
		Mockito.when(quizLibraryOperationsDao.viewAll()).thenReturn(quizList);
		List<QuizLibrary> retrievedQuizList = quizLibraryOperationsService.viewQuiz();
		assertEquals(quizList, retrievedQuizList);
	}

	@Test
	void viewQuizByExistingDomainTest() throws QuizNotFoundException {
		String title = "vjit";
		Mockito.when(quizLibraryOperationsDao.viewByQuiztitle(title)).thenReturn(quiz2);
		QuizLibrary retrievedQuiz = quizLibraryOperationsService.viewQuizByTitle("vjit");
		assertNotNull(retrievedQuiz);
	}

	@Test
	void viewQuizByNotExistingDomainTest() throws QuizNotFoundException {
		String title = "xyz";
		Mockito.when(quizLibraryOperationsDao.viewByQuiztitle(title)).thenReturn(null);
		assertThrows(QuizNotFoundException.class, () -> quizLibraryOperationsService.viewQuizByTitle(title));

	}

	@Test
	void updateQuizWithExistingTitle() throws QuizNotFoundException {
		QuizLibrary quiz2Updated = new QuizLibrary("vjitLocation", 65, Arrays
				.asList(new QuestionsLibrary("vjit location", Arrays.asList("a", "b", "c"), "low", "collage", "a")));
		String title = "vjit";
		Mockito.when(quizLibraryOperationsDao.viewByQuiztitle(title)).thenReturn(quiz2);
		Mockito.when(quizLibraryOperationsDao.update(quiz2)).thenReturn(quiz2Updated);
		Boolean result = quizLibraryOperationsService.editQuiz("vjit", "vjitLocation");
		assertTrue(result);
	}

	@Test
	void updateQuizWithNonExistingTitle() throws QuizNotFoundException {

		String title = "xyz";
		String updatedTitle = "pqr";
		Mockito.when(quizLibraryOperationsDao.viewByQuiztitle(title)).thenReturn(null);
		assertThrows(QuizNotFoundException.class, () -> quizLibraryOperationsService.editQuiz(title, updatedTitle));

	}

	@Test
	void deleteQuizWithExistingTitle() throws QuizNotFoundException {
		String title = "vjit";
		quiz2.setId(1);
		Mockito.when(quizLibraryOperationsDao.viewByQuiztitle(title)).thenReturn(quiz2);
		Mockito.when(quizLibraryOperationsDao.delete(quiz2.getId())).thenReturn(true);
		boolean result = quizLibraryOperationsService.deleteQuiz("vjit");
		assertTrue(result);
	}

	@Test
	void deleteQuizWithNonExistingTitle() throws QuizNotFoundException {
		String title = "xyz";
		Mockito.when(quizLibraryOperationsDao.viewByQuiztitle(title)).thenReturn(null);
		assertThrows(QuizNotFoundException.class, () -> quizLibraryOperationsService.deleteQuiz(title));
	}

}
