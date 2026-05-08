package com.phonebook.servlet;

import com.google.gson.Gson;
import com.phonebook.exception.PhoneBookException;
import com.phonebook.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/api/checkUsername")
public class CheckUsernameServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String username = req.getParameter("username");
        Map<String, Object> result = new HashMap<>();

        try {
            UserService service = (UserService) getServletContext().getAttribute("userService");

            if (service == null) {
                result.put("error", "UserService not initialized");
                result.put("exists", false);
            } else {
                boolean exists = service.findByUsername(username).isPresent();
                result.put("exists", exists);
                result.put("username", username);
                result.put("available", !exists);
            }

        } catch (PhoneBookException e) {
            result.put("error", e.getMessage());
            result.put("exists", false);
        } catch (Exception e) {
            result.put("error", "Server error: " + e.getMessage());
            result.put("exists", false);
        }

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        new Gson().toJson(result, resp.getWriter());
    }
}