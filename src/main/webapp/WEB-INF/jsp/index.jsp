<%@ page contentType="text/html;charset=UTF-8"%>
<%@include file="base.jspf"%>
<%@include file="navbar.jsp"%>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
    <title>Book Store</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
</head>
<body>
<div class="container mt-3">
    <div class="row">
        <c:if test="${success}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                    ${message}
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
            </div>
        </c:if>
        <c:choose>
            <c:when test="${fn:length(books)==0}">
                <h1>No books in the system OR database error.</h1>
            </c:when>
            <c:otherwise>
                <c:forEach items="${books}" var="book">
                    <div class="col-md-3">
                        <a href="/books/${book.id}" style="text-decoration: none">
                            <div class="card flex mb-4 shadow-sm" >
                                <div class="card-header" style="text-align:center">
                                    <img src="<c:choose>
                                            <c:when test="${not empty book.images and not empty book.images[0].id}">
                                                <c:url value='/books/${book.id}/images/${book.images[0].id}'/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:url value="/images/noimg.jpg"/>
                                            </c:otherwise>
                                          </c:choose>" alt="Book_Cover" class="img-thumbnail w-50 h-50">
                                </div>
                                <div class="card-body">
                                    <table class="table table-dark table-borderless" >
                                        <tbody>
                                        <tr><td class="book-name-cell">${book.name}</td></tr>
                                        <tr><td class="book-author-cell">${book.authors}</td></tr>
                                        </tbody>
                                    </table>
                                </div>
                                <div class="card-footer d-grid" style="height: 70px">
                                    <div class="row" style="margin-bottom: 1px">
                                        <small style="text-align: right;color:orange;">$${book.price}</small>
                                    </div>
                                    <div class="row">
                                            <%--                                        <security:authorize access="isAuthenticated()">--%>
                                        <div class="col">
                                            <c:choose>
                                                <c:when test="${bookmarkedIds.contains(book.id)}">
                                                    <form action="<c:url value='/user/bookmark/remove/${book.id}'/>" method="post">
                                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                        <input type="hidden" name="bookId" value="${book.id}" />
                                                        <input type="hidden" name="pageNum" value="${currentPage}">
                                                        <input type="hidden" name="size" value="20">
                                                        <input type="hidden" name="redirectUrl" value="/">
                                                        <button type="submit" class="btn btn-info btn-sm" >
                                                            <img style="width: 25px;height: 25px" src="<c:url value='/svg/bookmark-check.svg'/>" alt="addedBM">
                                                        </button>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <form action="<c:url value='/user/bookmark/add/${book.id}'/>" method="post">
                                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                                        <input type="hidden" name="bookId" value="${book.id}" />
                                                        <input type="hidden" name="pageNum" value="${currentPage}">
                                                        <input type="hidden" name="size" value="20">
                                                        <input type="hidden" name="redirectUrl" value="/">
                                                        <button type="submit"
                                                                class="btn btn-light btn-sm">
                                                            <img style="width: 25px;height: 25px" src="<c:url value='/svg/bookmark-plus.svg'/>" alt="addBM">
                                                        </button>
                                                    </form>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                        <div class="col" style="text-align:right;">
                                            <c:choose>
                                                <c:when test="${book.availability}">
                                                    <form action="<c:url value='/cart/add/${book.id}'/>" method="post">
                                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                                        <input type="hidden" name="bookId" value="${book.id}" />
                                                        <input type="hidden" name="pageNum" value="${currentPage}" />
                                                        <input type="hidden" name="size" value="20" />
                                                        <input type="hidden" name="redirectUrl" value="/" />
                                                        <button type="submit" class="btn btn-primary btn-sm">
                                                            <img style="width: 25px;height: 25px" src="<c:url value='/svg/cart.svg'/>" alt="Cart">
                                                        </button>
                                                    </form>
                                                </c:when>
                                                <c:otherwise>
                                                    <button class="btn btn-primary btn-sm" disabled>
                                                        <img style="width: 25px;height: 25px" src="<c:url value='/svg/cart.svg'/>" alt="Cart">
                                                    </button>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                            <%--                                        </security:authorize>--%>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </div>
                </c:forEach>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="d-flex" style="justify-content: center">
        <nav aria-label="page_navigation">
            <ul class="pagination">
                <li class="page-item ${currentPage == 0 ? 'disabled' : ''}">
                    <a class="page-link" href="<c:url value='/?page=${currentPage - 1}&size=20'/>" aria-label="Previous">
                        <span aria-hidden="true">&laquo;</span>
                    </a>
                </li>

                <c:forEach begin="0" end="${totalPages - 1}" var="pageNum">
                    <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
                        <a class="page-link" href="<c:url value='/?page=${pageNum}&size=20'/>">${pageNum + 1}</a>
                    </li>
                </c:forEach>

                <li class="page-item ${currentPage + 1 == totalPages ? 'disabled' : ''}">
                    <a class="page-link" href="<c:url value='/?page=${currentPage + 1}&size=20'/>" aria-label="Next">
                        <span aria-hidden="true">&raquo;</span>
                    </a>
                </li>
            </ul>
        </nav>
    </div>
</div>
</body>
</html>
<style>
    form{
        margin:0;
    }

    @media screen and (max-width:991px) {
        .book-name-cell {
            color: #0dcaf0 !important;
            font-weight: bold;
            height: 55px;
            white-space: normal;
            overflow: hidden;
            text-overflow: ellipsis;
            width: 134px;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
        }
        .book-author-cell {
            font-size: 14px;
            height: 50px;
            white-space: normal;
            overflow: hidden;
            text-overflow: ellipsis;
            width: 134px;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
        }
    }
    @media screen and (min-width: 992px){
        .book-name-cell {
            color: #0dcaf0 !important;
            font-weight: bold;
            height: 55px;
            white-space: normal;
            overflow: hidden;
            text-overflow: ellipsis;
            width: 194px;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
        }
        .book-author-cell {
            font-size: 14px;
            height: 50px;
            white-space: normal;
            overflow: hidden;
            text-overflow: ellipsis;
            width: 194px;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
        }
    }
    @media screen and (min-width: 1200px) {
        .book-name-cell {
            font-size: 18px;
            color: #0dcaf0 !important;
            font-weight: bold;
            height: 60px;
            white-space: normal;
            overflow: hidden;
            text-overflow: ellipsis;
            width: 239px;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
        }
        .book-author-cell {
            font-size: 16px;
            height: 55px;
            white-space: normal;
            overflow: hidden;
            text-overflow: ellipsis;
            width: 239px;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
        }
    }

    @media screen and (min-width: 1400px){
        .book-name-cell {
            font-size: 20px;
            color: #0dcaf0 !important;
            font-weight: bold;
            height: 65px;
            white-space: normal;
            overflow: hidden;
            text-overflow: ellipsis;
            width: 284px;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
        }
        .book-author-cell {
            font-size: 16px;
            height: 65px;
            white-space: normal;
            overflow: hidden;
            text-overflow: ellipsis;
            width: 284px;
            display: -webkit-box;
            -webkit-line-clamp: 2;
            -webkit-box-orient: vertical;
        }
    }
    .card-body {
        padding: 8px 10px 0;
    }
    .card-footer {
        padding-top: 4px;
    }

</style>