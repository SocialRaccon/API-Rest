package itst.socialraccoon.api.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import itst.socialraccoon.api.dtos.RelationshipDTO;
import itst.socialraccoon.api.dtos.RelationshipInfoDTO;
import itst.socialraccoon.api.models.RelationshipModel;
import itst.socialraccoon.api.models.UserModel;
import itst.socialraccoon.api.models.compositekeys.RelationshipPK;
import itst.socialraccoon.api.repositories.RelationshipRepository;
import itst.socialraccoon.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class RelationshipService {

    @Autowired
    @Lazy
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RelationshipRepository relationshipRepository;

    public void followUser(Integer userId, Integer followerId) {
        // Check if users exist
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        UserModel followerUser = userRepository.findById(followerId)
                .orElseThrow(() -> new NoSuchElementException("Follower user with id " + followerId + " not found"));

        // Create the composite key to check if the relationship already exists
        RelationshipPK relationshipPK = new RelationshipPK();
        relationshipPK.setUser(user);
        relationshipPK.setFollowerUser(followerUser);

        // Check if the relationship already exists
        if (relationshipRepository.existsById(relationshipPK)) {
            throw new IllegalStateException("User with id " + userId + " is already followed by user with id " + followerId);
        }

        // Create the relationship
        RelationshipModel relationship = new RelationshipModel(user, followerUser);
        relationshipRepository.save(relationship);
    }


    public void unfollowUser(Integer userId, Integer followerId) {
        // Check if users exist
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " not found"));
        UserModel followerUser = userRepository.findById(followerId)
                .orElseThrow(() -> new NoSuchElementException("Follower user with id " + followerId + " not found"));

        // Create the composite key to check if the relationship exists
        RelationshipPK relationshipPK = new RelationshipPK(user, followerUser);

        // Check if the relationship exists
        boolean isFollowing = relationshipRepository.existsById(relationshipPK);
        if (!isFollowing) {
            throw new NoSuchElementException("No relationship found for userId: " + userId + " and followerId: " + followerId);
        }

        // Retrieve the existing relationship and delete it
        RelationshipModel relationshipModel = relationshipRepository.findById(relationshipPK)
                .orElseThrow(() -> new NoSuchElementException("Relationship could not be found"));

        relationshipRepository.delete(relationshipModel);
    }

    public RelationshipDTO getFollowersAndFollowing(Integer userId) {
        List<RelationshipModel> followers = relationshipRepository.getFollowersByUserId(userId);
        List<RelationshipModel> following = relationshipRepository.getFollowingByUserId(userId);

        if (followers.isEmpty() && following.isEmpty()) {
            throw new NoSuchElementException("No followers or following found for the user with ID: " + userId);
        }

        RelationshipDTO relationshipDTO = new RelationshipDTO();
        relationshipDTO.setFollowers(followers.stream().map(this::convertToFollowerInfoDTO).collect(Collectors.toList()));
        relationshipDTO.setFollowing(following.stream().map(this::convertToFollowerInfoDTO).collect(Collectors.toList()));
        return relationshipDTO;
    }

    public List<RelationshipInfoDTO> getFollowersByUserId(Integer userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<RelationshipModel> followers = relationshipRepository.getFollowersByUserIdPaginated(userId, pageable);
        return followers.stream().map(relationshipModel -> {
            UserModel followedUser = relationshipModel.getUser();
            return new RelationshipInfoDTO(followedUser.getIdUser(), followedUser.getName());
        }).collect(Collectors.toList());
    }

    public List<RelationshipInfoDTO> getFollowingByUserId(Integer userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        List<RelationshipModel> following = relationshipRepository.getFollowingByUserIdPaginated(userId, pageable);
        return following.stream().map(relationshipModel -> {
            UserModel followedUser = relationshipModel.getFollowerUser();
            return new RelationshipInfoDTO(followedUser.getIdUser(), followedUser.getName());
        }).collect(Collectors.toList());
    }

    private RelationshipInfoDTO convertToFollowerInfoDTO(RelationshipModel relationshipModel) {
        UserModel followerUser = relationshipModel.getFollowerUser();
        return new RelationshipInfoDTO(followerUser.getIdUser(), followerUser.getName());
    }
}