package itst.social_raccoon_api.Services;

import itst.social_raccoon_api.Dto.FollowerDTO;
import itst.social_raccoon_api.Models.FollowerModel;
import itst.social_raccoon_api.Models.UserModel;
import itst.social_raccoon_api.Repositories.FollowerRepository;
import itst.social_raccoon_api.Repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class FollowerService {

    @Autowired
    private MappingService mappingService;

    @Autowired
    private UserService userService;

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private UserRepository userRepository;

    // Follow a user
    public void followUser(Integer userId, Integer followerId) {
        UserModel user = userService.findById(userId);
        UserModel follower = userService.findById(followerId);

        if (user != null && follower != null) {
            FollowerModel followerModel = new FollowerModel(user, follower);
            followerRepository.save(followerModel);
        }
    }

    // Unfollow a user
    public void unfollowUser(Integer userId, Integer followerId) {
        UserModel user = userService.findById(userId);
        UserModel follower = userService.findById(followerId);

        if (user != null && follower != null) {
            FollowerModel followerModel = new FollowerModel(user, follower);
            followerRepository.delete(followerModel);
        }
    }

    // Get users followed by the user
    public List<FollowerDTO> getFollowers(Integer userId) {
        List<FollowerModel> followers = followerRepository.getFollowersByUserId(userId);

        followers.forEach(follower -> {
            System.out.println("Follower: " + follower.getUser().getName());
        });

        return followers.stream()
                .map(follower -> mappingService.mapToDTO(follower, FollowerDTO.class))
                .collect(Collectors.toList());
    }

    public List<FollowerDTO> getFollowing(Integer userId) {
        return followerRepository.getFollowersByFollowerId(userId)
                .stream()
                .map(follower -> mappingService.mapToDTO(follower, FollowerDTO.class))
                .collect(Collectors.toList());
    }

}
