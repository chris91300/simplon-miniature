package fr.miniature.models;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User {
    private String firstname;
    private String lastname;
    private String password;
    private String pseudo;
    private int id;

    public User(String firstname, String lastname, String pseudo, String password){
        this.firstname = firstname;
        this.lastname = lastname;
        this.pseudo = pseudo;
        this.password = hashPassword( password);
        this.id = generateID();
    }

    
    public String getFirstname(){
        return this.firstname;
    }

    public String getLastname(){
        return this.lastname;
    }

    public String getPseudo(){
        return this.pseudo;
    }

    public boolean isGoodPassword(String mdp){
        return this.password.equals(hashPassword(mdp));
    }

    public int getId(){
        return  id;
    }


   private String hashPassword(String password) {
    System.out.println("on hash le password : "+password);
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedhash = digest.digest(
            password.getBytes(StandardCharsets.UTF_8));
            return new String(encodedhash, StandardCharsets.UTF_8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
       
    }


    private int generateID(){
        int id = (int) (Math.random() * (1000000 - 1000 + 1) + 1000);
        return id;
    }
}
