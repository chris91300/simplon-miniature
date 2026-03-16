package fr.miniature.models;

import java.util.ArrayList;
import java.util.Collections;

public class Comments {
   

    private static Comments instance;
    ArrayList<Comment> comments = new ArrayList<>();

    public static Comments getInstance(){
        if(instance == null){
            instance = new Comments();
        }
        return instance;
    }

    public void addComment(Comment comment){
    comments.add(comment);
    System.out.println("commentaire ajouté");
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<Comment> getCommmentsFor(String postID){
        ArrayList<Comment> list = new ArrayList<>();
        for(Comment comment: comments){
            System.out.println(comment.getContent());
            System.out.println(comment.getPostID());
            if(comment.getPostID().equals(postID)){
                list.add(comment);
            }
        }

        Collections.reverse(list);
        return list;
    }

    
}

