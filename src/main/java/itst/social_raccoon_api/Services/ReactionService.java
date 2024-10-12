package itst.social_raccoon_api.Services;

import itst.social_raccoon_api.Models.CompositeKeys.ReactionPK;
import itst.social_raccoon_api.Models.PostModel;
import itst.social_raccoon_api.Models.ReactionModel;
import itst.social_raccoon_api.Models.ReactionTypeModel;
import itst.social_raccoon_api.Models.UserModel;
import itst.social_raccoon_api.Repositories.ReactionRepository;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReactionTypeService reactionTypeService;

    public List<ReactionModel> getReactionsByPostId(int postId) {
        return reactionRepository.getReactionsByPostId(postId);
    }

    public List<ReactionModel> getReactionsByUserId(int userId) {
        return reactionRepository.getReactionsByUserId(userId);
    }

    public Integer getReactionCountByPostId(int postId) {
        return reactionRepository.getReactionCountByPostId(postId);
    }

    public void save(ReactionModel reaction) {
        reactionRepository.save(reaction);
    }

    public void delete(ReactionModel reaction) {
        reactionRepository.delete(reaction);
    }

    public ReactionModel getById(ReactionPK reactionPK) {
        return reactionRepository.findById(reactionPK).get();
    }

    public List<ReactionModel> getAll() {
        return reactionRepository.findAll();
    }

    public void update(ReactionModel reaction) {
        reactionRepository.save(reaction);
    }

    public ReactionModel getReactionByPostIdAndUserId(int postId, int userId) {
        return reactionRepository.getReactionByPostIdAndUserId(postId, userId);
    }

    public List<ReactionModel> getReactionsByPostIdAndReactionType(int postId, int reactionType) {
        return reactionRepository.getReactionsByPostIdAndReactionType(postId, reactionType);
    }

    public List<ReactionModel> getReactionsByUserIdAndReactionType(int userId, int reactionType) {
        return reactionRepository.getReactionsByUserIdAndReactionType(userId, reactionType);
    }

}
