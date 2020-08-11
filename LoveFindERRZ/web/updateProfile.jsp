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
<%@ page import="java.util.HashMap" %>
<%
    HashMap<String, String> images = (HashMap<String, String>) request.getAttribute("images");
    String description = (String) request.getAttribute("description");
%>

<html>
<head>
    <link rel="stylesheet" href = "imageUpload.css">
    <title>Edit profile</title>
    <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous">
    </script>
</head>

<body>

    <input id="browse" type="file" onchange="imagePreview()" multiple>
    <%
        out.print("<div id=\"preview\">");
        for(String key : images.keySet()){
            System.out.println(key);
            String image_src = "../LoveFindERRZ_war_exploded" + images.get(key);
            out.print("<img class = \"image\" id = " + key.substring(0,6) + " alt=\"loading image ...\" src= " + image_src + ">");
            out.print("<button onclick=\"deleteImage('"+ key + "')\" id = \"clear"+ key.substring(5,6) +"\" class = \"clear\" >X</button>");
        }
        out.print("</div>");

        out.print("<div id=\"desc\">");
        out.print("<textarea class=\"myText\" id=\"info\">" + description + "</textarea>");
        out.print("</div>");
    %>
    <button onclick="submitChanges()" id = "submit_img" >APPLY ALL CHANGES</button>
</body>
<script type="text/javascript" src = "imagePage.js"></script>
</html>
