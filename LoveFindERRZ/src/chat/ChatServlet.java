package chat;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

public class ChatServlet extends HttpServlet {
    @Override
    protected synchronized void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String command = req.getParameter("command");
        HttpSession curr_session = req.getSession();
        ServletContext s = getServletContext();
        HandleChat data = (HandleChat) s.getAttribute("chatCon");
        String from_id = (String) req.getSession().getAttribute("fromId");
        System.out.println("came to do " + command + " " + from_id);
        LinkedBlockingQueue<String> msgQueue = null;
        if(command.equals("create")){
            if(!data.containsId(from_id)) {
                System.out.println("Created new ChatServlet");
                Map<String, LinkedBlockingQueue<String>> msg = new HashMap<>();
                data.add(from_id, msg);
            }
        }else if(command.equals("get")){
            System.out.println("GOT GETMESSEGE REQUEST");
            String to_id = req.getParameter("toId");
            LinkedBlockingQueue getMessageQueue = data.get(to_id, from_id);
            GetMessege gm = new GetMessege(getMessageQueue, resp);
            gm.start();
            try {
                gm.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

    class GetMessege extends Thread{
        private LinkedBlockingQueue getMessageQueue;
        private HttpServletResponse resp;
        public GetMessege(LinkedBlockingQueue getMessageQueue, HttpServletResponse resp){
            this.getMessageQueue = getMessageQueue;
            this.resp = resp;
        }

        @Override
        public void run() {
            String ms = "";
            while(getMessageQueue.size() != 0){
                ms += (String) getMessageQueue.poll();
                ms += " ";
            }
            if(ms.equals("")) ms = "noMessege";
            try {
                resp.getWriter().write(ms);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}