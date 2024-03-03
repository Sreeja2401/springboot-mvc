<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Create Quiz</title>
</head>
<body>
	<h1>Create Quiz</h1>
	<form method="post" action="createQuiz">
		<label for="domain">Title:</label>
		<input type="text" name="title" required>
		<br>
		<label for="questionIds">Question IDs:</label>
		<input type="text" name="questionIds"  required>
		<small>Enter comma-separated IDs (e.g. 1,2,3)</small>
		<br>
		<label for="marks">Marks:</label>
		<input type="number" name="marks"  required>
		<br>
		<input type="submit" value="Create Quiz">
	</form>
</body>
</html>
