package com.phonebook.servlet;

import com.phonebook.exception.PhoneBookException;
import com.phonebook.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deleteUser")
public class DeleteUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));
        try {
            UserService service = (UserService) getServletContext().getAttribute("userService");
            service.deleteUser(id);
            resp.sendRedirect(req.getContextPath() + "/userList");
        } catch (PhoneBookException e) {
            resp.getWriter().write("Delete failed");
        }
    }
}