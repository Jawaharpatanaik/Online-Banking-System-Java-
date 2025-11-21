package com.bank.servlet;

import com.bank.util.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

@WebServlet("/adminActions")
public class AdminServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // list unapproved users
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE approved = FALSE")) {
            ResultSet rs = ps.executeQuery();
            List<Map<String,Object>> list = new ArrayList<>();
            while (rs.next()) {
                Map<String,Object> m = new HashMap<>();
                m.put("id", rs.getInt("id"));
                m.put("name", rs.getString("name"));
                m.put("email", rs.getString("email"));
                list.add(m);
            }
            req.setAttribute("pending", list);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.getRequestDispatcher("admin.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String uid = req.getParameter("userId");
        if ("approve".equals(action)) {
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement("UPDATE users SET approved = TRUE WHERE id = ?")) {
                ps.setInt(1, Integer.parseInt(uid));
                ps.executeUpdate();
                // create account for user with a generated account number
                PreparedStatement ps2 = conn.prepareStatement("INSERT INTO accounts (user_id, account_number, balance) VALUES (?, ?, 0.00)");
                ps2.setInt(1, Integer.parseInt(uid));
                ps2.setString(2, "ACC" + System.currentTimeMillis());
                ps2.executeUpdate();
            } catch (SQLException e) { e.printStackTrace(); }
        }
        resp.sendRedirect("adminActions");
    }
}
