<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="base.jspf"%>
<%@include file="navbar.jsp"%>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
    <title>Book Info</title>
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
    <div class="row">
        <div class="col-md-6">
            <div id="bookCarousel" class="carousel slide" data-bs-ride="carousel">
                <div class="carousel-inner">
                    <c:choose>
                        <c:when test="${not empty book.images}">
                            <c:forEach items="${book.images}" var="image" varStatus="status">
                                <div class="carousel-item ${status.index == 0 ? 'active' : ''}">
                                    <img src="<c:url value='/books/${book.id}/images/${image.id}'/>" class="d-block w-100" alt="Book Image ${status.index}">
                                </div>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <div class="carousel-item active">
                                <img src="<c:url value='/images/noimg.jpg'/>" class="d-block w-100" alt="Default Book Image">
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <button class="carousel-control-prev" type="button" data-bs-target="#bookCarousel" data-bs-slide="prev">
                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Previous</span>
                </button>
                <button class="carousel-control-next" type="button" data-bs-target="#bookCarousel" data-bs-slide="next">
                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                    <span class="visually-hidden">Next</span>
                </button>
            </div>
        </div>
        <div class="col-md-6">
            <ul class="list-group">
                <li class="list-group-item list-group-item-info"><h1><c:out value="${book.name}"/></h1></li>
                <li class="list-group-item"><strong>Authors: </strong><c:out value="${book.authors}"/></li>
                <li class="list-group-item"><strong>Price: </strong>$<c:out value="${book.price}"/></li>

                <li class="list-group-item"><strong>ISBN:</strong> <c:out value="${book.isbn}"/></li>
                <li class="list-group-item"><strong>Publisher:</strong> <c:out value="${book.publisher}"/></li>
                <li class="list-group-item"><strong>Publish Date:</strong> <c:out value="${book.publish_date}"/></li>
                <li class="list-group-item"><strong>Description:</strong> <c:out value="${book.description}"/></li>
                <li class="list-group-item">
                    <strong>Availability:</strong> <c:if test="${book.availability}">In Stock</c:if><c:if test="${!book.availability}">Out of Stock</c:if></li>
            <security:authorize access="hasAuthority('ROLE_ADMIN')">
                <li class="list-group-item"><strong>Quantity:</strong> ${book.quantity}</li>
            </security:authorize>
                <security:authorize access="isAuthenticated()">
                    <li class="list-group-item d-flex justify-content-between">
                        <c:choose>
                            <c:when test="${isBookmarked}">
                                <form action="<c:url value='/user/bookmark/remove/${book.id}'/>" method="post">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="bookId" value="${book.id}" />
                                    <input type="hidden" name="pageNum" value="${0}" />
                                    <input type="hidden" name="size" value="0" />
                                    <input type="hidden" name="redirectUrl" value="/books/${book.id}">
                                    <button type="submit" class="btn btn-info" >
                                        <img src="<c:url value='/svg/bookmark-check.svg'/>" alt="addedBM">
                                        Bookmarked
                                    </button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <form action="<c:url value='/user/bookmark/add/${book.id}'/>" method="post">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="bookId" value="${book.id}" />
                                    <input type="hidden" name="pageNum" value="${0}" />
                                    <input type="hidden" name="size" value="0" />
                                    <input type="hidden" name="redirectUrl" value="/books/${book.id}">
                                    <button type="submit"
                                            class="btn btn-light">
                                        <img src="<c:url value='/svg/bookmark-plus.svg'/>" alt="addBM">
                                        Bookmark
                                    </button>
                                </form>
                            </c:otherwise>
                        </c:choose>
                        <c:choose>
                            <c:when test="${book.availability}">
                                <form action="<c:url value='/cart/add/${book.id}'/>" method="post">
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="bookId" value="${book.id}" />
                                    <input type="hidden" name="pageNum" value="${0}" />
                                    <input type="hidden" name="size" value="0" />
                                    <input type="hidden" name="redirectUrl" value="/books/${book.id}">
                                    <button type="submit" class="btn btn-primary">
                                        <img style="width: 25px;height: 25px" src="<c:url value='/svg/cart.svg'/>" alt="Cart">
                                        Add to Cart
                                    </button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <button class="btn btn-danger" disabled>
                                    <img style="width: 25px;height: 25px" src="<c:url value='/svg/cart.svg'/>" alt="Cart">
                                    Out of Stock
                                </button>
                            </c:otherwise>
                        </c:choose>
                </li>
            </security:authorize>
            <security:authorize access="hasAuthority('ROLE_ADMIN')">
                <li class="list-group-item">
                    <form action="<c:url value='/books/${book.id}/uploadImages'/>" method="post" enctype="multipart/form-data">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" name="bookId" value="${book.id}" />
                        <input type="hidden" name="redirectUrl" value="/books/${book.id}">
                        <input type="file" name="images" multiple>
                    <button type="submit" class="btn btn-secondary">Upload Photo</button>
                    </form>
                </li>
                <li class="list-group-item">
                    <form action="<c:url value='/admin/${book.id}/edit'/>" method="GET">
                            <%--<input type="hidden" name="bookId" value="${book.id}" />--%>
                        <button type="submit" class="btn btn-warning btn-sm">
                            <img style="width: 25px;height: 25px" src="<c:url value='/svg/pencil-fill.svg'/>" alt="Cart">
                            Edit Book
                        </button>
                    </form>
                </li>
            </security:authorize>
            </ul>
        </div>
    </div>
</div>

<div class="container mt-4">
    <div class="comment-container">
        <h3>Comments</h3>
        <security:authorize access="isAuthenticated()">
            <form:form method="POST" modelAttribute="commentForm">
                <form:hidden path="bookId" value="${book.id}"/>

                <div class="mb-3">
                    <form:label path="comment" class="form-label">Your Comment:</form:label>
                    <form:textarea path="comment" rows="3" class="form-control"/>
                    <form:errors path="comment" cssClass="text-danger" />
                </div>

                <button type="submit" class="btn btn-success" value="addComment">Post Comment</button>
            </form:form>
        </security:authorize>

        <div class="comments">
            <c:forEach items="${comments}" var="comment">
                <div class="card mb-2">
                    <div class="card-header d-flex justify-content-between">
                        <div>
                            <h5 class="card-title">${comment.user.username}</h5>
                        </div>
                        <div>
                            <p class="card-text">${comment.date}</p>
                        </div>
                        <security:authorize access="hasAuthority('ROLE_ADMIN')">
                        <form method="POST" action="<c:url value="/admin/${book.id}/comment/${comment.id}/delete"/>">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button type="submit" class="btn btn-danger">
                                <img alt="delete" src="<c:url value="/svg/x-lg.svg"/>">
                            </button>
                        </form>
                        </security:authorize>
                    </div>
                    <div class="card-body">
                        <p class="card-text">${comment.comment}</p>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>
</body>
</html>