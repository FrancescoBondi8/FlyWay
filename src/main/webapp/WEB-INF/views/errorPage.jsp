<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Errore di Prenotazione</title>
  <link rel="stylesheet" href="<c:url value='/css/errorPage.css' />">
</head>
<body>
<h2>Errore</h2>

<div class="error-message">
  <p><strong>Si è riscontrato un problema</strong></p>
  <p>${error}</p>
</div>

<div>
  <a href="/" class="home-link">Torna alla home page</a>
</div>
</body>
</html>
