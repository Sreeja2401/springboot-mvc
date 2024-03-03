<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>user sign up</title>

<style>
/* CSS code goes here */

body {
  background-color: #f2f2f2;
  font-family: Arial, sans-serif;
  font-size: 16px;
}

h2 {
  text-align: center;
  color: #333333;
  margin-top: 40px;
  margin-bottom: 20px;
}

form {
  max-width: 400px;
  margin: 0 auto;
  background-color: #ffffff;
  padding: 30px;
  border-radius: 5px;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
}

.button{padding-left:30px;}

input[type=text], input[type=password] {
  width: 100%;
  padding: 12px 20px;
  margin: 8px 0;
  box-sizing: border-box;
  border: 2px solid #ccc;
  border-radius: 4px;
}

input[type=submit] {
  background-color: #4CAF50;
  color: white;
  padding: 12px 20px;
  border: none;
  border-radius: 4px;
  cursor: pointer;
}

input[type=submit]:hover {
  background-color: #45a049;
}

</style>

</head>
<body>
<h2>User Sign Up</h2>
<form  action="user" method="post">
USER NAME:<input type="text" name="userName"><br>
PASSWORD:<input type="password" name="password"><br>
<div className="button">
<input type="submit" value="SignUp">
</div>
</form>
</body>
</html> 
