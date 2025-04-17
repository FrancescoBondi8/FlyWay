<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Aggiungi un nuovo volo</title>
  <link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>
<body>
<div class="container">
  <h2>Aggiungi un nuovo volo</h2>



  <c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
  </c:if>

  <c:if test="${not empty success}">
    <div class="alert alert-success">${success}</div>
  </c:if>


  <!-- Form per inserire i dati del volo -->
  <form action="<c:url value='/flights/addFlight' />" method="post">

    <div class="form-group">
      <label for="departureCity">Città di Partenza:</label>
      <input type="text" id="departureCity" name="departureCity" required>
    </div>

    <div class="form-group">
      <label for="arrivalCity">Città di Arrivo:</label>
      <input type="text" id="arrivalCity" name="arrivalCity" required>
    </div>

    <div class="form-group">
      <label for="flightDate">Data del Volo:</label>
      <input type="date" id="flightDate" name="flightDate" required>
    </div>

    <div class="form-group">
      <label for="departureTime">Ora di Partenza:</label>
      <input type="time" id="departureTime" name="departureTime" required>
    </div>

    <div class="form-group">
      <label for="arrivalTime">Ora di Arrivo:</label>
      <input type="time" id="arrivalTime" name="arrivalTime" required>
    </div>

    <div class="form-group">
      <label for="aircraft">Tipo di Aereo:</label>
      <select id="aircraft" name="aircraft" required>
        <option value="">Seleziona...</option>
        <c:forEach var="aircraft" items="${aircraftList}">
          <option value="${aircraft.aircraftType}">${aircraft.aircraftType}</option>
        </c:forEach>
      </select>
    </div>
    <button type="submit">Aggiungi Volo</button>
  </form>


  <div style="position: absolute; top: 20px; right: 20px;">
    <button onclick="window.location.href='/menu'">
      Torna al Menu
    </button>
  </div>

</div>
</body>
</html>
