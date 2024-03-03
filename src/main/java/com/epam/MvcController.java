package com.epam;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.epam.customexceptions.DuplicateQuestionFoundException;
import com.epam.customexceptions.DuplicateQuizFoundException;
import com.epam.customexceptions.QuestionIdNotFoundException;
import com.epam.customexceptions.QuizNotFoundException;
import com.epam.entity.AdminAndUser;
import com.epam.entity.QuestionsLibrary;
import com.epam.entity.QuizLibrary;
import com.epam.sl.AdminAndUserValidationService;
import com.epam.sl.QuestionsLibraryOperationsService;
import com.epam.sl.QuizLibraryOperationsService;

@Controller
public class MvcController {
	@RequestMapping("/")
	public String welcome() {
		return "welcome.jsp";
	}

	@Autowired
	AdminAndUserValidationService au;
	@Autowired
	QuestionsLibraryOperationsService qlo;
	@Autowired
	QuizLibraryOperationsService quizLibraryOperations;

	@PostMapping("admin")
	public ModelAndView login(AdminAndUser admin) {
		ModelAndView modelAndView = new ModelAndView();
		admin.setUserType("admin");
		if (au.validateAdminAndUser(admin)) {
			modelAndView.setViewName("adminMenu.jsp");
		} else {
			modelAndView.setViewName("failure.jsp");
		}

		return modelAndView;
	}

	@PostMapping("user")
	public ModelAndView signUp(AdminAndUser user) {
		ModelAndView modelAndView = new ModelAndView();
		user.setUserType("user");
		if (au.userSignUp(user)) {
			modelAndView.addObject("message", "user added succesfully");
			modelAndView.setViewName("sucess.jsp");
		} else {
			modelAndView.addObject("message", "userName already exist..!! please try with another user name");
			modelAndView.setViewName("failure.jsp");
		}

		return modelAndView;
	}

	@PostMapping("userValidation")
	public ModelAndView signIn(AdminAndUser user) {
		ModelAndView modelAndView = new ModelAndView();
		user.setUserType("user");
		if (au.validateAdminAndUser(user)) {
			modelAndView.addObject("message", "user authenticated succesfully");
			modelAndView.setViewName("sucess.jsp");
		} else {
			modelAndView.setViewName("failure.jsp");
		}

		return modelAndView;
	}

	@PostMapping("addQuestions")
	public ModelAndView createQuestion(QuestionsLibrary question) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (qlo.addQuestion(question) != 0) {
				modelAndView.addObject("message", "question created  succesfully");
				modelAndView.setViewName("sucess.jsp");
			}
		} catch (DuplicateQuestionFoundException e) {
			modelAndView.addObject("exception", e);
			modelAndView.setViewName("exception.jsp");

		}
		return modelAndView;

	}

	@PostMapping("createQuiz")
	public ModelAndView createQuiz(String title, @RequestParam("questionIds") List<Integer> questionIds, int marks) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (quizLibraryOperations.createQuiz(title, marks, questionIds) != null) {
				modelAndView.addObject("message", "quiz created  succesfully");
				modelAndView.setViewName("sucess.jsp");
			}
		} catch (DuplicateQuizFoundException e) {
			modelAndView.addObject("exception", e);
			modelAndView.setViewName("exception.jsp");
		}
		return modelAndView;

	}

	@GetMapping("viewAllQuestions")
	public ModelAndView viewAllQuestions() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("viewAllQuestions.jsp");
		modelAndView.addObject("questions", qlo.viewAllQuestion());
		return modelAndView;
	}

	@PostMapping("viewQuestion")
	public ModelAndView viewQuestionById(int questionNumber) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.addObject("question", qlo.viewQuestionById(questionNumber));
			modelAndView.setViewName("viewById.jsp");
		} catch (QuestionIdNotFoundException e) {
			modelAndView.addObject("exception", e);
			modelAndView.setViewName("exception.jsp");
		}
		return modelAndView;
	}

	@GetMapping("viewAllQuiz")
	public ModelAndView viewAllQuiz() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("viewAllQuiz.jsp");
		modelAndView.addObject("quizList", quizLibraryOperations.viewQuiz());
		return modelAndView;
	}
	@PostMapping("viewQuiz")
	public ModelAndView viewQuizByTitle(String title) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			modelAndView.addObject("quiz", quizLibraryOperations.viewQuizByTitle(title));
			modelAndView.setViewName("viewByTitle.jsp");	
		} catch (QuizNotFoundException e) {
			modelAndView.addObject("exception", e);
			modelAndView.setViewName("exception.jsp");
		}
		return modelAndView;
	}

	@PostMapping("deleteQuestion")
	public ModelAndView deleteQuestion(int questionNumber) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (qlo.deleteQuestion(questionNumber) != 0) {
				modelAndView.addObject("message", "question deleted  succesfully");
				modelAndView.setViewName("sucess.jsp");
			}

		} catch (QuestionIdNotFoundException e) {
			modelAndView.addObject("exception", e);
			modelAndView.setViewName("exception.jsp");
		}
		return modelAndView;

	}

	@PostMapping("deletequiz")
	public ModelAndView deleteQuiz(String title) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (quizLibraryOperations.deleteQuiz(title)) {
				modelAndView.addObject("message", "quiz deleted  succesfully");
				modelAndView.setViewName("sucess.jsp");
			}
		} catch (QuizNotFoundException e) {
			modelAndView.addObject("exception", e);
			modelAndView.setViewName("exception.jsp");
		}
		return modelAndView;

	}

	@PostMapping("updatequestion")
	public ModelAndView updateQuestion(int questionNumber, @RequestParam("existingfeild") String existingfeild,
			@RequestParam("updatedvalue") String updatedvalue) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (qlo.updateQuestion(questionNumber, existingfeild, updatedvalue)) {
				modelAndView.addObject("message", "updated question succesfully");
				modelAndView.setViewName("sucess.jsp");
			}

		} catch (QuestionIdNotFoundException e) {
			modelAndView.addObject("exception", e);
			modelAndView.setViewName("exception.jsp");
		}
		return modelAndView;

	}

	@PostMapping("updateQuiz")
	public ModelAndView updateQuiz( @RequestParam("existingFeild") String existingFeild, @RequestParam("updatedValue") String updatedValue) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			if (quizLibraryOperations.editQuiz(existingFeild, updatedValue)) {
				modelAndView.addObject("message", "updated quiz succesfully");
				modelAndView.setViewName("sucess.jsp");
			}

		} catch (QuizNotFoundException e) {
			modelAndView.addObject("exception", e);
			modelAndView.setViewName("exception.jsp");
		}
		return modelAndView;

	}

}
