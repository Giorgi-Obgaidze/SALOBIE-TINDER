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

<html>
<head>
    <title>Find Match</title>
    <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous">
    </script>
</head>
<body>
    <h1>find your match</h1>
    <p id = "description"> <% request.getAttribute("status"); %> </p>

    <script type="text/javascript" src = "findMatch.js"> </script>
    <button onclick="findNext()">Next</button>
</body>
</html>
