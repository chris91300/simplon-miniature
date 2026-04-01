package domain.models.commentForClient;

import fr.miniature.models.Comment;
import fr.miniature.models.User;


public record CommentClient(Comment comment, User author){};


