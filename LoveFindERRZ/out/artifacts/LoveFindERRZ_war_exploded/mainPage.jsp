<%--
  Created by IntelliJ IDEA.
  User: jeose
  Date: 16/07/2020
  Time: 18:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>LoveFindERRZ</title>
    <link rel="stylesheet" href = "styling.css">

</head>
<body>
    <div class="container">
        <h1 style="text-align:center">FIND. MEAT. FUCK.</h1>
        <div class="button-container">
            <div class="log-in">
                <button id = "login" type="button" class="button">LOG IN</button>
            </div>
            <div class="agree-terms">
                <input type = "checkbox" id = "agreeTerms" name="agreeTerms" value="agree">
                <label for="agreeTerms">By checking this box you agree our </label>
                <a href = "TermsAndConditions.jsp">Terms and Conditions</a>
            </div>
            <div class="sign-up">
                <button id= "signup" type="button" class = "button">SIGN UP</button>
            </div>

        </div>
    </div>
</body>
</html>
