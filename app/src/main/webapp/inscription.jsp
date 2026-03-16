<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.lang.Error" %>

<%
Error error = (Error) request.getAttribute("error");
%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/assets/style.css" />
    <title>inscription</title>
</head>
<body>

    <main>
        <section id="inscription">        
            <h1>INSCRIPTION</h1>
            <form method="post" class="form">
                <label for="firstname">votre prénom</label>
                <input type="text" id="firstname" name="firstname" placeholder="votre prénom" />

                <label for="lastname">votre nom</label>
                <input type="text" id="lastname" name="lastname" placeholder="votre nom"/>

                <label for="pseudo">votre pseudo</label>
                <input type="text" id="pseudo" name="pseudo" placeholder="votre pseudo"/>

                <label for="password">votre mot de passe</label>
                <input type="password" id="password" name="password" placeholder="mot de passe" />
                <input type="hidden" name="action" value="inscription"/>
                <input type="submit" value="s'inscrire" />

                <%
                    if(error != null){%>
                        <p class="error"><%=error.getMessage()%></p>
                    <%}
                %>
            </form>
        </section>
    </main>
    
</body>
</html>