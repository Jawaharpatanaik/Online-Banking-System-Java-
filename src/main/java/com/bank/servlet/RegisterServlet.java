package com.bank.servlet;

import com.bank.dao.UserDAO;
import com.bank.dao.AccountDAO;
import com.bank.model.User;
import com.bank.model.Account;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private UserDAO userDAO = new UserDAO();
    private AccountDAO accountDAO = new AccountDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        User u = new User();
        u.setName(name);
        u.setEmail(email);
        u.setPassword(password);
        u.setApproved(false); // pending approval

        boolean ok = userDAO.register(u);
        if (ok) {
            // in real app generate after approval. create a placeholder account with zero balance.
            // obtain user id by login
            req.setAttribute("msg", "Registration successful. Wait for admin approval.");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        } else {
            req.setAttribute("error", "Registration failed. Try again.");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }
}
