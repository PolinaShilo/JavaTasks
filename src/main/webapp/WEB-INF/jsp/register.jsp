<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <title>Register</title>
  <link rel="stylesheet" href="css/style.css">
  <script src="js/validation.js"></script>

  <!-- ========== AJAX КОД НАЧАЛО ========== -->
  <script>
    // Функция для AJAX-проверки username
    function checkUsername() {
      let usernameInput = document.getElementById("username");
      let username = usernameInput.value;
      let statusSpan = document.getElementById("usernameStatus");

      // Не проверяем пустое или слишком короткое имя
      if (username.length < 3) {
        statusSpan.innerHTML = "";
        statusSpan.style.color = "black";
        return;
      }

      // Асинхронный запрос к серверу
      fetch("/phonebook/api/checkUsername?username=" + encodeURIComponent(username))
              .then(response => response.json())
              .then(data => {
                console.log(data); // для отладки в консоли браузера
                if (data.exists) {
                  statusSpan.innerHTML = "❌ Username already taken!";
                  statusSpan.style.color = "red";
                  document.getElementById("registerBtn").disabled = true;
                } else {
                  statusSpan.innerHTML = "✅ Username available!";
                  statusSpan.style.color = "green";
                  document.getElementById("registerBtn").disabled = false;
                }
              })
              .catch(error => {
                console.error("AJAX error:", error);
                statusSpan.innerHTML = "⚠️ Error checking username";
                statusSpan.style.color = "orange";
              });
    }

    // Валидация перед отправкой формы
    function validateRegistrationForm() {
      let username = document.getElementById("username").value;
      let password = document.getElementById("password").value;
      let email = document.getElementById("email").value;

      if (username.length < 3) {
        alert("Username must be at least 3 characters");
        return false;
      }
      if (password.length < 8) {
        alert("Password must be at least 8 characters");
        return false;
      }
      if (!email.includes("@")) {
        alert("Enter valid email");
        return false;
      }
      return true;
    }
  </script>
  <!-- ========== AJAX КОД КОНЕЦ ========== -->

</head>
<body>
<%@ include file="menu.jsp" %>
<div class="container">
  <h1>Register</h1>

  <form action="register" method="post" onsubmit="return validateRegistrationForm()">
    <!-- Добавляем ID для полей, чтобы к ним обращаться из JavaScript -->
    <div>
      <label>Username:</label>
      <input type="text" name="username" id="username"
             placeholder="Username"
             onkeyup="checkUsername()"
             onblur="checkUsername()"
             required>
      <span id="usernameStatus"></span>  <!-- Сюда выводится статус -->
    </div>

    <div>
      <label>Password:</label>
      <input type="password" name="password" id="password"
             placeholder="Password" required>
    </div>

    <div>
      <label>Email:</label>
      <input type="email" name="email" id="email"
             placeholder="Email" required>
    </div>

    <div>
      <label>Phone:</label>
      <input type="text" name="phoneNumber" placeholder="Phone">
    </div>

    <div>
      <label>Full Name:</label>
      <input type="text" name="fullName" placeholder="Full Name">
    </div>

    <button type="submit" id="registerBtn">Register</button>
  </form>

  <% if(request.getAttribute("error") != null) { %>
  <p class="error"><%= request.getAttribute("error") %></p>
  <% } %>
</div>
</body>
</html>