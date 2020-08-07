package chat;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Map;

public class ChatServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("came to do post");
        String command = req.getParameter("command");
        ServletContext s = getServletContext();
        Map<String,HttpServletResponse> data = (Map<String, HttpServletResponse>) s.getAttribute("data");
        String from_id = (String) req.getSession().getAttribute("fromId");
        if(command.equals("create") && !data.containsKey(from_id)){
            System.out.println("Created new ChatServlet");
            data.put(from_id, resp);
        }
        else {
            String ms = req.getParameter("msg");
            String to_id = req.getParameter("toId");
            HttpServletResponse rs = data.get(to_id);
            resp.getWriter().write(from_id + " " + ms);
        }
    }
}
