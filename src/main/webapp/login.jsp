<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jsp" %>
<div class="container mt-4">
  <h2>Login</h2>
  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>
  <form method="post" action="login">
    <div class="mb-3">
      <label>Email</label>
      <input class="form-control" name="email" required />
    </div>
    <div class="mb-3">
      <label>Password</label>
      <input type="password" class="form-control" name="password" required />
    </div>
    <button class="btn btn-primary">Login</button>
  </form>
</div>
<%@ include file="includes/footer.jsp" %>
