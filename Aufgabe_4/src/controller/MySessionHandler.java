package controller;

import sessionLogic.Verschiebespiel;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @author Manuel Adams
 * @since 2018-11-13
 */

public class MySessionHandler implements HttpSessionListener {


    @Override
    public void sessionCreated(HttpSessionEvent e) {
        HttpSession session = e.getSession();
        getNewModel(session);
        System.out.println("SESSION CREATED: " + session.getId());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent e) {
        HttpSession session = e.getSession();
        System.out.println("SESSION DESTROYED: " + session.getId());
    }

    public static void getNewModel(HttpSession session) {
        session.setAttribute(session.getId(), new Verschiebespiel());
    }
}
