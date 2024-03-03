package com.epam;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.epam.customexceptions.DuplicateQuestionFoundException;
import com.epam.customexceptions.QuestionIdNotFoundException;
import com.epam.db.QuestionsLibraryOperationsDao;
import com.epam.entity.QuestionsLibrary;
import com.epam.sl.QuestionsLibraryOperationsService;

@ExtendWith(MockitoExtension.class)
class QuestionsLibraryOperationsServiceTest {

	@Mock
	private QuestionsLibraryOperationsDao questionsLibraryOperationsDao;

	@InjectMocks
	private QuestionsLibraryOperationsService questionsLibraryOperations;
	List<QuestionsLibrary> availableQuestionList;
	QuestionsLibrary question1;
	QuestionsLibrary question2;

	@BeforeEach
	public void setData() {
		question1 = new QuestionsLibrary("vjit location?", Arrays.asList("aziznagar", "b", "c"), "low", "collage",
				"aziznagar");
		question2 = new QuestionsLibrary("cbit location?", Arrays.asList("gandipet", "b", "c"), "low", "collage",
				"gandipet");
		availableQuestionList = new ArrayList<>(Arrays.asList(question1, question2));
	}

	@Test
	void addingNewQuestionTest() throws DuplicateQuestionFoundException {
		question1.setQuestionNumber(1);
		when(questionsLibraryOperationsDao.checkForExistingTitle(question1.getQuestionTitle())).thenReturn(null);
		when(questionsLibraryOperationsDao.save(question1)).thenReturn(question1);
		int savedQuestionNumber = questionsLibraryOperations.addQuestion(question1);
		verify(questionsLibraryOperationsDao).save(question1);
		assertEquals(savedQuestionNumber, 1);
	}

	@Test
	void addingQuestionWithExistingTitle() {
		QuestionsLibrary question3 = new QuestionsLibrary("vjit location?", Arrays.asList("a", "b", "c"), "low",
				"collage", "a");
		when(questionsLibraryOperationsDao.checkForExistingTitle(question3.getQuestionTitle())).thenReturn(question1);
		assertThrows(DuplicateQuestionFoundException.class, () -> questionsLibraryOperations.addQuestion(question3));

	}

	@Test
	void viewQuestionByExistingIdTest() throws QuestionIdNotFoundException {
		QuestionsLibrary question = new QuestionsLibrary("vjit location", Arrays.asList("a", "b", "c"), "low",
				"collage", "a");
		question.setQuestionNumber(1);
		when(questionsLibraryOperationsDao.viewById(1)).thenReturn(question);
		QuestionsLibrary retrievedQuestion = questionsLibraryOperations.viewQuestionById(1);
		assertNotNull(retrievedQuestion);
		assertEquals(question, retrievedQuestion);
	}

	@Test
	void viewQuestionByNotExistingIdTest() throws QuestionIdNotFoundException {
		QuestionsLibrary question = new QuestionsLibrary();
		question.setQuestionNumber(1);
		int id=3;
		when(questionsLibraryOperationsDao.viewById(id)).thenReturn(null);
		assertThrows(QuestionIdNotFoundException.class, () -> questionsLibraryOperations.viewQuestionById(id));
	}

	@Test
    void viewAllQuestionsTest()  {
        when(questionsLibraryOperationsDao.viewAll()).thenReturn(availableQuestionList);
        List<QuestionsLibrary> retrievedQuestionList = questionsLibraryOperations.viewAllQuestion();
        assertEquals(availableQuestionList,retrievedQuestionList);
    }

	@Test
	void viewAllWhenQuestionListIsEmptyTest() {
		List<QuestionsLibrary> expected = new ArrayList<>();
		when(questionsLibraryOperationsDao.viewAll()).thenReturn(expected);
		List<QuestionsLibrary> result = questionsLibraryOperations.viewAllQuestion();
		assertEquals(expected, result);
		verify(questionsLibraryOperationsDao).viewAll();
	}

	@Test
	void updateQuestionTitleTest() throws QuestionIdNotFoundException {
		int id = 1;
		String existingField = "questionTitle";
		String updatedValue = "What is Python?";
		QuestionsLibrary question = new QuestionsLibrary("What is Java?", Arrays.asList("a", "b"), "Easy", "Java",
				"Java is a programming language.");
		when(questionsLibraryOperationsDao.viewById(id)).thenReturn(question);
		boolean result = questionsLibraryOperations.updateQuestion(id, existingField, updatedValue);
		assertTrue(result);
		assertEquals(updatedValue, question.getQuestionTitle());
		verify(questionsLibraryOperationsDao).update(question);
	}

	

	@Test
	void updateQuestionAnswerTest() throws QuestionIdNotFoundException {
		int id = 1;
		String existingField = "answer";
		String updatedValue = "java is advanced";
		QuestionsLibrary question = new QuestionsLibrary("What is Java?", Arrays.asList("a", "b"), "Easy", "Java",
				"Java is a programming language.");
		when(questionsLibraryOperationsDao.viewById(id)).thenReturn(question);
		boolean result = questionsLibraryOperations.updateQuestion(id, existingField, updatedValue);
		assertTrue(result);
		assertEquals(updatedValue, question.getAnswers());
		verify(questionsLibraryOperationsDao).update(question);
	}

	

	@Test
	void updateQuestiontaggingtopicTest() throws QuestionIdNotFoundException {
		int id = 1;
		String existingField = "taggingtopic";
		String updatedValue = "advjava";
		QuestionsLibrary question = new QuestionsLibrary("What is Java?", Arrays.asList("a", "b"), "Easy", "Java",
				"Java is a programming language.");
		when(questionsLibraryOperationsDao.viewById(id)).thenReturn(question);
		boolean result = questionsLibraryOperations.updateQuestion(id, existingField, updatedValue);
		assertEquals(updatedValue, question.getTaggingTopics());
		verify(questionsLibraryOperationsDao).update(question);
	}




	@Test
	void updateQuestiondifficultylevelTest() throws QuestionIdNotFoundException {
		int id = 1;
		String existingField = "difficultylevel";
		String updatedValue = "hard";
		QuestionsLibrary question = new QuestionsLibrary("What is Java?", Arrays.asList("a", "b"), "Easy", "Java",
				"Java is a programming language.");
		when(questionsLibraryOperationsDao.viewById(id)).thenReturn(question);
		boolean result = questionsLibraryOperations.updateQuestion(id, existingField, updatedValue);
		assertTrue(result);
		assertEquals(updatedValue, question.getDifficultyLevel());
		verify(questionsLibraryOperationsDao).update(question);
	}

	

	@Test
	void updateQuestionOptions() throws QuestionIdNotFoundException {
		int id = 1;
		String existingField = "options";
		String updatedValue = "c";
		QuestionsLibrary question = new QuestionsLibrary("What is Java?", Arrays.asList("a", "b"), "Easy", "Java",
				"Java is a programming language.");
		when(questionsLibraryOperationsDao.viewById(id)).thenReturn(question);
		boolean result = questionsLibraryOperations.updateQuestion(id, existingField, updatedValue);
		assertTrue(result);
		verify(questionsLibraryOperationsDao).update(question);
	}
	@Test
	void updateQuestionWithNotExistingIdTest()throws QuestionIdNotFoundException {
		int id = 1;
		String existingField = "options";
		String updatedValue = "c";
		when(questionsLibraryOperationsDao.viewById(id)).thenReturn(null);
		assertThrows(QuestionIdNotFoundException.class, () -> questionsLibraryOperations.updateQuestion(id, existingField, updatedValue));
	}

	@Test
	void deleteQuestionWithExistingIdTest() throws QuestionIdNotFoundException {
		QuestionsLibrary question = new QuestionsLibrary("What is Java?", Arrays.asList("a", "b"), "Easy", "Java",
				"Java is a programming language.");
		question.setQuestionNumber(1);
		when(questionsLibraryOperationsDao.viewById(1)).thenReturn(question);
		when(questionsLibraryOperationsDao.delete(1)).thenReturn(true);
		questionsLibraryOperations.deleteQuestion(1);
		verify(questionsLibraryOperationsDao).delete(1);
	}

	@Test
	void deleteQuestionWithNotExistingIdTest() {
		int questionId = 23;
		when(questionsLibraryOperationsDao.viewById(questionId)).thenReturn(null);
		assertThrows(QuestionIdNotFoundException.class, () -> questionsLibraryOperations.deleteQuestion(questionId));
	}

}
