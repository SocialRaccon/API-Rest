package itst.social_raccoon_api.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itst.social_raccoon_api.Dto.FollowerDTO;
import itst.social_raccoon_api.Models.FollowerModel;
import itst.social_raccoon_api.Models.UserModel;
import itst.social_raccoon_api.Services.FollowerService;
import itst.social_raccoon_api.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "User", description = "Provide methods to manage users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FollowerService followerService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    @Operation(summary = "Get all users", description = "Get all users from the database")
    public ResponseEntity<List<UserModel>> findAll() {
        List<UserModel> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get user by id", description = "Get a user by its id")
    public ResponseEntity<UserModel> findById(@PathVariable Integer id) {
        UserModel user = userService.findById(id);
        if (user == null) {
            throw new NoSuchElementException();
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping()
    @Operation(
            summary = "Create a new user",
            description = "Create a new user in the database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserModel.class),
                            examples = @ExampleObject(
                                    name = "User Example",
                                    value = "{ \"name\": \"John\", \"lastName\": \"Doe\", \"secondLastName\": \"Smith\", \"email\": \"john.doe@example.com\", \"controlNumber\": \"123456\" }"
                            )
                    )
            )
    )
    public ResponseEntity<UserModel> create(@RequestBody UserModel user) {
        UserModel newUser = userService.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @Operation(
            summary = "Update a user",
            description = "Update a user in the database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserModel.class),
                            examples = @ExampleObject(
                                    name = "User Example",
                                    value = "{ \"idUser\": 1, \"name\": \"John\", \"lastName\": \"Doe\", \"secondLastName\": \"Smith\", \"email\": \"john.doe@example.com\", \"controlNumber\": \"123456\" }"
                            )
                    ))
    )
    public ResponseEntity<UserModel> update(@PathVariable Integer id, @RequestBody UserModel user) {
        UserModel existingUser = userService.findById(id);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        existingUser.setName(user.getName());
        existingUser.setLastName(user.getLastName());
        existingUser.setSecondLastName(user.getSecondLastName());
        existingUser.setEmail(user.getEmail());
        existingUser.setControlNumber(user.getControlNumber());
        UserModel updatedUser = userService.save(existingUser);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a user", description = "Delete a user from the database")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Integer id) {
        userService.deleteById(id);
        Map<String, Boolean> response = Map.of("deleted", Boolean.TRUE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Follow a user
    @PostMapping("/{userId}/follow/{followerId}")
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
    @DeleteMapping("/{userId}/unfollow/{followerId}")
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
    @GetMapping("/{userId}/followers")
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
    @GetMapping("/{userId}/following")
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
