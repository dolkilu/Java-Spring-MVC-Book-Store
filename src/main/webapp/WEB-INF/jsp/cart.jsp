<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="base.jspf"%>
<%@include file="navbar.jsp"%>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
    <title>Cart</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
</head>
<body>
<div class="container mt-4 mb-3">
    <div class=""></div>
    <div class="row">
        <c:choose>
        <c:when test="${empty cart}">

            <div class="empty-cart">
                <h2>Cart is empty.</h2>
            </div>
        </c:when>
        <c:otherwise>
        <c:forEach items="${books}" var="book">
            <div class="row d-flex justify-content-center">
                <div class="card mb-3 px-1 " style="max-width: 800px;  max-height: 250px">
                    <div class="row g-0">

                        <img class="img-thumbnail" style="max-height: 250px; max-width: 150px" src="<c:choose>
                                                        <c:when test="${not empty book.images}">
                                                            <c:url value='/books/${book.id}/images/${book.images[0].id}'/>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:url value='/images/noimg.jpg'/>
                                                        </c:otherwise>
                                                    </c:choose>" alt="Book Cover" >
                        <div class="col">
                            <div class="card-body">
                                <a style="text-decoration: none" href="<c:url value="/books/${book.id}"/> ">
                                    <h4 class="card-title">${book.name}</h4>
                                </a>
                                <div class="card-text justify-content-between"></div>
                                <p>${book.authors}</p>
                                <div class="d-flex justify-content-between">
                                    <p>$${book.price}</p>
                                    <h5>Book Total: $${book.price * cart[book.id]}</h5>
                                </div>
                                <div class="d-flex justify-content-end">
                                    <ul class="list-group list-group-horizontal">
                                        <li class="list-group-item">
                                            <form action="<c:url value='/cart/update'/>" method="post">
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                <input type="hidden" name="bookId" value="${book.id}" />
                                                <input type="hidden" name="action" value="minus" />
                                                <button type="submit" class="btn btn-dark">-</button>
                                            </form>
                                        </li>
                                        <li class="list-group-item">
                                            <form action="<c:url value='/cart/update'/>" method="post">
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                <input type="hidden" name="bookId" value="${book.id}" />
                                                <input type="hidden" name="action" value="set" />
                                                <label>
                                                    <input type="number" name="quantity" value="${cart[book.id]}" min="1" class="form-control" style="width: 60px; display: inline-block;" />
                                                </label>
                                                <button type="submit" class="btn btn-success btn-sm">
                                                    <img style="width: 25px; height: 25px" src="<c:url value='/svg/check.svg'/>" alt="check"/>
                                                </button>
                                            </form>
                                        </li>
                                        <li class="list-group-item">
                                            <form action="<c:url value='/cart/update'/>" method="post">
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                <input type="hidden" name="bookId" value="${book.id}" />
                                                <input type="hidden" name="action" value="add" />
                                                <button type="submit" class="btn btn-dark">+</button>
                                            </form>
                                        </li>
                                        <li class="list-group-item">
                                            <form action="<c:url value='/cart/update'/>" method="post">
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                <input type="hidden" name="bookId" value="${book.id}" />
                                                <input type="hidden" name="action" value="remove" />
                                                <button type="submit" class="btn btn-dark">
                                                    <img src="<c:url value='/svg/trash3-fill.svg'/>" alt="remove" style="width: 20px;">
                                                </button>
                                            </form>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
    <div class="container row">
        <div class="col">
            <form action="<c:url value='/cart/empty'/>" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="bookId" value="${book.id}" />
                <button type="submit" class="btn btn-danger">Empty Cart</button>
            </form>
        </div>
        <div class="col" style="text-align: right">
            <a href="<c:url value="/cart/checkout"/>">
                <button type="submit" class="btn btn-success">Check out</button>
            </a>
        </div>
        </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
<style>
    .card-title {
        white-space: normal;
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
    }
    .card-text {
        white-space: nowrap;
        overflow: hidden;
        text-overflow: ellipsis;

    }
</style>