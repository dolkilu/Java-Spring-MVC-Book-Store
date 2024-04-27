<%@include file="base.jspf"%>
    <nav class="navbar navbar-expand-lg opacity-100">
    <div class="container-fluid me-lg-2">
        <a class="navbar-brand" href="<c:url value="/"/>">
            <button class="btn btn-light nav-link" style="width: 300px; font-size: 24px">
                <img style="width: 35px; height: 35px" src="<c:url value='/svg/house.svg'/>" alt="home">
               <c:out value="${pageTitle}" />
            </button>
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav ms-auto">
                <security:authorize access="isAnonymous()">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/register"/>">
                            <button title="Register" class="btn btn-light nav-link">
                                <img src="<c:url value='/svg/person-fill-add.svg'/>" alt="register">Register
                            </button>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/login"/>">
                            <button title="Login" class="btn btn-light nav-link">
                                <img src="<c:url value='/svg/door-open-fill.svg'/>" alt="login"/>Login
                            </button>
                        </a>

                    </li>
                </security:authorize>

                <security:authorize access="hasAuthority('ROLE_ADMIN')">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/admin/addNewBook"/>">
                            <button title="Add Book" class="btn btn-success nav-link">
                                <img src="<c:url value='/svg/file-earmark-plus-fill.svg'/>" alt="addBook">
                            </button>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/admin/addUser"/>">
                            <button title="Add User" class="btn btn-success nav-link">
                                <img src="<c:url value='/svg/person-fill-add.svg'/>" alt="addUser">
                            </button>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/admin/manageUsers"/>">
                            <button title="Manage Users" class="btn btn-success nav-link">
                                <img src="<c:url value='/svg/people-fill.svg'/>" alt="manage">
                            </button>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/admin/listOrders"/>">
                            <button title="View All Orders" class="btn btn-success nav-link">
                                <img src="<c:url value='/svg/clipboard-data.svg'/>" alt="orders">
                            </button>
                        </a>
                    </li>
                    <li class="nav-item" style="margin-right: 5px; margin-left: 5px">
                        <div style="border-left: 10px solid black"></div>
                    </li>
                </security:authorize>
                <security:authorize access="isAuthenticated()">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/user/changePassword"/>">
                            <button title="Change Password" class="btn btn-light nav-link">
                                <img src="<c:url value='/svg/key-fill.svg'/>" alt="pw">
                            </button>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/user/updateCustomerInfo"/>">
                            <button title="View Customer Info" class="btn btn-light nav-link">
                                <img src="<c:url value='/svg/person-fill.svg'/>" alt="user">
                            </button>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/cart/viewOrders"/>">
                            <button title="Order History" class="btn btn-light nav-link">
                                <img src="<c:url value='/svg/receipt.svg'/>" alt="Orders"/>
                            </button>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/user/bookmarks"/>">
                            <button title="Bookmarks" class="btn btn-light nav-link">
                                <img src="<c:url value='/svg/bookmark-fill.svg'/>" alt="bookmark"/>
                            </button>
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value="/cart"/>">
                            <button title="Shopping Cart" class="btn btn-light nav-link">
                                <img src="<c:url value='/svg/cart-fill.svg'/>" alt="Cart"/>
                            </button>
                        </a>
                    </li>
                    <li class="nav-item">
                        <form class="nav-link" action="<c:url value='/logout'/>" method="post">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <button title="Logout" class="btn btn-light nav-link" type="submit" >
                                <img src="<c:url value='/svg/door-closed-fill.svg'/>" alt="logout"/>
                            </button>
                        </form>
                    </li>
                </security:authorize>
            </ul>
        </div>
    </div>
</nav>
<style>
    .nav-link{
        margin-top: 5px;
    }
    .nav-item img{
        width: 30px;
        height: 30px;
    }
</style>