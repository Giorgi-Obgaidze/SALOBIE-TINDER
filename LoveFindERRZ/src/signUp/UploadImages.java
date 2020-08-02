package signUp;

import client.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;

public class UploadImages extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String image = request.getParameter("elem");
        String imageName = request.getParameter("name");
        String title = request.getParameter("title");
        int len = imageName.length(); //length of the image name
        String type = imageName.substring(len - 4, len);
        System.out.println(type);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decoded = decoder.decode(image);
        User user = (User) request.getSession().getAttribute("user");
        String relativeWebPath = "/IMAGES/images" + user.getUserId() + title + type;
        String path = getServletContext().getRealPath(relativeWebPath);
        System.out.println(path);
        OutputStream out1 = null;

        try {
            out1 = new BufferedOutputStream(new FileOutputStream(path));
            out1.write(decoded);
        } finally {
            if (out1 != null) {
                out1.close();
            }
        }
    }
}
