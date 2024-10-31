package itst.social_raccoon_api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itst.social_raccoon_api.dtos.RelationshipInfoDTO;
import itst.social_raccoon_api.services.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/{userId}/followers")
    @Operation(summary = "Get the followers", description = "Get the followers of a user through userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Followers found", content = @Content(schema = @Schema(implementation = RelationshipInfoDTO.class)),
                    headers = {@io.swagger.v3.oas.annotations.headers.Header(name = "X-Rate-Limit", description = "Rate limit for the user", required = true, schema = @Schema(type = "integer"))}),
            @ApiResponse(responseCode = "404", description = "Followers not found", content = @Content)
    })
    public ResponseEntity<List<RelationshipInfoDTO>> getFollowers(
            @PathVariable Integer userId,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        return new ResponseEntity<>(relationshipService.getFollowersByUserId(userId, page, size), HttpStatus.OK);
    }

    @GetMapping("/{userId}/following")
    @Operation(summary = "Get the following", description = "Get the following of a user through userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Following found", content = @Content(schema = @Schema(implementation = RelationshipInfoDTO.class)),
                    headers = {@io.swagger.v3.oas.annotations.headers.Header(name = "X-Rate-Limit", description = "Rate limit for the user", required = true, schema = @Schema(type = "integer"))}),
            @ApiResponse(responseCode = "404", description = "Following not found", content = @Content)
    })
    public ResponseEntity<List<RelationshipInfoDTO>> getFollowing(
            @PathVariable Integer userId,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        return new ResponseEntity<>(relationshipService.getFollowingByUserId(userId, page, size), HttpStatus.OK);
    }
}