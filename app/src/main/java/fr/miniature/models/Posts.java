package fr.miniature.models;

import java.util.ArrayList;

public final class Posts {
    private static Posts instance;
    ArrayList<Post> posts = new ArrayList<>();

public static Posts getInstance(){
    if(instance == null){
        instance = new Posts();
    }
    return instance;
}
public ArrayList<Post> getPosts() {
    return posts;
}
public void addPost(Post post){
posts.add(post);
}
}