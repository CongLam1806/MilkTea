<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Edit Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"  crossorigin="anonymous"></script>
    
        
        <link class="jsbin" href="http://ajax.googleapis.com/ajax/libs/jqueryui/1/themes/base/jquery-ui.css" rel="stylesheet" type="text/css" />
        <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
        <script class="jsbin" src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.0/jquery-ui.min.js"></script>
        
        <style>
            input[type="file"] {
                display: none;
            }
            .custom-file-upload {
                cursor: pointer;
            }
        </style>
    </head>
    <body style="margin-left: 10px; background-color: beige">
        <%@include file="header.jsp" %>

        <div class="row g-3 mt-2" style="margin-left: 100px;">
            <div class="col-lg-10">
                <h1 class="mt-2">Edit Form</h1>
            </div>
            <div class="col-lg-1" style="margin-left: 70px">
                <form action="DispatchServlet" method="POST">
                    <input type="hidden" value="${param.searchName}" name="searchName">
                    <button type="submit" class="btn btn-primary" name="btAction" value="Search" title="Back"><i class="fas fa-home"> Home</i></button>
                </form>
            </div>
        </div>
        <c:if test="${not empty requestScope.NOTIFICATION}">
            <div id="notification" class="container alert alert-${requestScope.NOTIFICATION.result ? 'success' : 'danger'} alert-dismissible fade show" role="alert">
                <strong>${requestScope.NOTIFICATION.message}</strong>
                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" onclick="document.getElementById('notification').hidden();"></button>
            </div>
        </c:if>
        <c:set var="product" value="${requestScope.UPDATE_PRODUCT}"/>            
        <div class="container" style="background: rgb(128,128,128, 0.4); padding: 20px; border-radius: 25px">
            <form class="row g-3" action="UpdateServlet" method="POST" enctype="multipart/form-data">
                <input type="hidden" name="image" value="${not empty param.image ? param.image : product.image}">
                <input type="hidden" name="productId" value="${not empty param.productId ? param.productId : product.productId}">
                <div class="col-md-7 row">
                    <div class="col-md-12 p-2">
                        <label for="productName" class="form-label">Product Name</label>
                        <input style="background-color: beige" type="text" class="form-control" id="productName" name="productName" value="${not empty param.productName ? param.productName : product.name}">
                        <small class="text-danger">
                            ${requestScope.productName_ERROR}
                        </small>
                    </div>
                    <div class="col-md-6 p-2">
                        <label for="mobileName" class="form-label">Price</label>
                        <input style="background-color: beige" type="text" class="form-control" id="mobileName" name="price" value="${not empty param.price ? param.price : product.price}">
                        <small class="text-danger">
                            ${requestScope.price_ERROR}
                        </small>
                    </div>   
                    <div class="col-md-6 p-2">
                        <label for="mobileName" class="form-label">Quantity</label>
                        <input style="background-color: beige" type="text" class="form-control" id="mobileName" name="quantity" value="${not empty param.quantity ? param.quantity : product.quantity}">
                        <small class="text-danger">
                            ${requestScope.quantity_ERROR}
                        </small>
                    </div>
                    <div class="col-md-4">
                        <div class="row">
                            <div class="col-12">
                                <c:set var="status" value="${not empty param.status ? param.status : product.status}"/>
                                <label for="status" class="form-label">Status</label>
                                <select id="status" style="background-color: beige;" class="form-control" name="status">
                                    <option value="true" ${status ? 'selected' : ''}>Active</option>
                                    <option value="false" ${not status ? 'selected' : ''}>Inactive</option>
                                </select>
                            </div>
                        </div>
                        <br>
                        <div class="row">
                            <div class="col-12">
                                <c:set var="categoryId" value="${not empty param.categoryId ? param.categoryId : product.categoryId}"/>
                                <label for="categoryId" class="form-label">Category</label>
                                <select name="categoryId" class="form-control" style="background-color: beige">
                                    <c:forEach items="${requestScope.LIST_CATEGORIES}" var="category">
                                        <option value="${category.categoryId}" ${categoryId eq category.categoryId ? 'selected' : ''}>
                                            ${category.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="row">
                            <div class="col-12">
                                <label for="desc" class="form-label">Description</label>
                                <textarea style="background-color: beige; resize: none" rows="5" class="form-control" id="desc" placeholder="Product's description" name="description">${not empty param.description ? param.description : product.description}</textarea>
                                <small class="text-danger">
                                    ${requestScope.description_ERROR}
                                </small>
                            </div>
                        </div>
                    </div>
                    <div class="col-12">
                        <button type="submit" class="btn btn-primary mt-3" value="Update"><i class="fas fa-save"> UPDATE</i></button>
                        <label for="file-upload" class="custom-file-upload btn btn-primary mt-3 float-end">
                            <i class="fas fa-cloud-upload-alt"></i> Upload Image
                        </label>
                        <input value="${param.file}" name="file" class="btn btn-primary mt-3" id="file-upload" type="file" onchange="readURL(this);"/>
                    </div>
                </div>
                <div class="col-md-5" style="text-align: center">
                    <img id="blah" src="${pageContext.request.contextPath}${product.image}" alt="your image" style="height: 380px; width: 450px; border: 3px black"/>
                </div>
                <script>
                    function readURL(input) {
                        if (input.files && input.files[0]) {
                            var reader = new FileReader();

                            reader.onload = function (e) {
                                $('#blah')
                                        .attr('src', e.target.result)
                                        .width(450)
                                        .height(380);
                            };

                            reader.readAsDataURL(input.files[0]);
                        }
                    }

                    const img = document.getElementById('blah');
                    img.addEventListener('error', function handleError() {
                        const defaultImage = '${pageContext.request.contextPath}/images/default_image.png';
                        img.src = defaultImage;
                        img.alt = 'default';
                    });
                </script>               
            </form>
        </div>
    </body>
</html>
