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
    <link rel="stylesheet" href = "imageUpload.css">
    <style>
        .close{
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }
        .close:hover,
        .close:focus {
            color: gray;
            text-decoration: none;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <h1>Image preview on File Uploads</h1>
    <from action = "uploadImages" method = "post">
        <input id="browse" type="file" onchange="imagePreview()" multiple>
        <div id="preview"></div>

        <script type="text/javascript" src = "MyWebPage.js"></script>
        <input type = "submit" name = "submit" value="Upload">
    </from>
</body>

</html>
