package com.phonebook.servlet;

import com.phonebook.entity.User;
import com.phonebook.exception.PhoneBookException;
import com.phonebook.service.UserService;
import com.phonebook.util.XssUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/editProfile")
public class EditProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/jsp/editProfile.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        User current = (User) session.getAttribute("user");

        String phone = XssUtil.sanitize(req.getParameter("phoneNumber"));
        String fullName = XssUtil.sanitize(req.getParameter("fullName"));

        try {
            User updated = new User.Builder()
                    .id(current.getId())
                    .username(current.getUsername())
                    .password(current.getPassword())
                    .email(current.getEmail())
                    .phoneNumber(phone)
                    .fullName(fullName)
                    .build();

            UserService service = (UserService) getServletContext().getAttribute("userService");
            service.updateProfile(updated);
            session.setAttribute("user", updated);
            resp.sendRedirect(req.getContextPath() + "/userList");
        } catch (PhoneBookException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/WEB-INF/jsp/editProfile.jsp").forward(req, resp);
        }
    }
}