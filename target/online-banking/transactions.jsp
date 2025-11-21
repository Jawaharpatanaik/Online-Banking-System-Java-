<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="includes/header.jsp" %>
<div class="container mt-4">
  <h2>Transactions</h2>
  <table class="table">
    <thead><tr><th>ID</th><th>Type</th><th>Amount</th><th>Description</th><th>Date</th></tr></thead>
    <tbody>
      <c:forEach var="t" items="${transactions}">
        <tr>
          <td>${t.id}</td>
          <td>${t.type}</td>
          <td>${t.amount}</td>
          <td>${t.description}</td>
          <td>${t.createdAt}</td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
  <a href="dashboard.jsp" class="btn btn-secondary">Back</a>
</div>
<%@ include file="includes/footer.jsp" %>
