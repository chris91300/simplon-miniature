package fr.miniature.models;

import java.util.Date;

abstract class Publication {
    private String id; 
    private String userID;
    private String content;
    private long createdAt; 

    public Publication(String userID,String content){
        this.userID = userID;
        this.content = content;
        this.id = generateID();
        this.createdAt = new Date().getTime();
    }

    protected String generateID(){
        int id = (int) (Math.random() * (1000000 - 1000 + 1) + 1000);
        return Integer.toString(id);
    }
}
