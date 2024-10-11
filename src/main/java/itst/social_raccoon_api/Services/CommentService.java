package itst.social_raccoon_api.Services;
import itst.social_raccoon_api.Models.CommentModel;
import itst.social_raccoon_api.Models.CompositeKeys.CommentPK;
import itst.social_raccoon_api.Models.FollowerModel;
import itst.social_raccoon_api.Repositories.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<CommentModel> findAll() {
        return commentRepository.findAll();
    }

    public CommentModel save(CommentModel comment) {
        return commentRepository.save(comment);
    }

    public CommentModel findById(CommentPK id) {
        return commentRepository.findById(id).orElse(null);
    }

    public void delete(CommentPK id) {
        commentRepository.deleteById(id);
    }

    public void deleteComment(Integer postId, Integer userId, Integer commentId) {
        commentRepository.deleteComment(postId, userId, commentId);
    }

    public List<CommentModel> getCommentsByPostId(Integer postId) {
        return commentRepository.getCommentsByPostId(postId);
    }

    public List<CommentModel> getCommentsByUserId(Integer userId) {
        return commentRepository.getCommentsByUserId(userId);
    }

    public List<CommentModel> getCommentsByPostIdAndUserId(Integer postId, Integer userId) {
        return commentRepository.getCommentsByPostIdAndUserId(postId, userId);
    }
}