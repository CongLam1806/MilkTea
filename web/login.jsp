<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet" crossorigin="anonymous">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.bundle.min.js"  crossorigin="anonymous"></script>
    </head>
    <body style="margin-left: 10px; background-color: beige; margin-top: 200px;">
        <div class="container" style="background: rgb(128,128,128, 0.4); width: 600px;padding: 20px; border-radius: 25px">
            <h1 style="margin-top: 10px; margin-left: 180px">Login Form</h1>

            <c:set var="error_message" value="${requestScope.ERROR_MESSAGE}"/>
            
            <c:if test="${not empty error_message}">
                <script>
                    alert('${error_message}');
                </script>
            </c:if>

            <div class="container">
                <form action="DispatchServlet" method="POST" style="width: 500px">
                    <div class="mb-3">
                        <label for="userId" class="form-label">UserName</label>
                        <input type="text" class="form-control" id="userId" name="txtUserName">
                    </div>
                    <div class="mb-3">
                        <label for="password" class="form-label">Password</label>
                        <input type="password" class="form-control" id="password" name="txtPassword">
                    </div>
                    <br>
                    <button type="submit" class="btn btn-primary" name="btAction" value="Login">Login</button>
                    <button type="submit" class="btn btn-primary" name="btAction" value=""><i class="fas fa-home"> Home</i></button>
                </form>
            </div>
        </div>
    </body>
</html>
