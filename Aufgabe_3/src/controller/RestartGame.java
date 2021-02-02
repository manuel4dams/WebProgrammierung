package controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Manuel Adams
 * @since 2018-11-26
 */
public class RestartGame extends HttpServlet {

    public static final String URL = "/" + RestartGame.class.getSimpleName();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("newGameButton") != null) {
            MySessionHandler.InvalidateSession(request.getSession(false));
        }

        response.sendRedirect("/index.jsp");
    }
}
