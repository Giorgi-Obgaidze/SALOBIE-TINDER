<%@ page import="database.DataAdministrator" %>
<%--<%@ page import="javax.websocket.Session" %>--%>
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
    <link rel="stylesheet" href = "findMatch.css">
    <script src="https://code.jquery.com/jquery-3.5.1.js" integrity="sha256-QWo7LDvxbWT2tbbQ97B53yJnYU3WhH/C8ycbRAkjPDc=" crossorigin="anonymous">
    </script>
</head>
<body>
<%--    ეს პოპაპია რა--%>
    <div id="myModal" class="modal">
        <div class="modal-content">
            <span class="close" onclick="closeFriendNot()">&times;</span>
            <p id ="matchMessage"></p>
        </div>
    </div>
    <div id="friendsPic" class="modal">
        <div class="modal-content" id="picList">
            <span class="close" onclick="closeFriendPic()">&times;</span>
        </div>
    </div>
    <div class="contentContainer">
        <div class="randomize">
            <h1>find your match</h1>
            <p id = "description"> <%= request.getAttribute("status") %> </p>
            <button onclick="findNext()">Next</button>
            <button onclick="matchPerson()">Match</button>
        </div>
        <div class="matchList" id="matchChat">

        </div>
        <div class="chat-popup" id="chatBox">
            <div class="chatName" id="chat_name">
                <h1 id="messageTo"></h1>
                <span class="close" onclick="chatClose()">&times;</span>
            </div>
            <div id="messageBox" class="message_box"></div>
            <textarea placeholder="Type message.." id="message" required></textarea>
            <button class="btn" id ="sndBtn" onclick="sendMessage(event)">Send</button>
            <button class="btn step" id="nextStpBtn" onclick="sendMessage(event)">Next Step</button>
        </div>
    </div>
    <form action="UpdateProfileServlet" method="post">
        <button type = "submit" >Update profile</button>
    </form>
</body>
<script type="text/javascript" src = "findMatch.js"> </script>
</html>
