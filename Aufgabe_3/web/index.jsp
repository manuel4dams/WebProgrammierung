<%@ page import="controller.RestartGame" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="static controller.MySessionHandler.GAME_OBJECT_NAME" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" type="text/css" href="sliderGame.css">
    <title>Verschiebespiel</title>
</head>
<body>
<main>
    <header>Verschiebespiel</header>
    <br>
    <% if (!((Verschiebespiel) session.getAttribute(GAME_OBJECT_NAME)).richtigeReihenfolge()) { %>
    <%@include file="table.jsp" %>
    <br>
    <% } else { %>
    <h1>Gewonnen!</h1>
    <% } %>
    <a href="${pageContext.request.contextPath}<%= RestartGame.URL %>?newGameButton=true" id="newGameButton">
        Neues Spiel Starten
    </a>
    <br>
</main>
</form>
</body>
</html>

