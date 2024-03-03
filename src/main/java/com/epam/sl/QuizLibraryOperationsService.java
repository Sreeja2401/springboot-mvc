package com.epam.sl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.customexceptions.DuplicateQuizFoundException;
import com.epam.customexceptions.QuizNotFoundException;
import com.epam.db.QuestionsLibraryOperationsDao;
import com.epam.db.QuizLibraryOperationsDao;
import com.epam.entity.QuestionsLibrary;
import com.epam.entity.QuizLibrary;

@Service
public class QuizLibraryOperationsService {
	@Autowired
	QuizLibraryOperationsDao quizLibraryOperationsImpl;
	@Autowired
	QuestionsLibraryOperationsDao questionsLibraryOperationsImpl;

	public QuizLibrary addQuizz(QuizLibrary q) throws DuplicateQuizFoundException {
		if (quizLibraryOperationsImpl.viewByQuiztitle(q.getTitle())==null) {
			quizLibraryOperationsImpl.save(q);
		} else {
			throw new DuplicateQuizFoundException("quiz with given title already exist..... ");
		}
		return q;
	}

	public List<QuizLibrary> viewQuiz() {
		return quizLibraryOperationsImpl.viewAll();
	}

	public QuizLibrary viewQuizByTitle(String domain) throws QuizNotFoundException {

		if (quizLibraryOperationsImpl.viewByQuiztitle(domain)==null) {
			throw new QuizNotFoundException("quiz does not exist with given title");
		} else {
			return quizLibraryOperationsImpl.viewByQuiztitle(domain);

		}
	}

	public boolean editQuiz(String existingTitle, String updatedTitle) throws QuizNotFoundException {

		boolean result = false;
		QuizLibrary availableQuiz = quizLibraryOperationsImpl.viewByQuiztitle(existingTitle);
		if (availableQuiz==null) {
			throw new QuizNotFoundException("quiz does not exist with given title");
		} else {
			
				if (availableQuiz.getTitle().equalsIgnoreCase(existingTitle)) {
					availableQuiz.setTitle(updatedTitle);
					result = true;
				}

				quizLibraryOperationsImpl.update(availableQuiz);
			}
			return result;
		}
	

	public boolean deleteQuiz(String title)throws QuizNotFoundException {
		boolean result = false;
		QuizLibrary availableQuiz = quizLibraryOperationsImpl.viewByQuiztitle(title);
		if (availableQuiz==null) {
			throw new QuizNotFoundException("quiz does not exist with given title");
		}else{
			
			if (availableQuiz.getTitle().equals(title)) {
				quizLibraryOperationsImpl.delete(availableQuiz.getId());
				result = true;
			}

		}
		return result;
		}
		
	public QuizLibrary createQuiz(String title,int marks,List<Integer> questionIds) throws DuplicateQuizFoundException{
   	 List<QuestionsLibrary> questions = new ArrayList<>();
   		  for(Integer questionId:questionIds) {
   		  Optional<QuestionsLibrary> question=Optional.ofNullable(questionsLibraryOperationsImpl.viewById(questionId));
   		  if(question.isPresent())
   		  {
   		 questions.add(question.get());
   		  }
   		  }
   		  QuizLibrary quiz=new QuizLibrary(title,marks,questions);
   		 return addQuizz(quiz);
}

	}

