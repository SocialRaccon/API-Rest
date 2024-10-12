package itst.social_raccoon_api.Services;
import itst.social_raccoon_api.Models.CommentModel;
import itst.social_raccoon_api.Repositories.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
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

    //update
    public void update(CommentModel comment) {
        commentRepository.save(comment);
    }

    public void delete(Integer id) {
        commentRepository.deleteById(id);
    }

    public void deleteComment(Integer postId, Integer userId, Integer commentId) {
        commentRepository.deleteComment(postId, userId, commentId);
    }

    public List<CommentModel> getCommentsByPostId(Integer postId) {
        return commentRepository.getCommentsByPostId(postId);
    }

    public List<CommentModel> getCommentsByPostId(Integer postId,  int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return commentRepository.getCommentsByPostId(postId, pageRequest);

    }

    public List<CommentModel> getCommentsByUserId(Integer userId) {
        return commentRepository.getCommentsByUserId(userId);
    }

    public List<CommentModel> getCommentsByUserId(Integer userId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return commentRepository.getCommentsByUserId(userId, pageRequest);
    }
    public List<CommentModel> getCommentsByPostIdAndUserId(Integer postId, Integer userId) {
        return commentRepository.getCommentsByPostIdAndUserId(postId, userId);
    }

    public List<CommentModel> getCommentsByPostIdAndUserId(Integer postId, Integer userId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return commentRepository.getCommentsByPostIdAndUserId(postId, userId, pageRequest);
    }

    //getCommentByPostIdAndUserIdAndCommentId
    public CommentModel getCommentByPostIdAndUserIdAndCommentId(Integer postId, Integer userId, Integer commentId) {
        return commentRepository.getComment(postId, userId, commentId);
    }

}