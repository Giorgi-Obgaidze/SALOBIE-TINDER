package LogIn;

import database.DataAdministrator;
import signUp.AccountManager;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;


public class LogInServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        DataAdministrator da = (DataAdministrator)getServletContext().getAttribute(DataAdministrator.AttributeName);
        AccountManager am = new AccountManager(da);
        String username = request.getParameter("username");
        String password = request.getParameter("pass");
        try {
            boolean accountIsReal = am.accountExists(username);
            if(accountIsReal){
                boolean passwordIsCorrect = am.accessGranted(username, password);
                if(passwordIsCorrect){
                    RequestDispatcher dispatch = request.getRequestDispatcher("homepage.jsp");
                    dispatch.forward(request, response);
                }else{
                    System.out.println("bla");
                    RequestDispatcher dispatch = request.getRequestDispatcher("tryAgain.jsp");
                    dispatch.forward(request, response);
                }
            }else{
                System.out.println("blu");
                RequestDispatcher dispatch = request.getRequestDispatcher("tryAgain.jsp");
                dispatch.forward(request, response);
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