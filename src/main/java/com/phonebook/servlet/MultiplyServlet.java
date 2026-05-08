package com.phonebook.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebServlet("/multiply")
public class MultiplyServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MultiplyServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/multiply.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String param = req.getParameter("number");
        try {
            int num = Integer.parseInt(param);
            int result = num * 2;
            req.setAttribute("result", result);
            log.info("Multiplied {} by 2 = {}", num, result);
        } catch (NumberFormatException e) {
            req.setAttribute("error", "Invalid number");
            log.error("Invalid number: {}", param);
        }
        req.getRequestDispatcher("/WEB-INF/jsp/multiply.jsp").forward(req, resp);
    }
}