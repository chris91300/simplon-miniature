package fr.miniature.models;

import java.util.ArrayList;

public class Comments {
   

    private static Comments instance;
    ArrayList<Comment> comments = new ArrayList<>();

public static Comments getInstance(){
    if(instance == null){
        instance = new Comments();
    }
    return instance;
}
public ArrayList<Comment> getComments() {
    return comments;
}
public void addComment(Comment comment){
comments.add(comment);
}
}

