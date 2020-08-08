package client;

import chat.HandleChat;
import database.DataAdministrator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//import javax.websocket.Session;

public class FindMyMatch extends HttpServlet {


    @Override
    protected synchronized void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        DataAdministrator da = (DataAdministrator) getServletContext().getAttribute(DataAdministrator.AttributeName);
        HttpSession curr_session = request.getSession();
        User user = (User) curr_session.getAttribute("user");
        String id = (String) curr_session.getAttribute("fromId");
        String matchCommand = request.getParameter("matchCommand");
        ServletContext s = getServletContext();
        HandleChat data = (HandleChat) s.getAttribute("chatCon");

        if (matchCommand == null) {
            System.out.println("noMatchCommand");
            String friendCommand = request.getParameter("friendCommand");
            try {
                if(friendCommand != null){
                    if (friendCommand.equals("friends")) {
                        returnNewFriendsList(response, user, da, id, data);
                    } else if (friendCommand.equals("totFriends")) {
                        returnAllFriendsList(response, user, da);
                    }
                }else {
                    String next_id = da.getNextMatch(user.getUserId());
                    String status = da.getData("description", next_id);
                    String username = da.getData("username", next_id);
                    curr_session.setAttribute("prev_user", next_id);
                    response.getWriter().write(username + ":" + status);
                }
            } catch (SQLException throwables) {
            throwables.printStackTrace();
            }
        } else {
            try {if (matchCommand.equals("next")) {
                    String prev_user = (String) curr_session.getAttribute("prev_user");
                    user.chooseFriends(prev_user, "reject");
                } else if (matchCommand.equals("match")) {
                    String prev_user = (String) curr_session.getAttribute("prev_user");
                    user.chooseFriends(prev_user, "accept");
                }
                String next_id = da.getNextMatch(user.getUserId());
                if (next_id == null) {
                    response.getWriter().write("null");
                    return;
                }
                String status = da.getData("description", next_id);
                String username = da.getData("username", next_id);
                curr_session.setAttribute("prev_user", next_id);
                response.getWriter().write(username + ":" + status);
            } catch (SQLException | IOException throwables) {
                throwables.printStackTrace();
            }

        }
    }

    private void returnAllFriendsList(HttpServletResponse response, User user, DataAdministrator da) throws IOException, SQLException {
        List<String> friends = user.myFriends();
        if (friends == null) {
            response.getWriter().write("noFriend");
            return;
        }
        String s = "";
        for (String frId : friends) {
            s += da.getData("username", frId);
            s += " ";
            s += frId;
            s += " ";
        }
        response.getWriter().write(s);
    }

    private void returnNewFriendsList(HttpServletResponse response, User user, DataAdministrator da, String fromId, HandleChat data) throws IOException, SQLException {
        List<String> friends = user.myNewFriends();
        if (friends == null) {
            response.getWriter().write("noFriend");
            return;
        }
        String s = "";
        for (String frId : friends) {
            data.addQueue(fromId,frId);
            data.addQueue(frId,fromId);
            s += da.getData("username", frId);
            s += " ";
            s+= frId;
            s+= " ";
        }
        response.getWriter().write(s);
    }

    private void tryMatch(HttpServletResponse response, User user, HttpSession curr_session) throws SQLException, IOException {
        String prev_user = (String) curr_session.getAttribute("prev_user");
        int friendsOrFoe = user.chooseFriends(prev_user, "accept");
        if (friendsOrFoe == 1) {
            response.getWriter().write("friends:" + user.getFriendUsername(prev_user));
        } else {
            response.getWriter().write("next");
        }
    }
}
