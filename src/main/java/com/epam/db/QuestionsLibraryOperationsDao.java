package com.epam.db;

import java.util.List;

import com.epam.entity.QuestionsLibrary;

public interface QuestionsLibraryOperationsDao {
	QuestionsLibrary save(QuestionsLibrary t);
	boolean delete(int questionId);
	List<QuestionsLibrary> viewAll();
	QuestionsLibrary viewById(int id);
	QuestionsLibrary update(QuestionsLibrary question);
	QuestionsLibrary checkForExistingTitle(String title);
}
