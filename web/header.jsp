<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<header>
    <!-- Navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-white">
        <div class="container-fluid" style="padding-right: 25px; padding-left: 25px">
            <button
                class="navbar-toggler"
                type="button"
                data-mdb-toggle="collapse"
                data-mdb-target="#navbarExample01"
                aria-controls="navbarExample01"
                aria-expanded="false"
                aria-label="Toggle navigation"
                >
                <i class="fas fa-bars"></i>
            </button>
            <div class="collapse navbar-collapse" id="navbarExample01">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item active">
                        <a class="nav-link" aria-current="page" href="#">
                            <div class="mt-2" style="width: 200%; display: inline-block">
                                <c:choose>
                                    <c:when test="${not empty sessionScope.LOGINED_USER}">
                                        <h4 style="display: inline">
                                            <i class="fas fa-user"> ${sessionScope.LOGINED_USER.name}</i>
                                        </h4>
                                        &nbsp;&nbsp;&nbsp;
                                        <form class="col-2" action="DispatchServlet" method="GET" style="display: inline">
                                            <button onclick="return confirmLogout();" style="padding: 3px" type="submit" name="btAction" value="Logout" title="Log out" class="btn btn-danger"><i class="fas fa-sign-out-alt">Logout</i></button>
                                            <script>
                                                function confirmLogout() {
                                                    var conf = confirm('Are you sure about logging out?');
                                                    if (conf) {
                                                        return true;
                                                    }
                                                    return false;
                                                }
                                            </script>
                                        </form>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="login.jsp" method="GET">
                                            <button style="height: 25px; padding: 3px" type="submit" value="LoginPage" title="Log in" class="btn btn-danger"><i class="fas fa-sign-in-alt">Login</i></button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </div>    
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#"></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="#"></a>
                    </li>
                    <li class="nav-item float-end">

                    </li>
                </ul>
                <c:if test="${not (sessionScope.USER_ROLE eq 'ADMIN')}">
                    <a class="nav-link" href="#" >
                        <form id="viewCart" action="DispatchServlet" method="POST" class="d-inline-flex float-end" style="margin-right: 50px">
                            <input type="hidden" value="View cart" name="btAction" />
                            <input type="hidden" name="searchValue" value="${param.searchValue}"/>
                            <a onclick="document.getElementById('viewCart').submit()" style="" href="#" class="cart position-relative d-inline-flex" aria-label="View your shopping cart" title="View your shopping cart">
                                <i class="fas fa fa-shopping-cart fa-lg"></i>

                                <c:forEach items="${sessionScope.CART.items}" var="item">
                                    <c:set var="total" value="${total + item.value}"/>
                                </c:forEach>

                                <span class="cart-basket d-flex align-items-center justify-content-center">
                                    ${total + 0}
                                </span>
                            </a>
                        </form>
                    </a>
                </c:if>                        

                <div class="float-end mr-2" style="padding: 3px" >
                    <form action="DispatchServlet" method="POST">
                        <c:if test="${not empty sessionScope.LOGINED_USER}">
                            <button type="submit" name="btAction" value="View Orders" class="btn btn-primary" style="border-radius: 10px">History Orders</button>
                            <c:if test="${sessionScope.USER_ROLE eq 'ADMIN'}">
                                <input type="hidden" name="isAdmin" value="true" />
                            </c:if>
                        </c:if>
                    </form>
                </div>
            </div>
        </div>
    </nav>
    <!-- Navbar -->
    <link
        rel="stylesheet"
        href="./css/header.css"
        />
    <!-- Jumbotron -->
    <div class="p-2 text-center bg-light" style="font-family: Ephesis; font-size: 120%;">
        <h1 style="font-size: 250%; font-weight: bold; ">Milk & Tea</h1>
        <h4 class="mb-3" style="font-size: 160%; font-style: italic;">Enjoy the life</h4>
    </div>
    
    <!-- Jumbotron -->
</header>
