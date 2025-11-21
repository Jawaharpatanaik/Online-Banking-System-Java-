<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="includes/header.jsp" %>
<div class="container mt-4">
  <h2>Admin Panel - Pending Approvals</h2>
  <form method="post" action="adminActions">
    <table class="table">
      <thead><tr><th>ID</th><th>Name</th><th>Email</th><th>Action</th></tr></thead>
      <tbody>
        <c:forEach var="u" items="${pending}">
          <tr>
            <td>${u.id}</td>
            <td>${u.name}</td>
            <td>${u.email}</td>
            <td>
              <button name="action" value="approve" class="btn btn-success" formaction="adminActions?userId=${u.id}">Approve</button>
              <input type="hidden" name="userId" value="${u.id}" />
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </form>
  <a href="logout" class="btn btn-secondary">Logout</a>
</div>
<%@ include file="includes/footer.jsp" %>
