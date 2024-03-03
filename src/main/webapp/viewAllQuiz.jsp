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
<th>QuizNumber</th>
<th>QuizTitle</th>
<th>marks</th>
<th>questionLibrary</th>
</tr>
<c:forEach items="${quizList}" var="quiz">
<tr>
<td><c:out value="${quiz.id}"/></td>
<td><c:out value="${quiz.title}"/></td>
<td><c:out value="${quiz.marks}"/></td>
<td><c:out value="${quiz.questionLibrary}"/></td>
</tr>
</c:forEach>
</table>
</body>
</html>