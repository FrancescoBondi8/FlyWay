<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista dei voli</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css' />">
</head>
<body>
<h2>Lista dei voli</h2>



    <form action="/flights/listOfFlights" method="get">
        <label for="startDate">Mostra voli da questa data:</label>
        <input type="date" id="startDate" name="flightDate" value="${flightDate}" >
        <button type="submit">Cerca</button>
    </form>
<!-- Verifica se la lista dei voli è vuota -->
<c:if test="${not empty flights}">
    <table border="1">
        <thead>
        <tr>
            <th>ID</th>
            <th>Aereo</th>
            <th>Città di Partenza</th>
            <th>Città di Arrivo</th>
            <th>Data Partenza</th>
            <th>Ora Partenza</th>
            <th>Ora di Arrivo</th>
            <th>Posti occupati</th>
            <th>Peso bagaglio attuale</th>
        </tr>
        </thead>
        <tbody>
        <!-- Itera sui voli e visualizza i dettagli -->
        <c:forEach var="flight" items="${flights}">
            <tr>
                <td>${flight.id}</td>
                <td>${flight.aircraft.aircraftType}</td>
                <td>${flight.departureCity}</td>
                <td>${flight.arrivalCity}</td>
                <td>${flight.flightDateFormatted}</td>
                <td>${flight.departureTimeFormatted}</td>
                <td>${flight.arrivalTimeFormatted}</td>
                <td>${flight.numberOfPassengers}</td>
                <td>${flight.weightBaggage}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<!-- Se non ci sono voli, mostra un messaggio -->
<c:if test="${empty flights}">
    <p>Nessun volo trovato</p>
    <a href="/menu" class="home-link">Ritorna al Menù</a>
</c:if>


<div style="position: absolute; top: 20px; right: 20px;">
    <button onclick="window.location.href='/menu'">
        Torna al Menu
    </button>
</div>

</body>
</html>

