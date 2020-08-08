package client;

import chat.HandleChat;
import database.DataAdministrator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

//import javax.websocket.Session;

public class FindMyMatch extends HttpServlet {


    @Override
    protected synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        DataAdministrator da = (DataAdministrator) getServletContext().getAttribute(DataAdministrator.AttributeName);
        HttpSession curr_session = request.getSession();
        User user = (User) curr_session.getAttribute("user");
        String id =  (String)curr_session.getAttribute("fromId");
        String matchCommand = request.getParameter("matchCommand");
        ServletContext s = getServletContext();
        HandleChat data = (HandleChat) s.getAttribute("chatCon");
        if(matchCommand != null){
            try {
                if(matchCommand.equals("friends")){
                    returnNewFriendsList(response, user,da, id , data);
                }else if(matchCommand.equals("totFriends")){
                    returnAllFriendsList(response, user,da);
                }else tryMatch(response, user, curr_session);
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }
        }
        else{
            try {
                String next_id = da.getNextMatch(user.getUserId());
                if(next_id == null){
                    response.getWriter().write("null");
                    return;
                }
                String status = da.getData("description", next_id);
                String nextCommand = request.getParameter("nextCommand");
                String username = da.getData("username", next_id);
                if (nextCommand != null) {
                    String prev_user = (String) curr_session.getAttribute("prev_user");
                    user.chooseFriends(prev_user, "reject");
                    curr_session.setAttribute("prev_user", next_id);
                    response.getWriter().write(username + ":" + status);
                }else {
                    curr_session.setAttribute("prev_user", next_id);
                    request.setAttribute("status", username + ":" + status);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("findMatch.jsp");
                    dispatcher.forward(request, response);
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void returnAllFriendsList(HttpServletResponse response, User user, DataAdministrator da) throws IOException, SQLException {
        List<String> friends = user.myFriends();
        if(friends == null) {
            response.getWriter().write("noFriend");
            return;
        }
        String s = "";
        for(String frId : friends){
            s += da.getData("username", frId);
            s+=" ";
            s+= frId;
            s+= " ";
        }
        response.getWriter().write(s);
    }

    private void returnNewFriendsList(HttpServletResponse response, User user, DataAdministrator da, String fromId, HandleChat data) throws IOException, SQLException {
        List<String> friends = user.myNewFriends();
        if(friends == null) {
            response.getWriter().write("noFriend");
            return;
        }
        String s = "";
        for(String frId : friends){
            LinkedBlockingQueue<String> msgFrom = new LinkedBlockingQueue<>();
            Map<String, LinkedBlockingQueue<String>> from = new HashMap<>();
            from.put(frId, msgFrom);
            data.add(fromId, from);
            s += da.getData("username", frId);
            s+=" ";
        }
        response.getWriter().write(s);
    }

    private void tryMatch(HttpServletResponse response, User user, HttpSession curr_session) throws SQLException, IOException {
        String prev_user = (String) curr_session.getAttribute("prev_user");
        int friendsOrFoe = user.chooseFriends(prev_user, "accept");
        if(friendsOrFoe == 1){
            response.getWriter().write("friends:" + user.getFriendUsername(prev_user));
        }else{
            response.getWriter().write("next");
        }
    }
}
