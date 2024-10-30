package itst.social_raccoon_api.Services;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import itst.social_raccoon_api.Models.CompositeKeys.ReactionPK;
import itst.social_raccoon_api.Models.PostModel;
import itst.social_raccoon_api.Models.ReactionModel;
import itst.social_raccoon_api.Models.ReactionTypeModel;
import itst.social_raccoon_api.Models.UserModel;
import itst.social_raccoon_api.Repositories.ReactionRepository;
import jakarta.transaction.Transactional;


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

    public List<ReactionModel> getReactionsByPostId(Integer postId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return reactionRepository.getReactionsByPostId(postId, pageRequest);
    }

    public List<ReactionModel> getReactionsByUserId(int userId) {
        return reactionRepository.getReactionsByUserId(userId);
    }

    public List<ReactionModel> getReactionsByUserId(int userId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return reactionRepository.getReactionsByUserId(userId, pageRequest);
    }

    public Integer getReactionCountByPostId(int postId) {
        if (postService.findById(postId) == null) {
            throw new NoSuchElementException();
        }
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

    public Page<ReactionModel> getAll(Pageable pageable) {
        return reactionRepository.findAll(pageable);
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

    public void deleteByUserId(UserModel user) {
        reactionRepository.deleteByIdUser(user);
    }

    public Integer getReactionCountByPostIdAndReactionTypeId(int postId, int reactionTypeId) {
        if (postService.findById(postId) == null) {
            throw new NoSuchElementException();
        }
        return reactionRepository.getReactionCountByPostIdAndReactionType(postId, reactionTypeId);
    }

}