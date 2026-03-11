package fr.miniature.models;


import java.util.ArrayList;


public class Post extends Publication {
  
    private Boolean isDraft;
    private ArrayList<String> likes;
   
    public Post(String userID, String content) {
        super(userID, content);
        this.likes = new ArrayList<>();
        this.isDraft = false;
    }

  
}
