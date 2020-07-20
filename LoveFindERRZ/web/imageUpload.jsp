<%--
  Created by IntelliJ IDEA.
  User: jeose
  Date: 18/07/2020
  Time: 21:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload image</title>
    <link rel="stylesheet" href = "styling.css">

</head>
<body>
    <h1>Image preview on File Uploads</h1>
    <input type="file" name = "image" id="imageID">
    <div class="my_image" id = "myImage">
        <img src="" alt="loading image" class = "profile_image">
        <span class="default_image_message">Image ...</span>
    </div>

    <script type="text/javascript" src = "MyWebPage.js"></script>
</body>

</html>
