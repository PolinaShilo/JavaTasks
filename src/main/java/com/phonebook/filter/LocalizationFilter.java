package com.phonebook.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class LocalizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        HttpSession session = request.getSession();

        String lang = request.getParameter("lang");
        if (lang != null) {
            session.setAttribute("lang", lang);
        }
        lang = (String) session.getAttribute("lang");
        if (lang == null) lang = "en";

        request.setAttribute("lang", lang);
        chain.doFilter(request, response);
    }
}