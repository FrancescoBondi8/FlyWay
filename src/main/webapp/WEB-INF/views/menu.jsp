<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Menu</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>
<body>
<!-- Navbar -->
<nav class="navbar">
        <input type="checkbox" id="menu-toggle">
        <label for="menu-toggle" class="hamburger">☰</label>
        <ul class="menu">
        <li><a href="<c:url value='/flights/addFlight' />">Inserire un nuovo volo</a></li>
        <li><a href="<c:url value='/flights/editFlight' />">Modifica volo</a></li>
        <li><a href="<c:url value='/flights/deleteFlight' />">Cancellare un volo</a></li>
        <li><a href="<c:url value='/flights/listOfFlights' />">Visualizzare l’elenco dei voli</a></li>
        <li><a href="<c:url value='/logout' />">Logout</a></li>
    </ul>
</nav>




<!-- Contenuto principale della pagina -->
<div class="container">
    <h2>Benvenuto, ${loggedUser.username}!</h2>
    <h3>Seleziona un'opzione dal menu per gestire i voli.</h3>

    <!-- Messaggio di errore in caso di problemi con le operazioni -->
    <c:if test="${not empty error}">
        <div class="alert alert-danger">
                ${error}
        </div>
    </c:if>

    <!-- Messaggio di successo per le operazioni riuscite -->
    <c:if test="${not empty success}">
        <div class="alert alert-success">
                ${success}
        </div>
    </c:if>
</div>
</body>
</html>
