<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Login</title></head>
<body>
<%@ include file="menu.jsp" %>
<form action="login" method="post">
    <input type="text" name="username" placeholder="Username"><br>
    <input type="password" name="password" placeholder="Password"><br>
    <button type="submit">Login</button>
</form>
<% if(request.getParameter("registered") != null) { %>
<p>Registration successful. Please confirm your email.</p>
<% } %>
<% if(request.getAttribute("error") != null) { %>
<p class="error"><%= request.getAttribute("error") %></p>
<% } %>
</body>
</html>