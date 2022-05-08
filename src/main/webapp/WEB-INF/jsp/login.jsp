<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Home</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
</head>
<body>
<h1>Travel Booking Service</h1>
<main>
    <section id="login-form-container">
        <h2>Login</h2>
        <form id="login-form" method="post" action="${pageContext.request.contextPath}/login">
            <input id="username" type="text" name="username" placeholder="Email" required>
            <input id="password" type="password" name="password" placeholder="Password" required>
            <div>
                <button type="submit" name="submit">Login</button>
                <a href="#">Forgot Your Password?</a>
            </div>
        </form>
    </section>
</main>
</body>
</html>
