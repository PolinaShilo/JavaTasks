<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Phonebook Application</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .menu {
            margin: 20px 0;
            padding: 10px;
            background-color: #f0f0f0;
            border-radius: 5px;
        }
        .menu a {
            margin: 0 10px;
            padding: 8px 15px;
            text-decoration: none;
            background-color: #007bff;
            color: white;
            border-radius: 3px;
        }
        .menu a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
<%@ include file="menu.jsp" %>
<div class="container">
    <h1>Hello World!</h1>
    <p>Welcome to Phonebook Application</p>

    <div class="menu">
        <a href="${pageContext.request.contextPath}/hello">🏠 Home</a>
        <a href="${pageContext.request.contextPath}/multiply">✖️ Multiply by 2</a>
        <a href="${pageContext.request.contextPath}/register">📝 Register</a>
        <a href="${pageContext.request.contextPath}/login">🔐 Login</a>
        <a href="${pageContext.request.contextPath}/userList">👥 User List</a>
        <a href="${pageContext.request.contextPath}/addUser">➕ Add User</a>
    </div>

    <div class="info">
        <h3>Available pages:</h3>
        <ul>
            <li><a href="${pageContext.request.contextPath}/multiply">Multiply by 2</a> - Enter a number and get result ×2</li>
            <li><a href="${pageContext.request.contextPath}/register">Register</a> - Create new account</li>
            <li><a href="${pageContext.request.contextPath}/login">Login</a> - Sign in to your account</li>
            <li><a href="${pageContext.request.contextPath}/userList">User List</a> - View all users (login required)</li>
            <li><a href="${pageContext.request.contextPath}/addUser">Add User</a> - Add new user to phonebook</li>
        </ul>
    </div>
</div>
</body>
</html>