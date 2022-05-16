<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Home</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/normalize.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/small-default.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/medium.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/large.css"/>
</head>
<body>
<div id="hidden-background"></div>
<header>
    <h1>Jalal Airlines</h1>
    <h2>${fn:substringBefore(username, "@")}</h2>
</header>
<nav></nav>
<main>
    <article>
        <div id="flight-search">
            <form id="flight-search-form" method="get"
                  action="${pageContext.request.contextPath}/resources/search/transportations">
                <div class="flight-search-form-section">
                    <div class="select-component">
                        <input
                                id="flight-offset"
                                type="text"
                                name="offset"
                                autocomplete="off"
                                placeholder="Offset?"
                                required
                        />
                        <ul class="cities"></ul>
                    </div>
                    <div class="select-component">
                        <input
                                id="flight-destination"
                                type="text"
                                name="destination"
                                autocomplete="off"
                                placeholder="Destination?"
                                required
                        />
                        <ul class="cities"></ul>
                    </div>
                </div>
                <div class="flight-search-form-section">
                    <input
                            id="flight-departure-time"
                            type="datetime-local"
                            name="departureTime"
                            required
                    />
                    <button type="submit">Search</button>
                </div>
            </form>
        </div>
    </article>

    <article id="filterable-flight-list">
        <div id="filter-component">
            <div>
                <h3>Filter Results</h3>
            </div>
            <div id="filter-component-checkboxes">
                <label for="economy-class">Economy Class <input type="checkbox" name="economy" id="economy-class"
                                                                value="economy" checked/></label>

                <label for="business-class">Business Class
                    <input type="checkbox" name="business" id="business-class" value="business" checked/></label>

                <label for="first-class">First Class
                    <input type="checkbox" name="first" id="first-class" value="first" checked/></label>
            </div>

        </div>
        <div id="flights-container">
            <c:forEach var="flight" items="${requestScope.flights}">
                <c:forEach var="section" items="${flight.sections}">
                    <div class="card-wrapper">
                        <article class="flight-card"
                                 data-section-id="${section.id}"
                                 data-section="${section.title}"
                                 data-flight-no="${flight.id}"
                                 data-departure-datetime="${flight.departuresAt}"
                                 data-flight-offset="${flight.offset}"
                                 data-flight-destination="${flight.destination}"
                                 data-flight-plane-model="${flight.vehicleModelName}"
                                 data-flight-section-privileges='{ "privileges":[ <c:forEach var="privilege" items="${section.privileges}">
                                <c:out value="{\"id\": ${privilege.id}, \"description\":\"${privilege.description}\"},"/>
                            </c:forEach>
                            ]}'
                                 data-flight-available-seats="${section.availableSeats}">
                            <div class="flight-card-header">
                                <p>FlightNO #${flight.id}</p>
                                <p><strong>${section.title}</strong></p>
                                <a href="#">Homa Airlines</a>
                            </div>
                            <div class="flight-card-body">
                                <div class="flight-route">
                                    <p>${flight.offset}</p>
                                    <span class="flight-direction-icon"><svg viewBox="0 0 24 24" width="1rem"
                                                                             height="1rem"
                                                                             fill="currentColor">
                <path
                        d="M.601 12.008c0 .929.297 1.545 1.003 1.857.392.172.802.226 1.46.22l.362-.009 5.656-.24.26.368.326.493.42.659.87 1.41 1.573 2.626 1.678 2.855a2.204 2.204 0 0 0 1.858 1.155.9.9 0 0 0 .878-1.198l-3.018-8.582c-.008-.021-.002-.03.006-.03l6.447-.29.79 2.114.035.13c.257.593.77.862 1.287.761.61-.12 1.008-.711.889-1.322l-.005-.09.009-5.947c.05-.488-.338-1.007-.9-1.12-.546-.107-1.029.189-1.246.665l-.832 2.22-6.446-.29a.013.013 0 0 1-.011-.017l3.179-8.595a.9.9 0 0 0-.92-1.209l-.161.014c-.69.02-1.352.4-1.754 1.013L12.426 4.8l-1.21 2.02-.8 1.308-.54.86-.45.693-.238.347-.107.149-5.602-.264h-.623l-.16.007-.206.016c-1.287.127-1.889.767-1.889 2.072Z"
                        fill-rule="evenodd"></path>
              </svg></span>
                                    <p>${flight.destination}</p>
                                </div>
                                <div class="expandable">
                                    <p>Departure Time: 2023-1-12 6:10 AM</p>
                                    <p>Plane: planeModel</p>
                                    <p>Available Seats: availableSeats</p>
                                    <ul class="section-privileges">
                                    </ul>
                                </div>
                            </div>
                            <div class="flight-card-footer">
                                <p class="flight-details-see-more">more</p>
                            </div>
                        </article>
                    </div>
                </c:forEach>
            </c:forEach>
        </div>
    </article>
</main>
<footer></footer>
<script type="module" src="${pageContext.request.contextPath}/static/js/app.js"></script>
</body>
</html>