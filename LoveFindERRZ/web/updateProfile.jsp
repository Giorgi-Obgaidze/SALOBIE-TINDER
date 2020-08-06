<%--
  Created by IntelliJ IDEA.
  User: jeose
  Date: 05/08/2020
  Time: 15:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import = "java.io.File"%>
<%@ page import="client.User" %>
<%
    HttpSession curr_session = request.getSession();
    User user = (User) curr_session.getAttribute("user");
    String image_src = request.getServletContext().getRealPath(user.getImageFolderPath());
%>

<html>
<head>
    <title>Edit profile</title>
</head>
<body>
    <%
        File dir = new File(image_src);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                child.getName();

            }
        } else {

        }
    %>
</body>
</html>
