package com.phonebook.servlet;

import com.phonebook.exception.PhoneBookException;
import com.phonebook.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/confirm")
public class ConfirmEmailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String email = req.getParameter("email");
        try {
            UserService service = (UserService) getServletContext().getAttribute("userService");
            service.confirmEmail(email);
            resp.sendRedirect(req.getContextPath() + "/login?confirmed=true");
        } catch (PhoneBookException e) {
            resp.getWriter().write("Confirmation failed: " + e.getMessage());
        }
    }
}