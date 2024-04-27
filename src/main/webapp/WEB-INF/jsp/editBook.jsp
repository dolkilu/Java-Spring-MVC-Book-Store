<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="base.jspf"%>
<%@include file="navbar.jsp"%>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
    <title>Edit Book</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
</head>
<script>
    function confirmDeleteBook() {
        return confirm('Are you sure you want to delete Book: ${book.name} ?');
    }
    function confirmDeleteImage() {
        return confirm('Are you sure you want to delete Image: ${image.name} ?');
    }
</script>
<body>
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
<main class="card plex form-signin w-100 m-auto" style="min-width: 900px;min-height: 1000px">
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


            <form:form method="POST" modelAttribute="editBook" class="form">
                <div class="form-floating mb-3">
                    <div class="form-floating">
                        <form:input type="text" path="name" class="form-control" id="name"/>
                        <form:label path="name">Book Name</form:label>
                        <form:errors path="name" cssClass="text-danger"/>
                    </div>
                </div>
                <div class="form-floating mb-3">
                    <form:input type="text" path="authors" class="form-control" id="authors"/>
                    <form:label path="authors">Authors</form:label>
                    <form:errors path="authors" cssClass="text-danger"/>
                </div>

                <div class="row mb-4">
                    <div class="col">
                        <div class="form-floating">
                            <form:input type="text" path="isbn" class="form-control" id="isbn"/>
                            <form:label path="isbn">ISBN</form:label>
                            <form:errors path="isbn" cssClass="text-danger"/>
                        </div>
                    </div>
                    <div class="col">
                        <div class="form-floating">
                            <form:input type="number" path="price" class="form-control" id="price"/>
                            <form:label path="price">Price</form:label>
                            <form:errors path="price" cssClass="text-danger"/>
                        </div>
                    </div>
                </div>

                <div class="row mb-4">
                    <div class="col">
                        <div class="form-floating">
                            <form:input type="text" path="publisher" class="form-control" id="publisher"/>
                            <form:label path="publisher">Publisher</form:label>
                            <form:errors path="publisher" cssClass="text-danger"/>
                        </div>
                    </div>
                </div>
                <div class="form-floating mb-3">
                    <form:input type="date" path="publish_date" class="form-control" id="publish_date"/>
                    <form:label path="publish_date">Publish Date</form:label>
                    <form:errors path="publish_date" cssClass="text-danger"/>
                </div>

                <div class="form-floating mb-3">
                    <form:textarea path="description" class="form-control" id="description"/>
                    <form:label path="description">Description</form:label>
                    <form:errors path="description" cssClass="text-danger"/>
                </div>

                <div class="row mb-4">
                    <div class="col">
                        <div class="form-floating">
                            <form:input type="number" path="quantity" class="form-control" id="quantity"/>
                            <form:label path="quantity">Quantity</form:label>
                            <form:errors path="quantity" cssClass="text-danger"/>
                        </div>
                    </div>
                    <div class="col">
                        Availability
                        <div class="form-floating">
                            <form:checkbox path="availability" class="form-check-input" id="availability"/>
                            <form:label path="availability"> </form:label>
                            <form:errors path="availability" cssClass="text-danger"/>
                        </div>
                    </div>
<%--                    <div class="form-floating mb-3">--%>
<%--                        <form:input type="file" path="images" class="form-control" id="images" multiple="true"/>--%>
<%--                        <form:label path="images"> </form:label>--%>
<%--                    </div>--%>
                </div>

                <button type="submit" class="btn btn-warning w-100" value="editBook">Edit Book</button>
            </form:form>
            <div class="form-floating mb-3">
            <form action="<c:url value='/books/${book.id}/uploadImages'/>" method="post" enctype="multipart/form-data">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="bookId" value="${book.id}" />
                <input type="hidden" name="redirectUrl" value="/admin/${book.id}/edit">
                <input type="file" name="images" multiple>
                <button type="submit" class="btn btn-secondary">Upload Photo</button>
            </form>
            </div>

            <form action="<c:url value='/admin/${book.id}/delete'/>" method="post">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                <input type="hidden" name="bookId" value="${book.id}" />
                <button type="submit" class="btn btn-danger" onclick="return confirmDeleteBook();">
                    <img style="width: 25px;height: 25px" src="<c:url value='/svg/trash.svg'/>" alt="delete">
                    Delete Book
                </button>
            </form>
        </div>
    </div>
    <div>
        <table class="table">

            <thead>
            <tr>
                <th>image id</th>
                <th>image name</th>
                <th>delete</th>
            </tr>
            </thead>
            <tbody>

            <c:if test="${not empty book.images}">
                <c:forEach items="${book.images}" var="image" varStatus="status">
                    <tr>
                        <td>${image.id} </td>
                        <td>${image.name}</td>
                        <td>
                            <form action="<c:url value='/admin/${book.id}/remove/${image.id}'/>" method="post">
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                <input type="hidden" name="bookId" value="${book.id}" />
                                <input type="hidden" name="imageId" value="${image.id}">
                                <button type="submit" class="btn btn-danger" onclick="return confirmDeleteImage();">
                                    <img src="<c:url value='/svg/trash.svg'/>" alt="Remove">
                                </button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </div>
</main>
</body>
<style>
    html,
    body {
        height: 100%;
    }

    .form-signin {
        max-width: 400px;
        padding: 1rem;
    }

    .form-signin .form-floating:focus-within {
        z-index: 2;
    }
</style>