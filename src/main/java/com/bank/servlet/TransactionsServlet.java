package com.bank.servlet;

import com.bank.dao.AccountDAO;
import com.bank.dao.TransactionDAO;
import com.bank.model.Transaction;
import com.bank.model.Account;
import com.bank.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/transactions")
public class TransactionsServlet extends HttpServlet {
    private AccountDAO accountDAO = new AccountDAO();
    private TransactionDAO txDAO = new TransactionDAO();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("user") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        User u = (User) s.getAttribute("user");
        Account a = accountDAO.getByUserId(u.getId());
        if (a != null) {
            List<Transaction> list = txDAO.listByAccount(a.getId());
            req.setAttribute("transactions", list);
        }
        req.getRequestDispatcher("transactions.jsp").forward(req, resp);
    }
}
