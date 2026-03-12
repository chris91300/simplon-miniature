package fr.miniature.models;


import java.util.ArrayList;


public class Post extends Publication {
  
    private Boolean isDraft;
    private int like;
    ArrayList<Comment> comments = new ArrayList<>();
   
    public Post(String userID, String content) {
        super(userID, content);
        this.like = 0;
        this.isDraft = false;
    }

    public void addComment(Comment comment){
        comments.add(comment);
    }

    public ArrayList<Comment> getComments(){
        return comments;
    }

    public void islike(){
        like++;
    }


    public int getLike(){
        return like;
    }

  
}
