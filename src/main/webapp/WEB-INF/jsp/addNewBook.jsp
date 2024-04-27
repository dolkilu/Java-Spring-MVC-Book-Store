<%@include file="base.jspf"%>
<%@include file="navbar.jsp"%>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
    <title>Add book</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
</head>
<body>
<c:if test="${param.error != null}">
    <div style="text-align: center">
        <br/>
        <p>Operation failed.</p>
    </div>
</c:if>
<main class="card form-signin w-100 m-auto" style="min-width: 550px">
    <form:form method="POST" modelAttribute="book" class="form" enctype="multipart/form-data">
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
        </div>

        <div class="form-floating mb-3">
            <form:input type="file" path="images" class="form-control" id="images" multiple="true"/>
            <form:label path="images"> </form:label>
        </div>

        <button type="submit" class="btn btn-primary w-100" value="addNewBook">Add New Book</button>
    </form:form>
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
