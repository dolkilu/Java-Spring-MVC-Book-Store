<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="base.jspf"%>
<%@include file="navbar.jsp"%>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
    <title>Manage Users</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
</head>
<body>
<div class="container mt-4">
    <c:if test="${success}">
        <div class="alert alert-success alert-dismissible fade show" role="alert">
                ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <c:if test="${error}">
        <div class="alert alert-danger alert-dismissible fade show" role="alert">
                ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
        </div>
    </c:if>
    <table class="table">
        <thead>
        <tr>
            <th>ID</th>
            <th>Date</th>
            <th>Username</th>
            <th>Email</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Total</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td><a href="<c:url value="/admin/viewOrders/order/${order.id}"/>">${order.id}</a></td>
                <td>${order.orderDate}</td>
                <td><a href="<c:url value="/admin/userInfo/${order.user.username}"/>">${order.user.username}</a></td>
                <td>${order.email}</td>
                <td>${order.firstname}</td>
                <td>${order.lastname}</td>
                <td>${order.total}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="d-flex" style="justify-content: center">
        <nav aria-label="page_navigation">
            <ul class="pagination">
                <li class="page-item ${currentPage == 0 ? 'disabled' : ''}">
                    <a class="page-link" href="<c:url value='/admin/listOrders?page=${currentPage - 1}&size=${size}'/>" aria-label="Next">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <c:forEach begin="0" end="${(totalPages - 1) == 0 ? (totalPages - 1) : 1}" var="pageNum">
                    <li class="page-item ${pageNum == currentPage ? 'active disabled' : ''}">
                        <a class="page-link" href="<c:url value='/admin/listOrders?page=${pageNum}&size=${size}'/>">${pageNum + 1}</a>
                    </li>
                </c:forEach>
                <li class="page-item ${currentPage + 1 == totalPages ? 'disabled' : ''}">
                    <a class="page-link" href="<c:url value='/admin/listOrders?page=${currentPage + 1}&size=${size}'/>" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>
