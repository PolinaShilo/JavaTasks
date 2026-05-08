package com.phonebook.command;

import com.phonebook.entity.User;
import com.phonebook.exception.PhoneBookException;
import com.phonebook.service.UserService;
import com.phonebook.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String remember = req.getParameter("remember");

        try {
            UserService service = (UserService) req.getServletContext().getAttribute("userService");
            Optional<User> opt = service.login(username, password);

            if (opt.isPresent()) {  // убрали проверку email {
                HttpSession session = req.getSession();
                session.setAttribute("user", opt.get());

                // Cookies: запомнить пользователя (задание 22)
                if ("on".equals(remember) || "true".equals(remember)) {
                    CookieUtil.set(resp, "saved_username", username, 86400);
                    CookieUtil.set(resp, "remember_me", "true", 86400);
                } else {
                    CookieUtil.delete(resp, "saved_username");
                    CookieUtil.delete(resp, "remember_me");
                }

                return "redirect:/userList";
            } else {
                req.setAttribute("error", "Invalid credentials or email not confirmed");
                return "/WEB-INF/jsp/login.jsp";
            }
        } catch (PhoneBookException e) {
            req.setAttribute("error", e.getMessage());
            return "/WEB-INF/jsp/login.jsp";
        }
    }
}
