package signUp;

import database.DataAdministrator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AccountCreation extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DataAdministrator administrator = (DataAdministrator)getServletContext().getAttribute(DataAdministrator.AttributeName);
        AccountManager manager = new AccountManager(administrator);
        String username = request.getParameter("username");
        String password = request.getParameter("pass");
        try {
            if(manager.accountExists(username)){
                RequestDispatcher dispatch = request.getRequestDispatcher("accountInUse.jsp");
                dispatch.forward(request, response);
            }else{
                manager.createAccount(username, password);
                request.setAttribute("currUserId", manager.getID());
                RequestDispatcher dispatch = request.getRequestDispatcher("card.jsp");
                dispatch.forward(request, response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
}
