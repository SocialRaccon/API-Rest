package itst.social.raccoon.api.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import itst.social.raccoon.api.dtos.RelationshipDTO;
import itst.social.raccoon.api.dtos.RelationshipInfoDTO;
import itst.social.raccoon.api.models.RelationshipModel;
import itst.social.raccoon.api.models.UserModel;
import itst.social.raccoon.api.repositories.RelationshipRepository;
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
    private RelationshipRepository relationshipRepository;

    public void followUser(Integer userId, Integer followerId) {
        UserModel user = userService.findById(userId);
        UserModel follower = userService.findById(followerId);

        if (user != null && follower != null) {
            RelationshipModel relationshipModel = new RelationshipModel(user, follower);
            relationshipRepository.save(relationshipModel);
        }
    }

    public void unfollowUser(Integer userId, Integer followerId) {
        UserModel user = userService.findById(userId);
        UserModel follower = userService.findById(followerId);

        if (user != null && follower != null) {
            RelationshipModel relationshipModel = new RelationshipModel(user, follower);
            relationshipRepository.delete(relationshipModel);
        }
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
            UserModel followedUser = relationshipModel.getUser();  // Corrección aquí
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