package com.phonebook.servlet;

import com.phonebook.entity.User;
import com.phonebook.exception.PhoneBookException;
import com.phonebook.service.UserService;
import com.phonebook.validator.UserValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/addUser")
public class AddUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/addUser.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String phone = req.getParameter("phoneNumber");
        String fullName = req.getParameter("fullName");

        try {
            UserValidator.validateUsername(username);
            UserValidator.validatePassword(password);
            UserValidator.validateEmail(email);

            User user = new User.Builder()
                    .username(username)
                    .email(email)
                    .phoneNumber(phone)
                    .fullName(fullName)
                    .build();

            UserService service = (UserService) getServletContext().getAttribute("userService");
            service.register(user, password);

            resp.sendRedirect(req.getContextPath() + "/userList");
        } catch (PhoneBookException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/addUser.jsp").forward(req, resp);
        }
    }
}