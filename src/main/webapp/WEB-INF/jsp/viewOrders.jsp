<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="base.jspf"%>
<%@include file="navbar.jsp"%>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
    <title>Order History</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
</head>
<script>
    function confirmDelete() {
        return confirm('Are you sure you want to delete order?');
    }
</script>
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
    <div class="row">
        <c:if test="${empty orderDTOs.content}">
            <h1>No orders available.</h1>
        </c:if>
        <c:if test="${not empty orderDTOs}">
            <c:forEach items="${orderDTOs.content}" var="orderDTO">
                <div class="row-md-4 mb-4">
                    <div class="card">
                        <div class="card-header">
                            <div class="list-group-item d-flex">
                                <div class="col-10">
                                    <div class="row">
                                        <h5 class="card-title">Order ID: ${orderDTO.order.id}</h5>
                                    </div>
                                    <div class="row">
                                        <span>${orderDTO.order.orderDate}</span>
                                    </div>
                                </div>
                                <security:authorize access="hasAuthority('ROLE_ADMIN')">
                                    <div class="col" style="text-align: right">
                                        <form action="<c:url value='/admin/viewOrders/${orderDTO.order.id}/delete'/>" method="post">
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                            <input type="hidden" name="orderId" value="${orderDTO.order.id}" />
                                            <button type="submit" class="btn btn-danger" onclick="return confirmDelete();">
                                                <img style="width: 25px;height: 25px" src="<c:url value='/svg/trash.svg'/>" alt="delete">
                                            </button>
                                        </form>
                                    </div>
                                </security:authorize>
                            </div>
                        </div>
                        <div class="card-body">
                            <p class="card-text">Total: <fmt:formatNumber value="${orderDTO.order.total}" type="currency"/></p>
                            <table class="table">
                                <thead>
                                <tr>
                                    <th scope="col">Book</th>
                                    <th scope="col">Quantity</th>
                                    <th scope="col">Price</th>
                                    <th scope="col">Total</th>
                                </tr>
                                </thead>
                                <tbody>

                                <c:forEach items="${orderDTO.orderBooks}" var="ob">
                                    <tr>
                                        <th scope="row">
                                            <a href="<c:url value="/books/${ob.book.id}"/>">
                                                    ${ob.book.name}
                                            </a>
                                        </th>
                                        <td>${ob.quantity}</td>
                                        <td>${ob.book.price}</td>
                                        <td>${ob.book.price * ob.quantity}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <div class="card-footer">
                            <ul class="list-group">
                                <li class="list-group-item">Customer: ${orderDTO.order.firstname} ${orderDTO.order.lastname}</li>
                                <li class="list-group-item d-flex justify-content-between">
                                    <span>
                                        Email: ${orderDTO.order.email}
                                    </span>
                                    <span>
                                        Phone: ${orderDTO.order.phone}
                                    </span>
                                </li>
                                <li class="list-group-item">Address: ${orderDTO.order.address1}</li>
                                <c:if test="${orderDTO.order.address2 != null}">
                                    <li class="list-group-item">
                                        Address 2:${orderDTO.order.address2}
                                    </li>
                                </c:if>
                                <li class="list-group-item d-flex justify-content-between">
                                    <span>
                                        Country: ${orderDTO.order.country}
                                    </span>
                                    <span>
                                        City: ${orderDTO.order.city}
                                    </span>
                                </li>
                                <li class="list-group-item">Postal Code: ${orderDTO.order.postalCode}</li>
                                <li class="list-group-item card-title" style="text-align: right">
                                    <h6>
                                        Total: <fmt:formatNumber value="${orderDTO.order.total}" type="currency"/>
                                    </h6>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </c:if>
    </div>
    <c:if test="${not empty orderDTOs.content}">
        <c:choose>
            <c:when test="${normalView}">
                <div class="d-flex" style="justify-content: center">
                    <nav aria-label="page_navigation">
                        <ul class="pagination">
                            <li class="page-item ${currentPage == 0 ? 'disabled' : ''}">
                                <a class="page-link" href="<c:url value='/cart/viewOrders?page=${currentPage - 1}&size=${orderDTOs.size}'/>" aria-label="Next">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <c:forEach begin="0" end="${(totalPages - 1) == 0 ? (totalPages - 1) : 1}" var="pageNum">
                                <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="<c:url value='/cart/viewOrders?page=${pageNum}&size=${orderDTOs.size}'/>">${pageNum + 1}</a>
                                </li>
                            </c:forEach>
                            <li class="page-item ${currentPage + 1 == totalPages ? 'disabled' : ''}">
                                <a class="page-link" href="<c:url value='/cart/viewOrders?page=${currentPage + 1}&size=${orderDTOs.size}'/>" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>

                        </ul>
                    </nav>
                </div>
            </c:when>
            <c:when test="${adminView}">
                <div class="d-flex" style="justify-content: center">
                    <nav aria-label="page_navigation">
                        <ul class="pagination">
                            <li class="page-item ${currentPage == 0 ? 'disabled' : ''}">
                                <a class="page-link" href="<c:url value='/admin/viewOrders/user/${username}?page=${currentPage - 1}&size=${orderDTOs.size}'/>" aria-label="Next">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </li>
                            <c:forEach begin="0" end="${(totalPages - 1) == 0 ? (totalPages - 1) : 1}" var="pageNum">
                                <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                                    <a class="page-link" href="<c:url value='/admin/viewOrders/user/${username}?page=${pageNum}&size=${orderDTOs.size}'/>">${pageNum + 1}</a>
                                </li>
                            </c:forEach>
                            <li class="page-item ${currentPage + 1 == totalPages ? 'disabled' : ''}">
                                <a class="page-link" href="<c:url value='/admin/viewOrders/user/${username}?page=${currentPage + 1}&size=${orderDTOs.size}'/>" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </c:when>
        </c:choose>
    </c:if>
</div>
</body>
</html>