package itst.social_raccoon_api.Services;

import itst.social_raccoon_api.Models.CompositeKeys.ReactionPK;
import itst.social_raccoon_api.Models.PostModel;
import itst.social_raccoon_api.Models.ReactionModel;
import itst.social_raccoon_api.Models.ReactionTypeModel;
import itst.social_raccoon_api.Models.UserModel;
import itst.social_raccoon_api.Repositories.ReactionRepository;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

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

    public List<ReactionModel> getReactionsByPostId(Integer postId) {
        return reactionRepository.getReactionsByPostId(postId);
    }

    public List<ReactionModel> getReactionsByUserId(int userId) {
        return reactionRepository.getReactionsByUserId(userId);
    }

    public Integer getReactionCountByPostId(int postId) {
        return reactionRepository.getReactionCountByPostId(postId);
    }

    public ReactionModel reactOrUpdate(Integer postId, Integer userId, Integer reactionTypeId) {
        PostModel post = postService.findById(postId);
        UserModel user = userService.findById(userId);
        ReactionTypeModel reactionType = reactionTypeService.getById(reactionTypeId);

        ReactionModel existingReaction = reactionRepository.getReactionByPostIdAndUserId(postId, userId);
        if (existingReaction != null) {
            existingReaction.setIdReactionType(reactionType);
            return reactionRepository.save(existingReaction);
        } else {
            ReactionModel newReaction = new ReactionModel(reactionType, post, user);
            return reactionRepository.save(newReaction);
        }
    }

    public boolean deleteReaction(Integer postId, Integer userId) {
        ReactionModel reaction = reactionRepository.getReactionByPostIdAndUserId(postId, userId);
        if (reaction != null) {
            reactionRepository.delete(reaction);
            return true;
        }
        return false;
    }

    public ReactionModel getById(ReactionPK reactionPK) {
        return reactionRepository.findById(reactionPK).get();
    }

    public List<ReactionModel> getAll() {
        return reactionRepository.findAll();
    }

    public ReactionModel update(Integer postId, Integer userId, Integer reactionTypeId) {
        ReactionModel reaction = reactionRepository.getReactionByPostIdAndUserId(postId, userId);
        reaction.setIdReactionType(reactionTypeService.getById(reactionTypeId));
        reactionRepository.save(reaction);
        return reaction;
    }

    public ReactionModel getReactionByPostIdAndUserId(int postId, int userId) {
        return reactionRepository.getReactionByPostIdAndUserId(postId, userId);
    }

    public Integer getReactionCountByPostIdAndReactionTypeId(int postId, int reactionTypeId) {
        return reactionRepository.getReactionCountByPostIdAndReactionType(postId, reactionTypeId);
    }

}
