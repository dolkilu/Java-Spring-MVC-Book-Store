<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="base.jspf"%>
<%@include file="navbar.jsp"%>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
    <title>Bookmarks</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
</head>
<body>
<div class="container mt-4">
    <c:if test="${param.error != null}">
        <div style="text-align: center">
            <br/>
            <p>Operation failed.</p>
        </div>
    </c:if>
    <table class="table">
        <thead>
        <tr>
            <th>Book</th>
            <th>Authors</th>
            <th>Price</th>
            <th>Remove</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${bookmarks}" var="bookmark">
            <tr>
                <td><a href="<c:url value="/books/${bookmark.book.id}"/>">${bookmark.book.name}</a></td>
                <td>${bookmark.book.authors}</td>
                <td><fmt:formatNumber value="${bookmark.book.price}" type="currency"/></td>
                <td>
                    <form action="<c:url value='/user/bookmark/remove/${bookmark.book.id}'/>" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <input type="hidden" name="bookId" value="${bookmark.book.id}" />
                        <input type="hidden" name="redirectUrl" value="/user/bookmarks">
                        <button type="submit" class="btn btn-danger" >
                            <img src="<c:url value='/svg/trash.svg'/>" alt="Remove">
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
