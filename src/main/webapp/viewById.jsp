
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border=1>
<tr>
<th>QuestionNumber</th>
<th>QuestionTitle</th>
<th>Options</th>
<th>DifficultyLevel</th>
<th>TaggingTopics</th>
<th>Answers</th>
</tr>
<tr>
<td>${question.questionNumber}</td>
<td>${question.questionTitle}</td>
<td>${question.options}</td>
<td>${question.difficultyLevel}</td>
<td>${question.taggingTopics}</td>
<td>${question.answers}</td>
</tr>
</table>
</body>
</html>