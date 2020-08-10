package chat;

import database.DataAdministrator;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServlet extends HttpServlet {
    @Override
    protected synchronized void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        HttpSession curr_session = req.getSession();
        ServletContext s = getServletContext();
        HandleChat data = (HandleChat) s.getAttribute("chatCon");
        String from_id = (String) curr_session.getAttribute("fromId");
        System.out.println("came to do " + command + " " + from_id);
        LinkedBlockingQueue<String> msgQueue = null;
        DataAdministrator da = (DataAdministrator) getServletContext().getAttribute(DataAdministrator.AttributeName);
        if(command.equals("create")){
            if(!data.containsId(from_id)) {
                System.out.println("Created new ChatServlet");
                Map<String, LinkedBlockingQueue<String>> msg = new HashMap<>();
                data.add(from_id, msg);
            }
        }else if(command.equals("nextstep")){
            var frId = req.getParameter("frdId");
            try {
                da.updateStatus(frId, from_id, "nextstep");
                da.updateStatus(from_id, frId,"nextstep");
                LinkedBlockingQueue<String> msgQ = data.get(from_id, frId);
                msgQ.put("code:NEXTSTEPACCEPTED");
            } catch (SQLException | InterruptedException throwables) {
                throwables.printStackTrace();
            }
        }else if(command.equals("get")){
            String fromId = req.getParameter("fromId");
            System.out.println("Got request to get message from  " + from_id);
            LinkedBlockingQueue getMessageQueue = data.get(fromId, from_id);
            getMessege(getMessageQueue, resp);
        }else {
            String ms = req.getParameter("msg");
            String to_id = req.getParameter("toId");
            Map<String, LinkedBlockingQueue<String>> d = data.getFriendsMap(from_id);
            System.out.println(d.toString());
            LinkedBlockingQueue<String> sendMessageQueue = data.get(from_id, to_id);
            try {
                sendMessageQueue.put(ms);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void getMessege(LinkedBlockingQueue getMessageQueue, HttpServletResponse resp) throws IOException {
        String ms = "";
        while(getMessageQueue.size() != 0){
            ms += (String) getMessageQueue.poll();
            ms += "|";
        }
        if(ms.equals("")) ms = "noMessege";
        resp.getWriter().write(ms);
    }
}