<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
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

        <style>
            body { min-height: 100vh; font-family: 'Roboto'; background-color:#fafafa}

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
    <body style="margin-left: 10px; background-color: beige">
        <%@include file="header.jsp" %>

        <c:set var="result" value ="${requestScope.CART_ITEMS}" />    

        <div class="row g-3 mt-3" style="margin-left: 100px; margin-bottom: 20px; margin-right: 100px">
            <div class="col-lg-8" style="display: inline-block">
                <form action="DispatchServlet" method="POST" class="" style="display: inline">
                    <button type="submit" class="btn btn-primary" name="btAction" value="Checkout" title="Checkout">
                        <i class="fas fa-money-check-alt"> Checkout</i>
                    </button>
                </form>
                <form action="DispatchServlet" method="POST" style="display: inline">
                    <button type="button" class="btn btn-primary" title="Update Cart" onclick="update();">
                        <i class="fa fa-cart-arrow-down"> Update Cart</i>
                    </button>
                    
                    <script>
                        function update() {
                            let form = document.getElementById('updateForm');
                            form.submit();
                        }
                    </script>
                </form>
                <h3 style="color: blue">Total: ${requestScope.TOTAL} VNĐ</h3>
            </div>
            <div class="col-lg-4">
                <form action="DispatchServlet" method="POST">
                    <button style="float: right" type="submit" class="btn btn-primary" name="btAction" value="Search by price" title="Back for shopping"><i class="fas fa-home"></i></button>
                </form>
            </div>
        </div>

        <c:if test="${not empty requestScope.NOTIFICATION}">
            <div id="notification" class="container alert alert-${requestScope.NOTIFICATION.result ? 'success' : 'danger'} alert-dismissible fade show" role="alert">
                <strong>${requestScope.NOTIFICATION.message}</strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" onclick="document.getElementById('notification').hidden();"></button>
            </div>
        </c:if>

        <div class="container">
            <c:choose>
                <c:when test="${empty result}">
                    <h2 class="alert alert-secondary" style="text-align: center; color: blue">Not any product in your cart</h2>
                </c:when>
                <c:otherwise>
                    <form action="DispatchServlet" method="post" id="updateForm">
                        <input type="hidden" name="btAction" value="Update cart" />
                        <table class="table" style="text-align: center">
                            <thead>
                                <tr class="table-dark">
                                    <th>#</th>
                                    <th scope="col">Image</th>
                                    <th scope="col">Name</th>
                                    <th scope="col">Price</th>
                                    <th scope="col">Quantity</th>
                                    <th scope="col">Action</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${result}" var="product" varStatus="counter">
                                    <tr class="${counter.count % 2 eq 0 ? 'table-success' : 'table-secondary'}">
                                        <th scope="row">${counter.count}</th>
                                        <td style="width: 200px; text-align: center">
                                            <img id="img${product.productId}" style="width: 300px; height: 200px" src="${pageContext.request.contextPath}${product.image}"/>
                                            <script>
                                            img = document.getElementById('img${product.productId}');
                                            img.addEventListener('error', function handleError() {
                                                const defaultImage = '${pageContext.request.contextPath}/images/default_image.png';
                                                img.src = defaultImage;
                                                img.alt = 'default';
                                            });
                                            </script>
                                        </td>
                                        <td style="text-align: center">${product.name}</td>
                                        <td style="width: 140px" style="text-align: center">
                                            <input type="text" class="form-control text-center" value="${product.price}" readonly/>
                                        </td>
                                        <td style="width: 100px" style="text-align: center">
                                            <input name="quantity" type="number" min="1" max="${product.quantity}" class="form-control text-center" placeholder="0" value="${sessionScope.CART.items[product.productId]}">
                                        </td>
                                        <td style="width: 150px">
                                            <button type="button" class="btn btn-danger" title="Remove from cart" onclick="remove('${product.productId}');">
                                                <i class="fas fa-times"> Remove</i>
                                            </button>
                                            <input type="hidden" name="productId" value="${product.productId}">
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </form>
                    <form action="DispatchServlet" method="POST" style="display: inline-table" id="removeForm">
                        <input type="hidden" name="btAction" value="Remove from cart"/>
                        <input type="hidden" name="productId" value="" id="removeId">
                    </form>
                    <script>
                        function remove(productId) {
                            let form = document.getElementById('removeForm');
                            let input = document.getElementById('removeId');
                            input.value = productId;

                            form.submit();
                        }
                    </script>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
