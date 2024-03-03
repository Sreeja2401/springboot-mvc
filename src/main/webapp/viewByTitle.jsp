<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<table border=1>
<tr>
<th>QuizNumber</th>
<th>QuizTitle</th>
<th>marks</th>
<th>questionLibrary</th>
</tr>
<tr>
<td>${quiz.id}</td>
<td>${quiz.title}</td>
<td>${quiz.marks}</td>
<td>${quiz.questionLibrary}</td>
</tr>
</table>
</body>
</html>