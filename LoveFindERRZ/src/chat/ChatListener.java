package chat;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ChatListener implements ServletContextListener {
    public ChatListener(){

    }
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Map<String, HttpServletResponse> data = new HashMap<>();
        ServletContext s =  servletContextEvent.getServletContext();
        s.setAttribute("chatCon", data);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}