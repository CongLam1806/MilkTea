<%-- 
    Document   : view_orders
    Created on : Jul 7, 2022, 10:31:13 PM
    Author     : netbeans
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.css" type="text/css" media="all" />
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js" type="text/javascript"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js" type="text/javascript"></script>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"  crossorigin="anonymous"></script>

        <script src="./Price-Range-Slider-jQuery-UI/price_range_script.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="./Price-Range-Slider-jQuery-UI/price_range_style.css"/>
        <style>
            body { min-height: 100vh; font-family: 'Roboto'; background-color:#fafafa}

            a {
                font-size: 1.1rem;
                color: #343a40;
            }

            a.cart:hover {
                text-decoration: none;
                color: #d60e96;
            }

            a.cart .cart-basket {
                font-size: .6rem;
                position: absolute;
                top: -6px;
                right: -5px;
                width: 15px;
                height: 15px;
                color: #fff;
                background-color: #418deb;
                border-radius: 50%;
            }
        </style>
    </head>
    <body>
        <%@include file="header.jsp" %>

        <c:set var="orders" value ="${requestScope.ORDERS}" />

        <c:if test="${not empty requestScope.NOTIFICATION}">
            <div id="notification" class="container alert alert-${requestScope.NOTIFICATION.result ? 'success' : 'danger'} alert-dismissible fade show" role="alert">
                <strong>${requestScope.NOTIFICATION.message}</strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" onclick="document.getElementById('notification').hidden();"></button>
            </div>
        </c:if>
        <div style="margin-left: 120px; margin-bottom: 20px">
            <form action="DispatchServlet" method="POST">
                <input type="hidden" value="${param.searchName}" name="searchName">
                <button type="submit" class="btn btn-primary" name="btAction" value="Search" title="Back"><i class="fas fa-home"> Home</i></button>
            </form>
        </div>
        <div class="container">
            <c:choose>
                <c:when test="${empty orders}">
                    <h2 class="alert alert-secondary" style="text-align: center; color: blue">Not any orders.</h2>
                </c:when>
                <c:otherwise>
                    <table class="table table-hover" style="text-align: center">
                        <thead>
                            <tr class="table-dark">
                                <th scope="col">Order ID</th>
                                <th scope="col">Order Date</th>
                                <th scope="col">Total</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${orders}" var="order" >
                                <tr class="${counter.count % 2 eq 0 ? 'table-success' : 'table-secondary'}" data-bs-toggle="collapse" data-bs-target="#collapseExample" aria-expanded="false" aria-controls="collapseExample">
                                    <td style="width: 200px; text-align: center">
                                        ${order.orderId}
                                    </td>
                                    <td style="text-align: center">${order.orderDate}</td>
                                    <td>${order.total} VNĐ</td>
                                </tr>
                                <tr class="collapse" id="collapseExample">
                                    <td colspan="3">
                                        <table class="table table-sm" style="text-align: center; width: 70%; margin: 0 auto" >
                                            <thead>
                                                <tr class="table-warning">
                                                    <th scope="col">Image</th>
                                                    <th scope="col">Name</th>
                                                    <th scope="col">Pice</th>
                                                    <th scope="col">Quantity</th>
                                                </tr>
                                            <tbody>
                                                <c:forEach items="${order.details}" var="detail" varStatus="counter">
                                                    <tr class="${counter.count % 2 eq 0 ? 'table-info' : 'table-light'}">
                                                        <td style="width: 200px; text-align: center">
                                                            <img id="img${detail.product.image}" style="width: 300px; height: 200px" src="${pageContext.request.contextPath}${detail.product.image}"/>
                                                            <script>
                                                                img = document.getElementById('img${detail.product.image}');
                                                                img.addEventListener('error', function handleError() {
                                                                    const defaultImage = '${pageContext.request.contextPath}/images/default_image.png';
                                                                    img.src = defaultImage;
                                                                    img.alt = 'default';
                                                                });
                                                            </script>
                                                        </td>
                                                        <td style="text-align: center">${detail.product.name}</td>
                                                        <td>${detail.price} VNĐ</td>
                                                        <td>${detail.quantity}</td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                            </thead>
                                        </table>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
