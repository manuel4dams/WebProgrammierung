<%@ page import="controller.SliderGame" %>
<%@ page import="model.Verschiebespiel" %>
<%@ page import="static controller.MySessionHandler.GAME_OBJECT_NAME" %>
<form action="<%= SliderGame.URL %>" method="post">
    <table>
        <%
            Verschiebespiel sessionLogic = (Verschiebespiel) session.getAttribute(GAME_OBJECT_NAME);

            int index = 0;
            for (int i = 0; i < sessionLogic.getGrid().length / 3; i++) {
        %>
        <tr>
            <% for (int j = 0; j < sessionLogic.getGrid().length / 3; j++) { %>
            <td>
                <input type="submit" name="button" class="number"
                       value="<%= (sessionLogic.getGrid()[index] == null) ? "" : sessionLogic.getGrid()[index] %>">
            </td>
            <% index++;%>
            <% } %>
        </tr>
        <% } %>
    </table>
</form>
<br>
<p id="count">
    Anzahl der Verschiebungen:
    <%= sessionLogic.getMoveCount() %>
</p>
