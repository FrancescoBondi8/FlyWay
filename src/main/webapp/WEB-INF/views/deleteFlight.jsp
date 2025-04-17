<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<html>
<head>
    <meta charset="UTF-8">
    <title>Modifica volo</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>
<body>
<form action="<c:url value='/flights/deleteFlight' />" method="post">

    <div class="form-group">
        <label for="flightId">ID del volo:</label>
        <input type="number" id="flightId" name="flightId" required>
    </div>

    <button type="submit">Elimina Volo</button>
</form>

<c:if test="${not empty error}">
    <div class="alert alert-danger">
            ${error}
    </div>
</c:if>


<!-- Compare solo se è stato trovato l'id -->
<c:if test="${not empty success}">
    <form action="<c:url value='/flights/deleteFlightSave'/>" method="post">
        <input type="hidden" name="flightId" value="${flight.id}"/>
        <p><strong>Città di Partenza:</strong> ${flight.departureCity}</p>
        <p><strong>Città di Arrivo:</strong> ${flight.arrivalCity}</p>
        <p><strong>Data Partenza:</strong> ${flight.flightDateFormatted}</p>
        <p><strong>Ora Partenza:</strong> ${flight.departureTimeFormatted}</p>
        <p><strong>Ora Arrivo:</strong> ${flight.arrivalTimeFormatted}</p>
        <p><strong>Peso Bagaglio:</strong> ${flight.weightBaggage} kg</p>

        <button type="submit">Elimina volo</button>
    </form>

</c:if>

<div style="position: absolute; top: 20px; right: 20px;">
    <button onclick="window.location.href='/menu'">
        Torna al Menu
    </button>
</div>
</body>
</html>

