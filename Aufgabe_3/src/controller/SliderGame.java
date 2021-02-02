package controller;

import model.Verschiebespiel;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Manuel Adams
 * @since 2018-11-21
 */
 
 //TODO Tabelle hätte man eleganter aufbauen können im jsp. Buttons vorgeben und value der Buttons mit der methode getgridelement(x,y) setzten.

public class SliderGame extends HttpServlet {

    public static final String URL = "/" + SliderGame.class.getSimpleName();

//    public static Verschiebespiel getModel(HttpServletRequest request) {
//        HttpSession session = request.getSession(true);
//        //session.setMaxInactiveInterval(60);
//
//        Verschiebespiel game = (Verschiebespiel) session.getAttribute("game");
//
//        if (session.isNew() || session.getAttribute("game") == null) {
//            System.out.println("creating new session");
//            session.setAttribute("game", game);
//        } else {
//            System.out.println("session refresh");
//        }
//        return (Verschiebespiel) session.getAttribute("game");
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Verschiebespiel sessionLogic = (Verschiebespiel) request.getSession(true).getAttribute(MySessionHandler.GAME_OBJECT_NAME);

//        if (sessionLogic.richtigeReihenfolge()) {
//            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/gameWon.jsp");
//            dispatcher.forward(request, response);
//            return;
//        }

        String number = request.getParameter("button");
        if (number != null && !number.equals("")) {
            sessionLogic.move(Integer.parseInt(number));
            sessionLogic.TestSpielEnde();
        }

//        request.setAttribute("number", sessionLogic.getGrid());
//        request.setAttribute("counter", sessionLogic.getMoveCount());

        response.sendRedirect("/index.jsp");
    }
}
