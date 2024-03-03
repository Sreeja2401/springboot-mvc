<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>update Question</h2>
	<form action="updatequestion" method="post">
  Enter question id:<input type="number" name="questionNumber"><br>
  Feild to be updated :<select name="existingfeild">
  <option value="questionTitle">questionTitle</option>
  <option value="options">options</option>
  <option value="difficultylevel">difficulty level</option>
  <option value="taggingtopics">tagging topics</option>
  <option value="answer">answer</option>
</select><br>
Updated value:<input type="text" name="updatedvalue"><br>
<input type="submit" value="update Question">
</form>
</body>
</html>