<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div style="margin: 10px 0; padding: 10px; background-color: #f0f0f0; border-radius: 5px;">
    <a href="${pageContext.request.contextPath}/hello">🏠 Home</a> |
    <a href="${pageContext.request.contextPath}/multiply">✖️ Multiply</a> |
    <a href="${pageContext.request.contextPath}/register">📝 Register</a> |

    <c:choose>
        <c:when test="${empty sessionScope.user}">
            <a href="${pageContext.request.contextPath}/login">🔐 Login</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/userList">👥 Users</a> |
            <a href="${pageContext.request.contextPath}/addUser">➕ Add</a> |
            <span>👋 Hello, ${sessionScope.user.username}!</span> |
            <a href="${pageContext.request.contextPath}/logout">🚪 Logout</a>
        </c:otherwise>
    </c:choose>
</div>
<hr>