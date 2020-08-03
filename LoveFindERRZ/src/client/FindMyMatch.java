package client;

import database.DataAdministrator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class FindMyMatch extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        DataAdministrator da = (DataAdministrator) getServletContext().getAttribute(DataAdministrator.AttributeName);
        HttpSession curr_session = request.getSession();
        User user = (User) curr_session.getAttribute("user");
        try {
            String id = da.getNextMatch(user.getUserId());
            String status = da.getData("description", id);
            //request.setAttribute("status", status);
            String command = request.getParameter("command");
            if(command != null) {
                response.getWriter().write(status);
            }else {
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
