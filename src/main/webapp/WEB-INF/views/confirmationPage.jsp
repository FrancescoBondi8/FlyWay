<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="it">
<head>
  <meta charset="UTF-8">
  <title>Ricevuta</title>
  <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css' />">
</head>
<body>
<h2>Prenotazione effettuata</h2>
<p><strong>Città di Partenza:</strong> ${flight.departureCity}</p>
<p><strong>Città di Arrivo:</strong> ${flight.arrivalCity}</p>
<p><strong>Data Partenza:</strong> ${flight.flightDateFormatted}</p>
<p><strong>Ora Partenza:</strong> ${flight.departureTimeFormatted}</p>
<p><strong>Ora Arrivo:</strong> ${flight.arrivalTimeFormatted}</p>
<p><strong>Peso Bagaglio:</strong> ${weightBaggage} kg</p>

<div style="position: absolute; top: 20px; right: 20px;">
  <button onclick="window.location.href='/'">
    Torna alla home
  </button>
</div>
</body>
</html>
