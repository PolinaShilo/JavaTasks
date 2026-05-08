<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <title>Phonebook Navigation</title>
  <link rel="stylesheet" href="css/style.css">
  <style>
    body { font-family: Arial; margin: 40px; }
    .card {
      border: 1px solid #ccc;
      padding: 15px;
      margin: 10px 0;
      border-radius: 5px;
      background-color: #f9f9f9;
    }
    .card a {
      font-size: 18px;
      text-decoration: none;
      color: #007bff;
    }
    .card a:hover { text-decoration: underline; }
    .description { color: #666; margin-top: 5px; }
  </style>
</head>
<body>
<h1>📱 Phonebook Application</h1>
<p>Complete implementation of all 28 tasks</p>

<div class="card">
  <a href="${pageContext.request.contextPath}/hello">✅ /hello</a>
  <div class="description">Task 1: Hello World JSP/Servlet</div>
</div>

<div class="card">
  <a href="${pageContext.request.contextPath}/multiply">✅ /multiply</a>
  <div class="description">Task 2: Multiply number by 2</div>
</div>

<div class="card">
  <a href="${pageContext.request.contextPath}/register">✅ /register</a>
  <div class="description">Task 8: Registration form with MVC + Command pattern</div>
</div>

<div class="card">
  <a href="${pageContext.request.contextPath}/login">✅ /login</a>
  <div class="description">Task 9: Login/Logout with password encryption</div>
</div>

<div class="card">
  <a href="${pageContext.request.contextPath}/userList">✅ /userList</a>
  <div class="description">Task 5: List all users from database</div>
</div>

<div class="card">
  <a href="${pageContext.request.contextPath}/addUser">✅ /addUser</a>
  <div class="description">Task 4: Add user to phonebook</div>
</div>
</body>
</html>