<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>viewAllQuestions</title>
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
<c:forEach items="${questions}" var="question">
<tr>
<td><c:out value="${question.questionNumber}"/></td>
<td><c:out value="${question.questionTitle}"/></td>
<td><c:out value="${question.options}"/></td>
<td><c:out value="${question.difficultyLevel}"/></td>
<td><c:out value="${question.taggingTopics}"/></td>
<td><c:out value="${question.answers}"/></td>
</tr>
</c:forEach>
</table>

</body>
</html>