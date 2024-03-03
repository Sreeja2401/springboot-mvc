package com.epam.db;

import java.util.List;

import com.epam.entity.QuizLibrary;

public interface QuizLibraryOperationsDao {
	public QuizLibrary save(QuizLibrary t);
	public List<QuizLibrary> viewAll();
	public QuizLibrary update(QuizLibrary q);
	public QuizLibrary viewByQuiztitle(String domain);
	public boolean delete(int id);
}
