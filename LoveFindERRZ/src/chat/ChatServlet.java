package chat;

import client.User;
import database.DataAdministrator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
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
        String from_id = (String) curr_session.getAttribute("fromId");
        System.out.println("came to do " + command + " " + from_id);
        Map<String, User> picData = (Map<String, User>) getServletContext().getAttribute("picData");
        LinkedBlockingQueue<String> msgQueue = null;
        DataAdministrator da = (DataAdministrator) getServletContext().getAttribute(DataAdministrator.AttributeName);
        if(command.equals("create")){
            if(!data.containsId(from_id)) {
                System.out.println("Created new ChatServlet");
                Map<String, LinkedBlockingQueue<String>> msg = new HashMap<>();
                data.add(from_id, msg);
            }
        }else if(command.equals("nextstep")){
            String frId = req.getParameter("frdId");
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
        }else if(command.equals("showpics")){
            String frId = req.getParameter("frdId");
            showPics(frId, picData, req, resp);
        }else if(command.equals("checkStatus")){
            String frId = req.getParameter("frdId");
            try {
                checkStatus(from_id, frId, da, resp);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
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

    private void checkStatus(String from_id, String frId, DataAdministrator da, HttpServletResponse resp) throws SQLException, IOException {
        String status = da.getStatus(from_id, frId);
        if(status.equals("nextstep")){
            resp.getWriter().write("true");
        }else{
            resp.getWriter().write("false");
        }
    }

    private void showPics(String frId, Map<String, User> picData, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//       System.out.println("requesting pictures");
//        User user = picData.get(frId);
////        //String desc = null;
////        try {
////            //desc = user.getDescription();
////        } catch (SQLException throwables) {
////            throwables.printStackTrace();
////        }
//        String image_src = getServletContext().getRealPath(user.getImageFolderPath());
//        System.out.println(image_src);
//        Map<String, String> images = new HashMap<>();
//        File dir = new File(image_src);
//        File[] directoryListing = dir.listFiles();
//        if (directoryListing != null) {
//            for (File child : directoryListing) {
//                images.put(child.getName(), user.getImageFolderPath() + "/" + child.getName());
//            }
//        } else {
//            System.out.println("boooooo");
//        }
//        req.setAttribute("images", images);
//        //req.setAttribute("description", desc);
//        RequestDispatcher dispatcher =  getServletContext().getRequestDispatcher("updateProfile.jsp");
//        dispatcher.forward(req, resp);
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