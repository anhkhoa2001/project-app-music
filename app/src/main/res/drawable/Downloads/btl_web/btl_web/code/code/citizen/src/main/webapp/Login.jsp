<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="resources/css/login.css">
    <link rel="stylesheet" href="resources/themify-icons/themify-icons.css">
</head>
<body>
    <div class="container">
        <div class="logo">
            <h1>CitizenV</h1>
            <p>Hệ thống điều tra dân số</p>
        </div>
        <div class="login">
            <h1>Đăng nhập</h1>
            <div class="form-login">
                <i class="ti-user"></i>
                <input type="text" placeholder="Username" required  id = "user">
            </div>
            <div class="form-login">
                <i class="ti-lock"></i>
                <input type="password" placeholder="Password" required  id = "pass">
            </div>
            <button onclick="Submit()" onkeyup="Submit()">Đăng nhập</button>
        </div>
    </div>
    <script src = "resources/js/login.js"></script>
</body>
</html>