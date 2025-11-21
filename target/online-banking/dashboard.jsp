<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.bank.dao.AccountDAO, com.bank.model.User, com.bank.model.Account" %>
<%@ include file="includes/header.jsp" %>
<%
  User u = (User) session.getAttribute("user");
  AccountDAO ad = new AccountDAO();
  Account a = null;
  if (u != null) a = ad.getByUserId(u.getId());
%>
<div class="container mt-4">
  <h2>Dashboard</h2>
  <c:if test="${not empty msg}">
    <div class="alert alert-success">${msg}</div>
  </c:if>
  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>
  <p>User: <strong><%= (u!=null?u.getName():"Guest") %></strong></p>
  <p>Account Number: <strong><%= (a!=null?a.getAccountNumber():"N/A") %></strong></p>
  <p>Balance: <strong><%= (a!=null?a.getBalance():"0.00") %></strong></p>

  <p>
    <a href="transfer.jsp" class="btn btn-warning">Fund Transfer</a>
    <a href="transactions" class="btn btn-info">Transaction History</a>
    <a href="logout" class="btn btn-secondary">Logout</a>
  </p>
</div>
<%@ include file="includes/footer.jsp" %>
