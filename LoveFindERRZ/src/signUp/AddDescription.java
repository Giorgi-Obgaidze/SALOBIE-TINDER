package signUp;

import database.DataAdministrator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class AddDescription extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        DataAdministrator administrator = (DataAdministrator)getServletContext().getAttribute(DataAdministrator.AttributeName);
        String description = request.getParameter("description");
        String userId = request.getParameter("userId");
        try {
            administrator.addCard(userId, description);
            if(request.getParameter("command") == null) {
                RequestDispatcher dispatch = request.getRequestDispatcher("imageUpload.jsp");
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
