package com.phonebook.servlet;

import com.phonebook.command.Command;
import com.phonebook.command.CommandFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Command cmd = CommandFactory.get("logout");
        String view = cmd.execute(req, resp);
        resp.sendRedirect(req.getContextPath() + view.substring(9));
    }
}