<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" import="java.util.Set" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Search Result</title>
    <style>table {
        font-family: arial, sans-serif;
        border-collapse: collapse;
        width: 100%;
    }

    td, th {
        border: 1px solid #dddddd;
        text-align: left;
        padding: 8px;
    }

    tr:nth-child(even) {
        background-color: #dddddd;
    }
    </style>

</head>
<body>
<main>
    <h1>Available Flights: </h1>
    <table>
        <tr>
            <th>Flight NO</th>
            <th>Departure Time</th>
            <th>Flight Details</th>
        </tr>
        <c:forEach var="flight" items="${requestScope.flights}">
            <tr>
                <td>${flight.id}</td>
                <td>${flight.departuresAt}</td>
                <td><a href="${pageContext.request.contextPath}/resources/transportations/${flight.id}">View</a></td>
            </tr>
        </c:forEach>
    </table>
</main>
</body>
</html>