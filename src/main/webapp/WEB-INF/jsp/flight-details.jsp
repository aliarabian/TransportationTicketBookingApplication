<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" import="java.util.Set" %>
<%@ page import="com.platform.business.service.search.transportations.dto.AirlineTransportationDto" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Flight Details</title>
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
    <h1>Flight Details </h1>
    <table>
        <tr>
            <th>Flight NO</th>
            <th>Departure Time</th>
            <th>From</th>
            <th>To</th>
            <c:forEach var="section" items="${flight.sections}">
                <th> ${section.title} Available Seats</th>
            </c:forEach>
            <th>Total Available Seats</th>
        </tr>
        <tr>
            <td>${flight.id}</td>
            <%
                AirlineTransportationDto transportationDto = (AirlineTransportationDto) request.getAttribute("flight");
                String departureTime = transportationDto.getDeparturesAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z"));
                request.setAttribute("departureTime", departureTime);
            %>
            <td>${departureTime}</td>
            <td>${flight.offset}</td>
            <td>${flight.destination}</td>
            <c:forEach var="section" items="${flight.sections}">
                <td> ${section.availableSeats}</td>
            </c:forEach>
            <td>${flight.availableSeats}</td>
        </tr>
    </table>

</main>
</body>
</html>
