package controller;

import sessionLogic.Verschiebespiel;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

/**
 * @author Manuel Adams
 * @since 2018-11-21
 */

public class SliderGame extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        Verschiebespiel sessionLogic = (Verschiebespiel) request.getSession(true).getAttribute(request.getSession().getId());

        response.setContentType("text/xml");

        String number = request.getParameter("button");
        if (number != null) {
            if (number.equals("true")) {
                MySessionHandler.getNewModel(request.getSession());
                sessionLogic = (Verschiebespiel) request.getSession(true).getAttribute(request.getSession().getId());
            } else if (!number.equals("") && !number.equals("true") && !sessionLogic.richtigeReihenfolge()) {
                sessionLogic.move(Integer.parseInt(number));
                sessionLogic.TestSpielEnde();
            }
        }

        try {
            PrintWriter writer = response.getWriter();
            System.out.println(Arrays.toString(sessionLogic.getGrid()));
            writer.print("<data>");
            writer.print("<numbers>");
            for (Integer numbers : sessionLogic.getGrid()) {
                if (numbers == null) {
                    writer.print("0");
                } else {
                    writer.print(numbers);
                }
            }
            writer.print("</numbers>");
            writer.print("<count>" + sessionLogic.getMoveCount() + "</count>");
            if (sessionLogic.richtigeReihenfolge()) {
                writer.print("<status>won</status>");
            } else {
                writer.print("<status>inProgress</status>");
            }
            writer.print("</data>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
