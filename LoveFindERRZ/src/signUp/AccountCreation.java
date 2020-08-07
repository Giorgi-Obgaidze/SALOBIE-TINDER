package signUp;

import chat.ChatServlet;
import client.User;
import database.DataAdministrator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AccountCreation extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataAdministrator administrator = (DataAdministrator)getServletContext().getAttribute(DataAdministrator.AttributeName);
        AccountManager manager = new AccountManager(administrator);
        String username = request.getParameter("username");
        String password = request.getParameter("pass");
        User user = null;
        try {
            if(manager.accountExists(username)){
                RequestDispatcher dispatch = request.getRequestDispatcher("accountInUse.jsp");
                dispatch.forward(request, response);
            }else{


                String relativeWebPath = "/IMAGES/images";
                manager.createAccount(username, password, relativeWebPath);
                relativeWebPath += manager.getID();
                String absoluteDiskPath = getServletContext().getRealPath(relativeWebPath);
                File file = new File(absoluteDiskPath);
                //System.out.println(absoluteDiskPath);
                boolean bool = file.mkdir();
                if(bool){
                    System.out.println("Directory created successfully");
                }else{
                    System.out.println("Sorry couldn’t create specified directory");
                }
                user = new User(manager.getID(), administrator);
                request.getSession().setAttribute("user", user);
                request.setAttribute("currUserId", manager.getID());
                RequestDispatcher dispatch = request.getRequestDispatcher("card.jsp");
                dispatch.forward(request, response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String userId = user.getUserId();
        request.getSession().setAttribute("fromId", userId);
    }
}
