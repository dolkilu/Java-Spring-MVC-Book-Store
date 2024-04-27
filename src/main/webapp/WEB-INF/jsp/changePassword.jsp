<%@include file="base.jspf"%>
<%@include file="navbar.jsp"%>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
    <title>Change Password</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
</head>
<body>
<main class="form w-100 m-auto" style="height: 900px">
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
    <form:form method="POST" modelAttribute="password" class="form">
        <h1 class="h3 mb-3 fw-normal">Change Password</h1>

        <div class="form-floating mb-3">
            <form:input type="password" path="old_password" class="form-control" id="old_password"/>
            <form:label path="old_password">Current Password</form:label>
            <form:errors path="old_password" cssClass="text-danger"/>
        </div>

        <div class="form-floating mb-3">
            <form:input type="password" path="new_password" class="form-control" id="new_password"/>
            <form:label path="new_password">New Password</form:label>
            <form:errors path="new_password" cssClass="text-danger"/>
        </div>

        <div class="form-floating mb-3">
            <form:input type="password" path="confirm_password" class="form-control" id="confirm_password"/>
            <form:label path="confirm_password">Confirm Password</form:label>
            <form:errors path="confirm_password" cssClass="text-danger"/>
        </div>
        <div class="form-floating">
            <button type="submit" class="btn btn-primary w-100" value="changePassword">Change Password</button>
        </div>
    </form:form>
</main>
</body>
<style>
    .form {
        max-width: 400px;
        padding: 1rem;
    }

    /*.form-signin .form-floating:focus-within {*/
    /*    z-index: 2;*/
    /*}*/

    /*.form-signin input[type="password"] {*/
    /*    margin-bottom: 10px;*/
    /*    border-top-left-radius: 0;*/
    /*    border-top-right-radius: 0;*/
    /*}*/
</style>