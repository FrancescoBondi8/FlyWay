<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Flight Search Results</title>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css' />">
</head>
<body>
<h2>Available Flights</h2>

<!-- Verifica se la lista dei voli è vuota -->
<c:if test="${not empty flights}">
    <table border="1">
        <thead>
        <tr>
            <th>Città di Partenza</th>
            <th>Città di Arrivo</th>
            <th>Data Partenza</th>
            <th>Ora Partenza</th>
            <th>Ora di Arrivo</th>
            <th>Posti disponibili</th>
            <th>Peso bagaglio disponibile</th>
            <th>Azioni</th>
        </tr>
        </thead>
        <tbody>
        <!-- Itera sui voli e visualizza i dettagli -->
        <c:forEach var="flight" items="${flights}">
            <tr>
                <td>${flight.departureCity}</td>
                <td>${flight.arrivalCity}</td>
                <td>${flight.flightDateFormatted}</td>
                <td>${flight.departureTimeFormatted}</td>
                <td>${flight.arrivalTimeFormatted}</td>
                <td>${flight.availableSeats}</td>
                <td>${flight.availableBaggageWeight}</td>
                <td>
                    <button type="button" onclick="openModal('${flight.id}', '${flight.departureCity}', '${flight.arrivalCity}', '${flight.flightDateFormatted}', '${flight.departureTimeFormatted}', '${flight.arrivalTimeFormatted}', '${weightBaggage}')">Prenota</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<!-- Se non ci sono voli, mostra un messaggio -->
<c:if test="${empty flights}">
    <p>Nessun volo trovato per i criteri selezionati.</p>
</c:if>



<!-- Il Pop-up Modale -->
<div id="confirmationModal">
    <div class="modal-content">
        <span class="close" onclick="closeModal()">&times;</span>
        <h3>Conferma la prenotazione</h3>
        <p><strong>Città di Partenza:</strong> <span id="modalDepartureCity"></span></p>
        <p><strong>Città di Arrivo:</strong> <span id="modalArrivalCity"></span></p>
        <p><strong>Data Partenza:</strong> <span id="modalFlightDate"></span></p>
        <p><strong>Ora Partenza:</strong> <span id="modalDepartureTime"></span></p>
        <p><strong>Ora Arrivo:</strong> <span id="modalArrivalTime"></span></p>
        <form action="/flights/bookFlight" method="post">
            <!-- id del volo prenotato e peso del bagaglio inserito dall'utente -->
            <input type="hidden" name="flightId" id="modalFlightId" />
            <input type ="hidden" name ="weightBaggage" id ="modalBaggageWeight"/>
            <button type="submit">Conferma Prenotazione</button>
        </form>
        <button type="button" class = "annulla" onclick="closeModal()">Annulla</button>
    </div>
</div>

<div style="position: absolute; top: 20px; right: 20px;">
    <button onclick="window.location.href='/'">
        Torna alla Home
    </button>
</div>

<script>
    // Funzione per aprire il pop-up modale e visualizzare i dettagli del volo
    //ritorna oltre ai dati di riepilogo anche il peso del bagaglio così da aggiungerlo al DB successivamente
    function openModal(id, departureCity, arrivalCity, flightDate, departureTime, arrivalTime,weightBaggage) {
        document.getElementById('modalFlightId').value = id;
        document.getElementById('modalDepartureCity').innerText = departureCity;
        document.getElementById('modalArrivalCity').innerText = arrivalCity;
        document.getElementById('modalFlightDate').innerText = flightDate;
        document.getElementById('modalDepartureTime').innerText = departureTime;
        document.getElementById('modalArrivalTime').innerText = arrivalTime;
        document.getElementById('modalBaggageWeight').value = weightBaggage;
        document.getElementById('confirmationModal').style.display = 'block';
    }

    // Funzione per chiudere il pop-up modale
    function closeModal() {
        document.getElementById('confirmationModal').style.display = 'none';
    }
</script>
</body>
</html>
