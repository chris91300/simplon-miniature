<%@ page contentType="text/html;charset=UTF-8" language="java" %>
ArrayList<User> abonnements = user.getAbonnements();
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