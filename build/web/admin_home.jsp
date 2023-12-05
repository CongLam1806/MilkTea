<%@page import="java.text.DecimalFormat"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Staff Home</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"  crossorigin="anonymous"></script> 

        <link class="jsbin" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
        <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
        <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/jquery-ui.min.js"></script>

        <script src="./Price-Range-Slider-jQuery-UI/price_range_script.js" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="./Price-Range-Slider-jQuery-UI/price_range_style.css"/>
    </head>
    <body style="margin-left: 10px; background-color: beige">


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
                <strong>${requestScope.NOTIFICATION.message}</strong> <c:if test="${not empty param.numOfRow}"><strong><small> Update on row: ${param.numOfRow}</small></strong> </c:if>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" onclick="document.getElementById('notification').remove();"></button>
                    <small class="text-danger">
                    <c:set var="price_error" value="${requestScope.price_ERROR}"/>
                    
                    <c:if test="${not empty price_error}"> 
                        <br><strong>  + </strong>${price_error} <c:if test="${not (param.yearOfProduction eq '')}">Invalid value: ${param.price}</c:if>
                    </c:if>
                </small>
                <small class="text-danger">
                    <c:set var="quantity_error" value="${requestScope.quantity_ERROR}"/>
                    <c:if test="${not empty quantity_error}"> 
                        <br><strong>  + </strong>${quantity_error} <c:if test="${not (param.quantity eq '')}">Invalid value: ${param.quantity}</c:if>
                    </c:if>
                </small>
                <small class="text-danger">
                    <c:set var="description_error" value="${requestScope.description_ERROR}"/>
                    <c:if test="${not empty description_error}"> 
                        <br><strong>  + </strong>${description_error} <c:if test="${not (param.description eq '')}">Invalid value: ${param.description}</c:if>
                    </c:if>
                </small>
            </div>
        </c:if>

        <div class="container">
            <form action="DispatchServlet" method="GET" class="mb-3">
                <button title="Create new milk tea" class="btn btn-success" name="btAction" value="Create"><i class="fa fa-plus" aria-hidden="true"> Create New</i></button>
            </form>
            <c:choose>
                <c:when test="${empty result}">
                    <h2 class="alert alert-secondary" style="text-align: center; color: blue">Not any MilkTea in DB</h2>
                </c:when>
                <c:otherwise>
                    <table class="table" style="text-align: center">
                        <thead>
                            <tr class="table-dark">
                                <th>#</th>
                                <th scope="col">Image</th>
                                <th scope="col">Name</th>
                                <th scope="col">Price</th>
                                <th scope="col">Quantity</th>
                                <th scope="col">Status</th>
                                <th scope="col">Action</th>
                            </tr>
                        </thead>
                        <script>
                            let img = null;
                        </script>
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
                                        <input type="text" class="form-control text-center" placeholder="0" value="${product.quantity}" readonly>
                                    </td>
                                    <td style="text-align: center">
                                        <c:set var="status" value="${product.status}"/>
                                        <c:choose>
                                            <c:when test="${status}">
                                                <i class="fa fa-check" aria-hidden="true" style="color: green"></i>
                                            </c:when>
                                            <c:otherwise>
                                                <i class="fas fa-times" aria-hidden="true" style="color: red"></i>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td style="width: 150px; text-align: center">
                                        <form action="DispatchServlet" method="POST" style="display: inline-table">
                                            <button name="btAction" value="Edit" type="submit" class="btn btn-warning" title="Edit"><i class="fas fa-edit"></i></button>
                                            <input type="hidden" value="${product.productId}" name="productId" />
                                        </form>
                                        <form action="DispatchServlet" method="POST" style="display: inline-table" >
                                            <button ${not product.status ? 'disabled' : ''} type="submit" class="btn btn-danger" name="btAction" value="Delete" onclick="return confirmDelete()" title="Delete"><i class="fas fa-times"></i></button>
                                            <input type="hidden" value="${product.productId}" name="deletedProductId">
                                            <input type="hidden" value="${param.searchValue}" name="searchValue">
                                            <script>
                                                function confirmDelete() {
                                                    var conf = confirm('Are you sure about this delete action?');
                                                    if (conf) {
                                                        return true;
                                                    }
                                                    return false;
                                                }
                                            </script>
                                        </form>
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
