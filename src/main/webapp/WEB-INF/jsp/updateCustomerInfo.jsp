<%@include file="base.jspf"%>
<%@include file="navbar.jsp"%>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
    <title>Update User Info</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
</head>
<body>
<c:if test="${success}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>
<main class="card form-signin w-100 m-auto" style="min-width: 550px">
    <form:form method="POST" modelAttribute="customerInfo" class="form">

        <div class="row mb-4">
            <div class="col">
                <div data-mdb-input-init class="form-floating">
                    <form:input type="text" path="firstname" class="form-control" id="firstname"/>
                    <form:label path="firstname">First Name</form:label>
                    <form:errors path="firstname" cssClass="text-danger"/>
                </div>
            </div>
            <div class="col">
                <div data-mdb-input-init class="form-floating">
                    <form:input type="text" path="lastname" class="form-control" id="lastname"/>
                    <form:label path="lastname">Last Name</form:label>
                    <form:errors path="lastname" cssClass="text-danger"/>
                </div>
            </div>
        </div>

        <div class="row mb-4">
            <div class="col">
                <div data-mdb-input-init class="form-floating">
                    <form:input type="text" path="phone" class="form-control" id="phone"/>
                    <form:label path="phone">Phone</form:label>
                    <form:errors path="phone" cssClass="text-danger"/>
                </div>
            </div>
            <div class="col">
                <div data-mdb-input-init class="form-floating">
                    <form:input type="text" path="email" class="form-control" id="email"/>
                    <form:label path="email">Email</form:label>
                    <form:errors path="email" cssClass="text-danger"/>
                </div>
            </div>
        </div>

        <div class="row mb-4">
            <div class="col">
                <div data-mdb-input-init class="form-select">
                    <form:label path="country">Country</form:label>
                    <form:select path="country" class="form-control" id="country">
                        <form:options items="${countries}" />
                    </form:select>
                    <form:errors path="country" cssClass="text-danger"/>
                </div>
            </div>
            <div class="col">
                <div data-mdb-input-init class="form-floating">
                    <form:input type="text" path="city" class="form-control" id="city"/>
                    <form:label path="city">City</form:label>
                    <form:errors path="city" cssClass="text-danger"/>
                </div>
            </div>
        </div>

        <div class="form-floating mb-3">
            <form:input type="text" path="address1" class="form-control" id="address1"/>
            <form:label path="address1">Address line 1</form:label>
            <form:errors path="address1" cssClass="text-danger"/>
        </div>

        <div class="form-floating mb-3">
            <form:input type="text" path="address2" class="form-control" id="address2"/>
            <form:label path="address2">Address line 2</form:label>
            <form:errors path="address2" cssClass="text-danger"/>
        </div>

        <div class="form-floating mb-3">
            <form:input type="text" path="postalCode" class="form-control" id="postalCode"/>
            <form:label path="postalCode">Postal Code</form:label>
            <form:errors path="postalCode" cssClass="text-danger"/>
        </div>
        <button type="submit" class="btn btn-primary w-100" value="updateCustomerInfo">Update</button>
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