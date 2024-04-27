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
    <table class="table">
        <thead>
        <tr>
            <th>Username</th>
            <th>Email</th>
            <th class="d-none d-xl-block">Phone</th>
            <th>First Name</th>
            <th>Last Name</th>
            <th class="d-none d-xl-block">Country</th>
            <th>Orders</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td><a href="<c:url value="/admin/userInfo/${user.user.username}"/>">${user.user.username}</a></td>
                <td>${user.email}</td>
                <td class="d-none d-xl-block">${user.phone}</td>
                <td>${user.firstname}</td>
                <td>${user.lastname}</td>
                <td class="d-none d-xl-block">${user.country}</td>
                <td><a href="<c:url value="/admin/viewOrders/user/${user.user.username}"/>">View</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <div class="d-flex" style="justify-content: center">
        <nav aria-label="page_navigation">
            <ul class="pagination">
                <li class="page-item ${currentPage == 0 ? 'disabled' : ''}">
                    <a class="page-link" href="<c:url value='/admin/manageUsers?page=${currentPage - 1}&size=${size}'/>" aria-label="Next">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>
                <c:forEach begin="0" end="${(totalPages - 1) == 0 ? (totalPages - 1) : 1}" var="pageNum">
                    <li class="page-item ${pageNum == currentPage ? 'active disabled' : ''}">
                        <a class="page-link" href="<c:url value='/admin/manageUsers?page=${pageNum}&size=${size}'/>">${pageNum + 1}</a>
                    </li>
                </c:forEach>
                <li class="page-item ${currentPage + 1 == totalPages ? 'disabled' : ''}">
                    <a class="page-link" href="<c:url value='/admin/manageUsers?page=${currentPage + 1}&size=${size}'/>" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>
