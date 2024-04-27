<%@include file="base.jspf"%>
<%@include file="navbar.jsp"%>
<!DOCTYPE html>
<html data-bs-theme="dark">
<head>
    <title>Login</title>
    <link rel="stylesheet" href="<c:url value="/css/bootstrap.min.css"/>">
    <link rel="stylesheet" href="<c:url value="/css/style.css"/>">
    <script src="<c:url value="/js/bootstrap.min.js"/>"></script>
</head>
<body>

<main class="form-signin w-100 m-auto">
    <form action="login" method="POST">
        <a href="<c:url value="/"/>" style="text-decoration: none">
        <h1 class="h3 mb-3 fw-normal">Login</h1>
        </a>

        <div class="form-floating">
            <input type="text" class="form-control" id="username" name="username" placeholder="username123" required="required">
            <label for="username">Username</label>
        </div>
        <div class="form-floating">
            <input type="password" class="form-control" id="password" name="password" placeholder="password" required="required">
            <label for="password">Password</label>
        </div>

        <div class="form-check text-start my-3">
            <input class="form-check-input" type="checkbox" value="remember-me" id="remember-me" name="remember-me">
            <label class="form-check-label" for="remember-me">
                Remember me
            </label>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn btn-primary w-100 py-2" type="submit" value="Login">Sign in</button>

        <div style="text-align: center">
            <c:if test="${param.error != null}">
                <br>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    Login failed.
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
            <c:if test="${param.logout != null}">
                <br>
                <div class="alert alert-success alert-dismissible fade show" role="alert">
                    Successfully logged out.
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
            </c:if>
        </div>
    </form>
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

    .form-signin input[type="text"] {
        margin-bottom: -1px;
        border-bottom-right-radius: 0;
        border-bottom-left-radius: 0;
    }

    .form-signin input[type="password"] {
        margin-bottom: 10px;
        border-top-left-radius: 0;
        border-top-right-radius: 0;
    }
</style>