<%@ page import="database.DataAdministrator" %>
<%@ page import="javax.websocket.Session" %>
<%@ page import="client.User" %>
<%@ page import="java.sql.Statement" %>
<%@ page import="database.DBConnection" %>
<%@ page import="java.sql.ResultSet" %><%--
  Created by IntelliJ IDEA.
  User: jeose
  Date: 31/07/2020
  Time: 01:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    DataAdministrator da = (DataAdministrator)getServletConfig().getServletContext().getAttribute(DataAdministrator.AttributeName);
    User curr_user = (User) session.getAttribute("user");
    String description = null;
    String chosen;
%>
<html>
<head>
    <title>Find Match</title>
</head>
<body>
    <h1>find your match</h1>

    <%
        //String user_id = curr_user.getUserId();
        String command = "Select * from users ";
        Statement s = da.getBaseConnection().createStatement();
        ResultSet rs = s.executeQuery(command);
        rs.next();
        rs.next();
        rs.next();
        if(rs.next()){
            description = rs.getString(4);
            chosen = rs.getString(1);
        }
        out.print("<p>" + description + "</p>");
        //out.print("<button id = \"match\" type=\"button\" class=\"button\" onclick=\"location.href=''\">LOG IN</button>");
    %>

</body>
</html>
