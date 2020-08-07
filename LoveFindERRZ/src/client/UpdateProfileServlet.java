package client;

import javax.servlet.RequestDispatcher;
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

public class UpdateProfileServlet extends HttpServlet {

    protected  void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession curr_session = request.getSession();
        User user = (User) curr_session.getAttribute("user");
        String desc = null;
        try {
            desc = user.getDescription();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String image_src = getServletContext().getRealPath(user.getImageFolderPath());
        System.out.println(image_src);
        Map<String, String> images = new HashMap<>();
        File dir = new File(image_src);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                images.put(child.getName(), user.getImageFolderPath() + "/" + child.getName());
            }
        } else {
            System.out.println("boooooo");
        }
        request.setAttribute("images", images);
        request.setAttribute("description", desc);
        RequestDispatcher dispatcher = request.getRequestDispatcher("updateProfile.jsp");
        dispatcher.forward(request, response);
    }
}
