package itst.social_raccoon_api.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itst.social_raccoon_api.Dto.RelationshipDTO;
import itst.social_raccoon_api.Services.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("relationships")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
@Tag(name = "Relationships", description = "Provide methods to manage user relationships (follow/unfollow)")
public class RelationshipController {

    @Autowired
    private RelationshipService relationshipService;

    @PostMapping("/{userId}/{followerId}")
    @Operation(summary = "Follow a user", description = "Establish a following relationship between two users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User followed", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<String> followUser(@PathVariable Integer userId, @PathVariable Integer followerId) {
        relationshipService.followUser(userId, followerId);
        return new ResponseEntity<>("User followed", HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/{followerId}")
    @Operation(summary = "Unfollow a user", description = "Remove a following relationship between two users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User unfollowed", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<String> unfollowUser(@PathVariable Integer userId, @PathVariable Integer followerId) {
        relationshipService.unfollowUser(userId, followerId);
        return new ResponseEntity<>("User unfollowed", HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get followers and following of a user", description = "Get followers and following of a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Followers and following of the user", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = RelationshipDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
    })
    public ResponseEntity<RelationshipDTO> getFollowersAndFollowing(@PathVariable Integer userId) {
        try {
            RelationshipDTO relationshipDTO = relationshipService.getFollowersAndFollowing(userId);
            return new ResponseEntity<>(relationshipDTO, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}