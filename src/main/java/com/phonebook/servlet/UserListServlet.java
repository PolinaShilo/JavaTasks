package com.phonebook.servlet;

import com.phonebook.entity.User;
import com.phonebook.exception.PhoneBookException;
import com.phonebook.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/userList")
public class UserListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            UserService service = (UserService) getServletContext().getAttribute("userService");

            // ========== НАЧАЛО ПАГИНАЦИИ ==========
            int page = 1;
            int size = 5; // количество записей на странице

            // Получаем номер страницы из параметра запроса
            String pageParam = req.getParameter("page");
            if (pageParam != null && !pageParam.isEmpty()) {
                try {
                    page = Integer.parseInt(pageParam);
                    if (page < 1) page = 1;
                } catch (NumberFormatException e) {
                    page = 1;
                }
            }

            // Получаем ВСЕХ пользователей
            List<User> allUsers = service.getAllUsers();
            int totalUsers = allUsers.size();

            // Вычисляем границы текущей страницы
            int fromIndex = (page - 1) * size;
            int toIndex = Math.min(fromIndex + size, totalUsers);

            // Если fromIndex выходит за пределы, показываем последнюю страницу
            if (fromIndex >= totalUsers && totalUsers > 0) {
                page = (int) Math.ceil((double) totalUsers / size);
                fromIndex = (page - 1) * size;
                toIndex = totalUsers;
            }

            // Получаем только пользователей для текущей страницы
            List<User> usersForPage = allUsers.subList(fromIndex, toIndex);

            // Передаём данные в JSP
            req.setAttribute("users", usersForPage);
            req.setAttribute("currentPage", page);
            req.setAttribute("totalPages", (int) Math.ceil((double) totalUsers / size));
            req.setAttribute("totalUsers", totalUsers);
            req.setAttribute("pageSize", size);
            // ========== КОНЕЦ ПАГИНАЦИИ ==========

        } catch (PhoneBookException e) {
            req.setAttribute("error", e.getMessage());
        }

        req.getRequestDispatcher("/WEB-INF/jsp/userList.jsp").forward(req, resp);
    }
}