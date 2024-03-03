package com.epam.sl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.customexceptions.DuplicateQuestionFoundException;
import com.epam.customexceptions.QuestionIdNotFoundException;
import com.epam.db.QuestionsLibraryOperationsDao;
import com.epam.entity.QuestionsLibrary;

@Service
public class QuestionsLibraryOperationsService {

	@Autowired
	QuestionsLibraryOperationsDao qlo;

	public int addQuestion(QuestionsLibrary q) throws DuplicateQuestionFoundException {
		if (qlo.checkForExistingTitle(q.getQuestionTitle()) == null) {
			qlo.save(q);
			return q.getQuestionNumber();
		} else {
			throw new DuplicateQuestionFoundException("question with given title already exist");
		}

	}

	public int deleteQuestion(int questionId) throws QuestionIdNotFoundException {
		if (qlo.viewById(questionId) != null) {
			qlo.delete(questionId);
			return questionId;
		}

		else {
			throw new QuestionIdNotFoundException("id not found");
		}
	}

	public List<QuestionsLibrary> viewAllQuestion() {
		return qlo.viewAll();
	}

	/*
	 * public List<Integer> viewAllQuestionIds() { return qlo.listOfQuestionIds(); }
	 */

	public QuestionsLibrary viewQuestionById(int id) throws QuestionIdNotFoundException {
		if (qlo.viewById(id) != null) {
			return qlo.viewById(id);
		}
		throw new QuestionIdNotFoundException("id not found");
	}

	public boolean updateQuestion(int id, String existingField, String updatedValue)
			throws QuestionIdNotFoundException {
		boolean result = false;
		QuestionsLibrary question = qlo.viewById(id);
		if (qlo.viewById(id) != null) {
			if (existingField.equalsIgnoreCase("questionTitle")) {
				question.setQuestionTitle(updatedValue);
			}
			if (existingField.equalsIgnoreCase("options")) {
				List<String> newOptions = new LinkedList<>(question.getOptions());
				newOptions.add(updatedValue);
				question.setOptions(newOptions);
			}
			if (existingField.equalsIgnoreCase("difficultylevel")) {
				question.setDifficultyLevel(updatedValue);
			}
			if (existingField.equalsIgnoreCase("taggingtopic")) {
				question.setTaggingTopics(updatedValue);
			}
			if (existingField.equalsIgnoreCase("answer")) {
				question.setAnswers(updatedValue);
			}
			result = true;
			qlo.update(question);
		} else {
			throw new QuestionIdNotFoundException("id not found");
		}
		return result;
	}

}
