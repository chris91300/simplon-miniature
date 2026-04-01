package domain.models.publication;

import java.util.Date;

import domain.models.entities.EntityInterface;

public abstract class Publication implements EntityInterface {
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



    public String getID() {
        return id;
    }



    public String getUserID() {
        return userID;
    }



    public String getContent() {
        return content;
    }



    public long getCreatedAt() {
        return createdAt;
    }
}
