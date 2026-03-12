<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.lang.Error" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="fr.miniature.models.User"%>
<%@ page import="fr.miniature.models.Post"%>
<%@ page import="java.util.Map"%>

<%
Error error = (Error) request.getAttribute("error");
User user = (User) request.getAttribute("user");
Post post = (Post) request.getAttribute("post");
User author = (User) request.getAttribute("author");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/assets/style.css" />
    <title>Poster commentaire</title>
</head>
<body>
    <main>
        <h1>Poster un commentaire</h1>
        <p>user = <%=user.getFirstname()%></p>
        <p><%=post.getContent()%></p>
        <p>author = <%=author.getFirstname()%></p>
    </main>
</body>
</html>