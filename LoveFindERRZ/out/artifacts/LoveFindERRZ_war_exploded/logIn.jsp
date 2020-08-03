<%--
  Created by IntelliJ IDEA.
  User: jeose
  Date: 18/07/2020
  Time: 16:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LOG IN</title>
</head>
<body>
<form action="LogInServlet" method = "post">
    USERNAME: <input type = "text" id = "userName" name = "username"><br style = "line-height:1;"><br>
    PASSWORD: <input type = "password" id = "pass" name = "pass">
    <input type = "submit" name = "submit" value = "Login">
</form><br>

<a href = "registerAccount.jsp">Create New Account</a>
</body>
</html>
