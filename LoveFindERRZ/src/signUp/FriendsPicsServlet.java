package signUp;

import client.User;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class FriendsPicsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, User> picData = (Map<String, User>) getServletContext().getAttribute("picData");
        String frId = req.getParameter("frdId");
        User user = picData.get(frId);
        String desc = null;
        try {
            desc = user.getDescription();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        String image_src = getServletContext().getRealPath(user.getImageFolderPath());
        Map<String, String> images = new HashMap<>();
        File dir = new File(image_src);
        File[] directoryListing = dir.listFiles();
        String s = "";
        if (directoryListing != null) {
            for (File child : directoryListing) {
               s += "../LoveFindERRZ_war_exploded" + user.getImageFolderPath() + "/" + child.getName();
               s += " ";
            }
        }
        if(s != "") resp.getWriter().write(s);
        else resp.getWriter().write("EMPTY");
    }
}
