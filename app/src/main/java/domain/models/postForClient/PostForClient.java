package domain.models.postForClient;

import java.util.List;

import domain.models.commentForClient.CommentClient;
import fr.miniature.models.Post;
import fr.miniature.models.User;


public record PostForClient(Post post, User author, List<CommentClient> comments ){};

