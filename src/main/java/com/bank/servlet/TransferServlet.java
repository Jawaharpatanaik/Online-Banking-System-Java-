package com.bank.servlet;

import com.bank.dao.AccountDAO;
import com.bank.dao.TransactionDAO;
import com.bank.model.Account;
import com.bank.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;

@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {
    private AccountDAO accountDAO = new AccountDAO();
    private TransactionDAO txDAO = new TransactionDAO();

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession s = req.getSession(false);
        if (s == null || s.getAttribute("user") == null) {
            resp.sendRedirect("login.jsp");
            return;
        }
        User u = (User) s.getAttribute("user");
        String toAcc = req.getParameter("toAccount");
        String amountStr = req.getParameter("amount");
        BigDecimal amount = new BigDecimal(amountStr);

        Account from = accountDAO.getByUserId(u.getId());
        if (from == null) {
            req.setAttribute("error", "No account found.");
            req.getRequestDispatcher("transfer.jsp").forward(req, resp);
            return;
        }
        if (from.getBalance().compareTo(amount) < 0) {
            req.setAttribute("error", "Insufficient funds.");
            req.getRequestDispatcher("transfer.jsp").forward(req, resp);
            return;
        }

        // find target account
        // simple query by account_number
        try (java.sql.Connection conn = com.bank.util.DBConnection.getConnection()) {
            conn.setAutoCommit(false);
            java.sql.PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM accounts WHERE account_number = ?");
            ps1.setString(1, toAcc);
            java.sql.ResultSet rs = ps1.executeQuery();
            if (!rs.next()) {
                conn.rollback();
                req.setAttribute("error", "Recipient account not found.");
                req.getRequestDispatcher("transfer.jsp").forward(req, resp);
                return;
            }
            int toId = rs.getInt("id");
            java.math.BigDecimal toBal = rs.getBigDecimal("balance");
            java.math.BigDecimal newFromBal = from.getBalance().subtract(amount);
            java.math.BigDecimal newToBal = toBal.add(amount);

            java.sql.PreparedStatement updFrom = conn.prepareStatement("UPDATE accounts SET balance = ? WHERE id = ?");
            updFrom.setBigDecimal(1, newFromBal);
            updFrom.setInt(2, from.getId());
            updFrom.executeUpdate();

            java.sql.PreparedStatement updTo = conn.prepareStatement("UPDATE accounts SET balance = ? WHERE id = ?");
            updTo.setBigDecimal(1, newToBal);
            updTo.setInt(2, toId);
            updTo.executeUpdate();

            // insert transactions
            java.sql.PreparedStatement ins1 = conn.prepareStatement("INSERT INTO transactions (account_id, type, amount, description) VALUES (?, 'DEBIT', ?, ?)");
            ins1.setInt(1, from.getId());
            ins1.setBigDecimal(2, amount);
            ins1.setString(3, "Transfer to " + toAcc);
            ins1.executeUpdate();

            java.sql.PreparedStatement ins2 = conn.prepareStatement("INSERT INTO transactions (account_id, type, amount, description) VALUES (?, 'CREDIT', ?, ?)");
            ins2.setInt(1, toId);
            ins2.setBigDecimal(2, amount);
            ins2.setString(3, "Transfer from " + from.getAccountNumber());
            ins2.executeUpdate();

            conn.commit();
            req.setAttribute("msg", "Transfer successful.");
            req.getRequestDispatcher("dashboard.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Transfer failed.");
            req.getRequestDispatcher("transfer.jsp").forward(req, resp);
        }
    }
}
