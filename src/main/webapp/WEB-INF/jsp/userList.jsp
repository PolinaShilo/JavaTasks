<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>User List</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <style>
    .pagination {
      margin-top: 20px;
      display: flex;
      gap: 10px;
      justify-content: center;
    }
    .pagination a, .pagination span {
      padding: 8px 12px;
      border: 1px solid #ccc;
      text-decoration: none;
      color: #333;
    }
    .pagination .current {
      background-color: #007bff;
      color: white;
      border-color: #007bff;
    }
    .pagination a:hover {
      background-color: #f0f0f0;
    }
    .info {
      text-align: center;
      margin-bottom: 10px;
      color: #666;
    }
  </style>
</head>
<body>
<%@ include file="menu.jsp" %>
<div class="container">
  <h1>Phonebook - All Users</h1>

  <c:if test="${not empty error}">
    <div class="error">${error}</div>
  </c:if>

  <div class="info">
    Showing page ${currentPage} of ${totalPages} | Total users: ${totalUsers}
  </div>

  <table border="1">
    <thead>
    <tr>
      <th>ID</th>
      <th>Username</th>
      <th>Email</th>
      <th>Phone Number</th>
      <th>Full Name</th>
      <th>Actions</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="user" items="${users}">
      <tr>
        <td>${user.id}</td>
        <td>${user.username}</td>
        <td>${user.email}</td>
        <td>${user.phoneNumber}</td>
        <td>${user.fullName}</td>
        <td>
          <a href="${pageContext.request.contextPath}/editProfile?id=${user.id}">Edit</a>
          <form action="${pageContext.request.contextPath}/deleteUser" method="post" style="display:inline">
            <input type="hidden" name="id" value="${user.id}">
            <button type="submit" onclick="return confirm('Delete user?')">Delete</button>
          </form>
        </td>
      </tr>
    </c:forEach>
    <c:if test="${empty users}">
      <tr>
        <td colspan="6" style="text-align:center">No users found</td>
      </tr>
    </c:if>
    </tbody>
  </table>

  <!-- Пагинация -->
  <div class="pagination">
    <c:if test="${currentPage > 1}">
      <a href="?page=${currentPage - 1}">« Previous</a>
    </c:if>

    <c:forEach begin="1" end="${totalPages}" var="i">
      <c:choose>
        <c:when test="${i == currentPage}">
          <span class="current">${i}</span>
        </c:when>
        <c:otherwise>
          <a href="?page=${i}">${i}</a>
        </c:otherwise>
      </c:choose>
    </c:forEach>

    <c:if test="${currentPage < totalPages}">
      <a href="?page=${currentPage + 1}">Next »</a>
    </c:if>
  </div>

  <div style="margin-top: 20px; text-align: center;">
    <a href="${pageContext.request.contextPath}/addUser">Add New User</a> |
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
  </div>
</div>
</body>
</html>