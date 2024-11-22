package itst.socialraccoon.api.services;

import java.util.List;
import java.util.NoSuchElementException;

import itst.socialraccoon.api.models.PostModel;
import itst.socialraccoon.api.models.compositekeys.ReactionPK;
import itst.socialraccoon.api.repositories.ReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import itst.socialraccoon.api.models.ReactionModel;
import itst.socialraccoon.api.models.ReactionTypeModel;
import itst.socialraccoon.api.models.UserModel;
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
        if (postService.findById(postId) == null || userService.findById(userId) == null) {
            throw new NoSuchElementException("Post or user not found");
        }
        ReactionModel reaction = reactionRepository.getReactionByPostIdAndUserId(postId, userId);
        if (reaction != null) {
            reactionRepository.delete(reaction);
            return true;
        }
        throw new NoSuchElementException("Reaction not found");
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