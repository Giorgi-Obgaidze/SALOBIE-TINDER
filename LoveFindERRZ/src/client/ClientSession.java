package client;

import database.DataAdministrator;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class ClientSession implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        //DataAdministrator da = (DataAdministrator) httpSessionEvent.getSession().getServletContext().getAttribute(DataAdministrator.AttributeName);

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
    }
}
