package com.epam.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class QuizLibrary {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String title;
	int marks;
	@ManyToMany(targetEntity = QuestionsLibrary.class)
	List<QuestionsLibrary> questionLibrary;
	public QuizLibrary() {
		super();
	}
	public QuizLibrary(String title, int marks, List<QuestionsLibrary> questionLibrary) {
		super();
		this.title = title;
		this.marks = marks;
		this.questionLibrary = questionLibrary;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getMarks() {
		return marks;
	}
	public void setMarks(int marks) {
		this.marks = marks;
	}
	public List<QuestionsLibrary> getQuestionLibrary() {
		return questionLibrary;
	}
	public void setQuestionLibrary(List<QuestionsLibrary> questionLibrary) {
		this.questionLibrary = questionLibrary;
	}
	@Override
	public String toString() {
		return "QuizLibrary [id=" + id + ", Title=" + title + ", marks=" + marks + ", questionLibrary="
				+ questionLibrary + "]";
	}	
	
}
	



