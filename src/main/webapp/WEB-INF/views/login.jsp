<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>
<body>
<h2>Login</h2>

<form action="<c:url value='/login' />" method="post">
    <div>
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" required>
    </div>
    <div>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required>
    </div>
    <div>
        <button type="submit">Login</button>
    </div>

    <br>
    <!-- Mostra messaggio di errore per le credenziali non valide -->
    <c:if test="${not empty error}">
        <div style="color: red; font-weight: bold;">
                ${error}
        </div>
    </c:if>
</form>

<!-- logout -->
<c:if test="${not empty loggedUser}">
    <form action="<c:url value='/logout' />" method="get">
        <button type="submit">Logout</button>
    </form>
</c:if>

<div style="position: absolute; top: 20px; right: 20px;">
    <button onclick="window.location.href='/'">
        Torna alla Home
    </button>
</div>

</body>
</html>

