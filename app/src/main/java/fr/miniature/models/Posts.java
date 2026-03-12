package fr.miniature.models;

import java.util.ArrayList;
import java.util.Collections;

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
        ArrayList<Post> smallPosts = getPosts(10);
        ArrayList<Post> postsInversed = new ArrayList<>(smallPosts);
        Collections.reverse(postsInversed);
        return postsInversed;
    }

    public ArrayList<Post> getPosts(int max){
        ArrayList<Post> postList = new ArrayList<>();
        int compteur = 0;

        for(Post post: posts){
            if(compteur < max){
                postList.add(post);
                compteur++;
            }else{
                break;
            }           
        }

        return postList;
    }


    public ArrayList<Post> addPost(Post post){
        posts.add(post);
        return getPosts();
        
    }


    public Post getPost(String id){
        Post currentPost = null;
        for(Post post: posts){
            if(post.getID().equals(id)){
                currentPost = post;
                break;
            }
        }

        return currentPost;
    }
}