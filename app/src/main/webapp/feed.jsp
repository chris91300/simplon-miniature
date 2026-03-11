<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.lang.Error" %>
<%@ page import="fr.miniature.models.User"%>

<%
Error error = (Error) request.getAttribute("error");
User user = (User) request.getAttribute("user");
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <h1> coucou</h1>
    <p><%=user.getFirstname()%></p>
</body>
</html>