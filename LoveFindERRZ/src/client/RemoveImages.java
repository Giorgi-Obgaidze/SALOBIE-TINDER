package client;

import database.DataAdministrator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;

public class RemoveImages extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        DataAdministrator da = (DataAdministrator)getServletContext().getAttribute(DataAdministrator.AttributeName);
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if(request.getParameter("deleteImage") != null){
            String image = request.getParameter("data");
            File file = new File(user.getImageFolderPath() + "/" + image);
            if(file.delete()){
                System.out.println("uraaa");
            }else {
                System.out.println("vaiiii");
            }
        }

    }
}
