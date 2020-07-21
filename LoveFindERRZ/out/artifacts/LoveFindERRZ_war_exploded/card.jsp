<%--
  Created by IntelliJ IDEA.
  User: jeose
  Date: 18/07/2020
  Time: 19:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>და მაინც ვინ ხარ შენ ადამიანო?</title>
    <link rel="stylesheet" href = "card.css">
</head>
<body>
<div id="myModal" class="modal">
    <div class="modal-content">
        <span class="close">&times;</span>
        <p>რეგისტრაციის ამ ეტაპზე თქვენ უნდა შეავსოთ პირადი ბარათი, რომელზეც ხაზს გაუსვამთ თქვენს ინტერესებს, საქმიანობას და ა.შ მაგალითად:</p>
        <textarea class="cardText" id="sampleCard" disabled="true"></textarea>
    </div>
</div>

<form class="cardForm">
    <p>შეავსეთ თქვენი ბარათი მაქსიმუმ 50 სიტყვით</p>
    <p id="countWord">სიტყვების რაოდენობა თქვენს ბარათში - 0</p>
    <textarea class="cardText" onkeypress="updateWordCount(event)" id="cardTextArea"></textarea>
    <button class="cardSubmit"></button>
</form>

</body>
<script src="card.js"></script>
</html>
