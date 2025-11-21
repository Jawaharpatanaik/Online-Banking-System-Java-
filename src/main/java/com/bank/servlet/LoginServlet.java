package com.bank.servlet;

import com.bank.dao.UserDAO;
import com.bank.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        User u = userDAO.login(email, password);
        if (u != null) {
            if (!u.isApproved()) {
                req.setAttribute("error", "Account pending admin approval.");
                req.getRequestDispatcher("login.jsp").forward(req, resp);
                return;
            }
            HttpSession session = req.getSession();
            session.setAttribute("user", u);
            if ("ADMIN".equals(u.getRole())) {
                resp.sendRedirect("admin.jsp");
            } else {
                resp.sendRedirect("dashboard.jsp");
            }
        } else {
            req.setAttribute("error", "Invalid credentials.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
