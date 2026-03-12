<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.lang.Error" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="fr.miniature.models.User"%>
<%@ page import="fr.miniature.models.Post"%>
<%@ page import="java.util.Map"%>

<%
Error error = (Error) request.getAttribute("error");

User user = (User) request.getAttribute("user");
ArrayList<Post> posts = (ArrayList<Post>) request.getAttribute("posts");
Map<String, User> authors = (Map<String, User>) request.getAttribute("users");
%>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/assets/style.css" />
    <title>Document</title>
</head>
<body>
    <main>
        <h1>Votre réseau Miniature</h1>

        <form id="create_post" method="post">
            <p><%=user.getPseudo()%></p>
            <textarea name="newPost" placeholder="créer un nouveau post ..."></textarea>
            <input type="hidden" name="action" value="creerpost">
            <input type="submit" value="envoyer" />
            <%
                if(error != null){%>
                    <p class="error"><%=error.getMessage()%></p>
                <%}
            %>
        </form>

        <%
            if(posts.size() == 0){
            %>
            <p>aucun post pour le moment</p>
            <%
            }else{
                for(Post post: posts){
                 %>
                 <article>
                    <%
                        String postUserID = post.getUserID();
                        User author = authors.get(postUserID);
                        String href = "/comments?postUserID="+post.getUserID()+"&postID="+post.getID();
                        
                    %>
                    <p><%=author.getPseudo()%></p>
                    <p><%=post.getContent()%></p>
                    <form method="post">
                        <input type="hidden" name="action" value="liker">
                        <input type="hidden" name="postID" value="<%=post.getID()%>">
                        <button type="submit">❤️<sup><%=post.getLike()%></sup></button>

                    </form>
                    <a href=<%=href%> title="ajouter un commentaire">
                        commenter
                    </a>
                 </article>
                <%
                }
            }
        %>
    </main>
</body>
</html>