<%--
  Created by IntelliJ IDEA.
  User: jeose
  Date: 18/07/2020
  Time: 16:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Account Registration</title>
    <link rel="stylesheet" href = "styling.css">
</head>
<body>
    <h1>Create New Account</h1>

    <form action = "AccountCreation" method="post">
        <label for = "userName">USERNAME</label>
        <input type="text" id = "userName" name="username"><br style = "line-height:1;"><br>
        <label for = "pass">PASSWORD</label>
        <input type = "password" id = "pass" name = "pass">
        <input type = "submit" name = "submit" value="NEXT">
    </form>
</body>
</html>
