<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>Multiply</title>
    <link rel="stylesheet" href="css/style.css">
    <script src="js/validation.js"></script>
</head>
<body>
<%@ include file="menu.jsp" %>
<form action="multiply" method="post" onsubmit="return validateNumber()">
    <input type="text" name="number" id="number" placeholder="Enter number">
    <button type="submit">Multiply by 2</button>
</form>
<% if(request.getAttribute("result") != null) { %>
<h3>Result: <%= request.getAttribute("result") %></h3>
<% } %>
<% if(request.getAttribute("error") != null) { %>
<p class="error"><%= request.getAttribute("error") %></p>
<% } %>
</body>
</html>
</body>
</html>
