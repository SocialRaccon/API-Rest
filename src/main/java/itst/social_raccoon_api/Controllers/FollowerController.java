package itst.social_raccoon_api.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itst.social_raccoon_api.Dto.FollowerDTO;
import itst.social_raccoon_api.Models.FollowerModel;
import itst.social_raccoon_api.Services.FollowerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("follower")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Follower", description = "Provide methods to manage followers")
public class FollowerController {

    @Autowired
    private FollowerService followerService;

    @Autowired
    private ModelMapper modelMapper;
    // Follow a user
    @PostMapping("/user/{userId}/follow/{followerId}")
    @Operation(summary = "Follow a user", description = "Follow a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User followed", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<String> followUser(@PathVariable Integer userId, @PathVariable Integer followerId) {
        followerService.followUser(userId, followerId);
        return new ResponseEntity<>("User followed", HttpStatus.OK);
    }

    // Unfollow a user
    @DeleteMapping("/user/{userId}/unfollow/{followerId}")
    @Operation(summary = "Unfollow a user", description = "Unfollow a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User unfollowed", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<String> unfollowUser(@PathVariable Integer userId, @PathVariable Integer followerId) {
        followerService.unfollowUser(userId, followerId);
        return new ResponseEntity<>("User unfollowed", HttpStatus.OK);
    }

    // Get followers of a user (returning FollowerDTO)
    @GetMapping("/user/{userId}/followers")
    @Operation(summary = "Get followers of a user", description = "Get followers of a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Followers of the user", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FollowerDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "No followers of the user", content = @Content)
    })
    public ResponseEntity<List<FollowerDTO>> getFollowers(@PathVariable Integer userId) {
        List<FollowerModel> followers = followerService.getFollowers(userId);
        if (followers.isEmpty()) {
            throw new NoSuchElementException();
        }
        List<FollowerDTO> followersDTO = followers.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(followersDTO, HttpStatus.OK);
    }

    // Get users followed by the user (returning FollowerDTO)
    @GetMapping("/user/{userId}/following")
    @Operation(summary = "Get users followed by the user", description = "Get users followed by the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users followed by the user", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = FollowerDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "No users followed by the user", content = @Content)
    })
    public ResponseEntity<List<FollowerDTO>> getFollowing(@PathVariable Integer userId) {
        List<FollowerModel> following = followerService.getFollowing(userId);
        if (following.isEmpty()) {
            throw new NoSuchElementException();
        }
        List<FollowerDTO> followingDTO = following.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(followingDTO, HttpStatus.OK);
    }

    private FollowerDTO convertToDTO(FollowerModel follower) {
        return modelMapper.map(follower, FollowerDTO.class);
    }
}
