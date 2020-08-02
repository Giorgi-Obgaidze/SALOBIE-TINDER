package signUp;

import client.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Base64;
import org.json.*;

public class UploadImages extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        BufferedReader br = new BufferedReader((new InputStreamReader((request.getInputStream()))));
        User user = (User) request.getSession().getAttribute("user");
        String json = br.readLine();
        //System.out.println(json);

        JSONArray data = new JSONArray(json);
        for (int i = 0; i < data.length(); i++) {
            JSONObject ithImage = data.getJSONObject(i);
            String base64data = ithImage.get("elem").toString();
            String imageName = ithImage.get("name").toString();
            String imageTitle = ithImage.get("title").toString();
            System.out.println(imageName);
            System.out.println(imageTitle);
            int len = imageName.length(); //length of the image name
            String type = imageName.substring(len - 4, len);
            System.out.println(type);
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] decoded = decoder.decode(base64data);
            String relativeWebPath = user.getImageFolderPath() + "/" + imageTitle + type;
            String path = getServletContext().getRealPath(relativeWebPath);
            System.out.println(path);
            try (OutputStream out1 = new BufferedOutputStream(new FileOutputStream(path))) {
                out1.write(decoded);
                System.out.println(imageTitle + " saved");
            }
        }
    }
}
