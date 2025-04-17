<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<html>
<head>
    <meta charset="UTF-8">
    <title>Modifica volo</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>
<body>
<form action="<c:url value='/flights/editFlight' />" method="post">

    <div class="form-group">
        <label for="flightId">ID del volo:</label>
        <input type="number" id="flightId" name="flightId"  required>
    </div>

    <button type="submit">Modifica Volo</button>
</form>

    <c:if test="${not empty error}">
        <div class="alert alert-danger">
                ${error}
        </div>
    </c:if>


    <!-- Compare solo se è stato trovato l'id -->
    <c:if test="${not empty success}">
        <form action="<c:url value='/flights/editFlightSave'/>" method="post">

            <input type="hidden" name="flightId" min = "0" max="9223372036854775807" value="${flight.id}"/>

            <div class="form-group">
                <label for="departureCity">Città di Partenza:</label>
                <input type="text" id="departureCity" name="departureCity" value="${flight.departureCity}" required>
            </div>

            <div class="form-group">
                <label for="arrivalCity">Città di Arrivo:</label>
                <input type="text" id="arrivalCity" name="arrivalCity" value="${flight.arrivalCity}" required>
            </div>

            <div class="form-group">
                <label for="flightDate">Data del Volo:</label>
                <input type="date" id="flightDate" name="flightDate" value="${flight.flightDate}" required>
            </div>

            <div class="form-group">
                <label for="departureTime">Ora di Partenza:</label>
                <input type="time" id="departureTime" name="departureTime" value="${flight.departureTimeFormatted}" required>
            </div>

            <div class="form-group">
                <label for="arrivalTime">Ora di Arrivo:</label>
                <input type="time" id="arrivalTime" name="arrivalTime" value="${flight.arrivalTimeFormatted}" required>
            </div>

            <div class="form-group">
                <label for="aircraft">Tipo di Aereo:</label>
                <select id="aircraft" name="aircraft" required>
                    <option value="${flight.aircraft.aircraftType}" selected>${flight.aircraft.aircraftType}</option>
                    <c:forEach var="aircraft" items="${aircraftList}">
                        <option value="${aircraft.aircraftType}">${aircraft.aircraftType}</option>
                    </c:forEach>
                </select>
            </div>

            <button type="submit">Modifica Volo</button>
        </form>

    </c:if>

<div style="position: absolute; top: 20px; right: 20px;">
    <button onclick="window.location.href='/menu'">
        Torna al Menu
    </button>
</div>
</body>
</html>
