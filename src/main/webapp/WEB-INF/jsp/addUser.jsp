<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Add User</title>
    <script src="js/validation.js"></script>
</head>
<body>
<%@ include file="menu.jsp" %>
<form action="addUser" method="post" onsubmit="return validateRegistration()">
    <input name="username"><br>
    <input type="password" name="password"><br>
    <input name="email"><br>
    <input name="phoneNumber"><br>
    <input name="fullName"><br>
    <button>Save</button>
</form>
<% if(request.getAttribute("error") != null) { %>
<p class="error"><%= request.getAttribute("error") %></p>
<% } %>
</body>
</html>