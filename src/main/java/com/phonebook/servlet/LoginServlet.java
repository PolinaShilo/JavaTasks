package com.phonebook.servlet;

import com.phonebook.command.Command;
import com.phonebook.command.CommandFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Command cmd = CommandFactory.get("login");
        String view = cmd.execute(req, resp);
        if (view.startsWith("redirect:")) {
            resp.sendRedirect(req.getContextPath() + view.substring(9));
        } else {
            req.getRequestDispatcher(view).forward(req, resp);
        }
    }
}