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
    <body style="background-color: beige">
        <%@include file="header.jsp" %>

        <div class="row" >
            <div class="price-range-block" style="margin-left: 120px; width: 1000px">
                <div class="">
                    <div style="margin:20px auto">
                        <form action="DispatchServlet" method="POST">
                            <input name="searchValue" type="text" class="price-range-field" value="${param.searchValue}" placeholder="Search Name"/>
                            <button type="submit" class="btn btn-info" name="btAction" value="Search by price" title="Search By Name" style="margin-bottom: 5px"><i class="fas fa-search"></i></button>
                        </form>
                    </div>
                </div>
            </div>
        </div>

        <c:set var="result" value ="${requestScope.SEARCH_RESULT}" />    

        <c:if test="${not empty requestScope.NOTIFICATION}">
            <div id="notification" class="container alert alert-${requestScope.NOTIFICATION.result ? 'success' : 'danger'} alert-dismissible fade show" role="alert">
                <strong>${requestScope.NOTIFICATION.message}</strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" onclick="document.getElementById('notification').hidden();"></button>
            </div>
        </c:if>

        <div class="container">
            <c:choose>
                <c:when test="${empty result}">
                    <h2 class="alert alert-secondary" style="text-align: center; color: blue">Not any milk tea</h2>
                </c:when>
                <c:otherwise>
                    <table class="table" style="text-align: center">
                        <thead>
                            <tr class="table-dark">
                                <th scope="col">Image</th>
                                <th scope="col">Name</th>
                                <th scope="col">Description</th>
                                <th scope="col">Price</th>
                                <th scope="col">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${result}" var="product" >
                                <c:if test="${product.status}">
                                    <tr class="${counter.count % 2 eq 0 ? 'table-success' : 'table-secondary'}">
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
                                        <td style="text-align: center">${product.description}</td>
                                        <td>${product.price} VNĐ</td>
                                        <td style="width: 150px">
                                            <form action="DispatchServlet" method="POST" style="display: inline-table">
                                                <button style="" ${not product.status or product.quantity <= sessionScope.CART.items[product.productId] or product.quantity eq 0 ? 'disabled' : ''} type="submit" class="btn btn-warning" name="btAction" value="Add to cart" title="Add to cart">
                                                    <i class="fas fa-cart-plus"></i>
                                                </button>

                                                <input type="hidden" name="productId" value="${product.productId}">
                                                <input type="hidden" name="searchValue" value="${param.searchValue}"/>
                                            </form>
                                        </td>
                                    </tr>
                                </c:if>
                            </c:forEach>
                        </tbody>
                    </table>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>
