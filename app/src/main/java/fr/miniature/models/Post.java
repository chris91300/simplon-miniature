package fr.miniature.models;


import java.util.ArrayList;


public class Post extends Publication {
  
    private Boolean isDraft;
    private int like;
   
    public Post(String userID, String content) {
        super(userID, content);
        this.like = 0;
        this.isDraft = false;
    }


    public void islike(){
        like++;
    }


    public int getLike(){
        return like;
    }

  
}
