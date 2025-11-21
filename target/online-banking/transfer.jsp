<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="includes/header.jsp" %>
<div class="container mt-4">
  <h2>Fund Transfer</h2>
  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>
  <form method="post" action="transfer">
    <div class="mb-3">
      <label>To Account Number</label>
      <input class="form-control" name="toAccount" required />
    </div>
    <div class="mb-3">
      <label>Amount</label>
      <input type="number" step="0.01" class="form-control" name="amount" required />
    </div>
    <button class="btn btn-primary">Transfer</button>
  </form>
</div>
<%@ include file="includes/footer.jsp" %>
