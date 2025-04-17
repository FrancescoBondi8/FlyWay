<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Flight Search</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/style.css' />">
</head>
<body>

<h2>Benvenuto su FlyWay</h2>
<h3>Cerca tra i voli disponibili:</h3>

<!-- Messaggio di errore in caso di problemi con le operazioni -->
<c:if test="${not empty error}">
    <div class="alert alert-danger">
            ${error}
    </div>
</c:if>


<form action="${pageContext.request.contextPath}/flights/search" method="post">
    <label>Aeroporto di Partenza:</label>
    <select name="departureCity" required>
        <option value="">Seleziona...</option>
        <c:forEach var="city" items="${cities}">
            <option value="${city}">${city}</option>
        </c:forEach>
    </select>

    <br><br>

    <label>Aeroporto di arrivo:</label>
    <select name="arrivalCity" required>
        <option value="">Seleziona...</option>
        <c:forEach var="city" items="${cities}">
            <option value="${city}">${city}</option>
        </c:forEach>
    </select>

    <br><br>

    <label>Data di partenza:</label>
    <input type="date" name="flightDate"  id ="flightDate" required><br><br>

    <label>Peso del bagaglio (kg):</label>
    <input type="number" name="weightBaggage" min="0" max="9223372036854775807" required><br><br>

    <input type="submit" value="Cerca Voli">
</form>


<div style="position: absolute; top: 20px; right: 20px;">
    <button onclick="window.location.href='/login'">
        Login
    </button>
</div>


<script>
    //il calendario parte dal 1 gennaio 2017 (così come nel db)
    window.onload = function() {
        var defaultDate = "2017-01-01";
        document.getElementById('flightDate').value = defaultDate;
    }
</script>
</body>
</html>

