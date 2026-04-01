package infrastructure.facade;

import java.util.ArrayList;
import java.util.List;

import domain.models.commentForClient.CommentClient;
import domain.models.postForClient.PostForClient;
import fr.miniature.models.Comment;
import fr.miniature.models.Post;
import fr.miniature.models.User;
import infrastructure.database.CommentRepository;
import infrastructure.database.PostRepository;
import infrastructure.database.UserRepository;

public class PostsFacade {
    private UserRepository users = UserRepository.getInstance();
    private PostRepository posts = PostRepository.getInstance();
    private CommentRepository comments = CommentRepository.getInstance();

    // filter est soit all soit un id de user
    public List<PostForClient> getPosts(String filter){
        // implementer la logique
        List<PostForClient> postsClient = new ArrayList<>();
        List<Post> postsList = null;
        if(filter == null || filter.equals("all")){
            postsList = posts.find();
        }else{
            postsList = posts.findFromID(filter);
        }
        

        for(Post post: postsList){
            User authorPost = users.findByID(post.getUserID());
            List<CommentClient> commentsClient = new ArrayList<>();

            for(Comment comment: comments.findFromID(post.getID())){
                User authorComment = users.findByID(comment.getUserID());
                CommentClient commentClient = new CommentClient(comment, authorComment);
                commentsClient.add(commentClient);
            }

            PostForClient postClient = new PostForClient(post, authorPost, commentsClient);
            postsClient.add(postClient);
        }

        return postsClient;

         // on récupère les author des posts et on évite les doublons
      /*   Map<String, User> listOfUser = new HashMap<>();
        for (Post post : postsList) {
            String userID = post.getUserID();
            User user = users.findByID(userID);
            listOfUser.put(userID, user);
        }

        // on récupère les author des commentaires et on évite les doublons
       Map<String, List<Comment>> listOfCommentsByPostID = new HashMap<>();
        for (Post post : postsList) {
            String postID = post.getID();
            if(!listOfCommentsByPostID.containsKey(postID)){
                List<Comment> commentList = comments.findFromID(postID);
                listOfCommentsByPostID.put(postID, commentList);
            }
            
        }*/
    }
}
