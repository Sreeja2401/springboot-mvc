
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Add Questions</title>
</head>
<body>
	<h2> Create Questions</h2>
	<form action="addQuestions" method="post">
		Question title :<input type=text name="questionTitle"><br><br>
		options:<input type=text name="options"><br><br>
		level:<input type=text name="difficultyLevel"><br><br>
		TaggingTopics: <input type=text name=" taggingTopics"><br><br>
		Answers: <input type=text name="answers"><br><br>
		<input type="submit" value="Add Question">
	</form>
</body>
</html>
