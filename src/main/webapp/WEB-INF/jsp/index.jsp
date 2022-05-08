<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Home</title>
    <meta name="viewport" charset="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/main.css">
</head>
<body>
<main>
    <h1>Hello, ${fn:substringBefore(username, "@")}</h1>
    <section>
        <c:if test="${searchError != null}">
            <div style="color: red">
                <p>${searchError}</p>
            </div>
        </c:if>
        <h2>Search</h2>
        <form method="get" action="${pageContext.request.contextPath}/resources/search/transportations">
            <input id="offset" type="text" name="from" placeholder="From" required>
            <input id="destination" type="text" name="to" placeholder="To" required>
            <label for="departureDateTime">
                Departure Time:
                <input id="departureDateTime" type="datetime-local" name="departureDateTime" required>
            </label>
            <button type="submit" name="submit">Search</button>
        </form>
    </section>
</main>
</body>
</html>