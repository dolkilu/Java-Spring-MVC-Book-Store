<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="base.jspf"%>
<%@include file="navbar.jsp"%>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
    <title>Checkout</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
</head>
<body>
<div class="container mt-4">
    <h2>Checkout</h2>
    <c:if test="${error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
             ${errorMessage}
                 <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <table class="table">
        <thead>
        <tr>
            <th>Title</th>
            <th>Quantity</th>
            <th>Price</th>
            <th>Total</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${books}" var="book">
            <tr>
                <td>${book.name}</td>
                <td>${cart[book.id]}</td>
                <td><fmt:formatNumber value="${book.price}" type="currency"/></td>
                <td><fmt:formatNumber value="${book.price * cart[book.id]}" type="currency"/></td>
            </tr>
        </c:forEach>
        <tr>
            <td colspan="3" style="text-align: right;"><strong>Grand Total:</strong></td>
            <td><strong><fmt:formatNumber value="${total}" type="currency"/></strong></td>
        </tr>
        </tbody>
    </table>
    <div class="text-right">
        <form action="<c:url value='/cart/placeOrder'/>" method="post">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            <button type="submit" class="btn btn-success">Place Order</button>
        </form>
    </div>
</div>
</body>
</html>