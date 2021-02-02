package controller;

import model.Verschiebespiel;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Manuel Adams
 * @since 2018-11-13
 */
public class MySessionHandler implements HttpSessionListener {

    public static final String GAME_OBJECT_NAME = "game";

    @Override
    public void sessionCreated(HttpSessionEvent e) {
        HttpSession session = e.getSession();
        session.setAttribute(GAME_OBJECT_NAME, new Verschiebespiel());
        System.out.println("SESSION CREATED: " + session.getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent e) {
        HttpSession session = e.getSession();
        System.out.println("SESSION DESTROYED: " + session.getId());
    }

    public static void InvalidateSession(HttpSession session) {
        System.out.println("invalidating");
        if (session != null)
            session.invalidate();
    }
}
