package itst.social_raccoon_api.services;

import itst.social_raccoon_api.models.CommentModel;
import itst.social_raccoon_api.repositories.CommentRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public CommentModel save(CommentModel comment) {
        return commentRepository.save(comment);
    }

    public void delete(Integer id) {
        commentRepository.deleteById(id);
    }

    public CommentModel findById(Integer id) {
        return commentRepository.findById(id).get();
    }

    public List<CommentModel> getCommentsByPostId(Integer postId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<CommentModel> commentPage = commentRepository.getCommentsByPostId(postId, pageRequest);
        return commentPage.getContent();
    }

    public List<CommentModel> getCommentsByUserId(Integer userId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<CommentModel> commentPage = commentRepository.getCommentsByUserId(userId, pageRequest);
        return commentPage.getContent();
    }

    public List<CommentModel> getCommentsByPostIdAndUserId(Integer postId, Integer userId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        Page<CommentModel> commentPage = commentRepository.getCommentsByPostIdAndUserId(postId, userId, pageRequest);
        return commentPage.getContent();
    }

    public CommentModel getCommentByPostIdAndUserIdAndCommentId(Integer postId, Integer userId, Integer commentId) {
        return commentRepository.getComment(postId, userId, commentId);
    }
}