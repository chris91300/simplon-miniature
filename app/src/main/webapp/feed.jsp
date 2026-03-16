<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.lang.Error" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="fr.miniature.models.User"%>
<%@ page import="fr.miniature.models.Post"%>
<%@ page import="fr.miniature.models.Comment"%>
<%@ page import="java.util.Map"%>

<%
Error error = (Error) request.getAttribute("error");

User user = (User) request.getAttribute("user");
ArrayList<Post> posts = (ArrayList<Post>) request.getAttribute("posts");
Map<String, User> authors = (Map<String, User>) request.getAttribute("users");
Map<String, ArrayList<Comment>> listOfCommentsByPostID = (Map<String, ArrayList<Comment>>) request.getAttribute("listOfCommentsByPostID");
ArrayList<User> abonnements = user.getAbonnements();

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
        <h2>Bonjour <%=user.getPseudo()%></h2>
        <form method="post" action="/login" id="deconnexion" class="as-button">
            <input type="hidden" name="action" value="deconnexion"/>
            <input type="submit" value="se déconnecter"/>
        </form>
        <section>

        
        <%
            if(abonnements.size() != 0){
                if(abonnements.size() > 1){%>
                    <a href="/feed?abonnement=all" title="voir uniquement ceux que je suis">
                        voir uniquement les post de ceux que je suis
                    </a>
                <%}

                for(User author: abonnements){
                    %>
                    <a href="/feed?abonnement=<%=author.getId()%>" title="voir les post de <%=author.getFullName()%>">
                        <span><%=author.getFullName()%></span> voir uniquement ces posts
                    </a>
                    
                <%}
            }
            
        %>

        </section>
        <section id="create_post">
            <form id="create_post" method="post">
                
                <textarea name="newPost" placeholder="créer un nouveau post ..."></textarea>
                <input type="hidden" name="action" value="creerpost">
                <input type="submit" value="envoyer" />
                <%
                    if(error != null){%>
                        <p class="error"><%=error.getMessage()%></p>
                    <%}
                %>
            </form>
        </section>
        <section>
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
                        String postID = post.getID();
                        String href = "/comments?postUserID="+post.getUserID()+"&postID="+postID;
                        ArrayList<Comment> comments = post.getComments();
                    %>
                    <p><%=author.getPseudo()%></p>
                    <p class="content"><%=post.getContent()%></p>
                    <form method="post" class="as-button button-style-none">
                        <input type="hidden" name="action" value="liker">
                        <input type="hidden" name="postID" value="<%=post.getID()%>">
                        <button type="submit" class="button-style-none">❤️<sup><%=post.getLike()%></sup></button>

                    </form>
                    <a href=<%=href%> title="ajouter un commentaire">
                        commenter
                    </a>

                    <%
                        if(comments.size() == 0){%>
                            <p>aucun commentaire</p>
                        <%}else{%>
                            <details>
                                <summary>voir les commentaires</summary>
                                <%
                                ArrayList<Comment> listOfComment = listOfCommentsByPostID.get(postID);
                                for(Comment comment: listOfComment){%>
                                    <comment>
                                        <p><%=comment.getContent()%></p>
                                    </comment>
                                <%}
                                %>
                            </details>
                        <%}
                    %>
                    
                 </article>
                <%
                }
            }
        %>
        
            
        </section>
    </main>
</body>
</html>