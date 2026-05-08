package com.phonebook.command;
import com.phonebook.entity.User;
import com.phonebook.exception.PhoneBookException;
import com.phonebook.service.UserService;
import com.phonebook.util.EmailUtil;
import com.phonebook.validator.UserValidator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class RegisterCommand implements Command {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
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

            UserService service = (UserService) req.getServletContext().getAttribute("userService");
            service.register(user, password);

            // отправка письма с подтверждением
            EmailUtil.sendConfirmationEmail(email);

            return "redirect:/login?registered=true";
        } catch (PhoneBookException e) {
            req.setAttribute("error", e.getMessage());
            return "/WEB-INF/jsp/register.jsp";
        }
    }
}