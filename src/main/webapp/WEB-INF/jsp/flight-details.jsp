<%@ page pageEncoding="UTF-8" contentType="text/html; charset=utf-8" import="java.util.Set" %>
<%@ page import="com.platform.business.service.search.transportations.dto.AirlineTransportationDto" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
            <th>Available Seats</th>
        </tr>
        <tr>
            <td>${flight.id}</td>
            <td>${flight.departuresAt}</td>
            <td>${flight.offset}</td>
            <td>${flight.destination}</td>
            <td>${flight.availableSeats}</td>
        </tr>
    </table>
</main>
</body>
</html>
