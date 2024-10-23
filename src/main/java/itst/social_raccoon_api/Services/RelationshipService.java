package itst.social_raccoon_api.Services;

import itst.social_raccoon_api.Dto.RelationshipDTO;
import itst.social_raccoon_api.Dto.RelationshipInfoDTO;
import itst.social_raccoon_api.Models.RelationshipModel;
import itst.social_raccoon_api.Models.UserModel;
import itst.social_raccoon_api.Repositories.RelationshipRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
        List<RelationshipModel> following = relationshipRepository.getFollowersByFollowerId(userId);

        if (followers.isEmpty() && following.isEmpty()) {
            throw new NoSuchElementException("No followers or following found for the user with ID: " + userId);
        }

        RelationshipDTO relationshipDTO = new RelationshipDTO();
        relationshipDTO.setFollowers(followers.stream().map(this::convertToFollowerInfoDTO).collect(Collectors.toList()));
        relationshipDTO.setFollowing(following.stream().map(this::convertToFollowerInfoDTO).collect(Collectors.toList()));
        return relationshipDTO;
    }

    private RelationshipInfoDTO convertToFollowerInfoDTO(RelationshipModel relationshipModel) {
        // Suponiendo que quieres obtener la información del usuario que SIGUE al usuario actual
        UserModel followerUser = relationshipModel.getFollowerUser();
        return new RelationshipInfoDTO(followerUser.getIdUser(), followerUser.getName());
    }
}