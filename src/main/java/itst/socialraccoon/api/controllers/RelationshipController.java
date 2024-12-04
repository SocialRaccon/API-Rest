package itst.socialraccoon.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itst.socialraccoon.api.annotations.GlobalApiResponses;
import itst.socialraccoon.api.dtos.RelationshipInfoDTO;
import itst.socialraccoon.api.services.RelationshipService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("relationships")
@Validated
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
@Tag(name = "Relationships", description = "Provides methods to manage relationships between users")
@GlobalApiResponses
public class RelationshipController {

    /*@Autowired
    private RelationshipService relationshipService;

    @PostMapping("/{userId}")
    @Operation(summary = "Follow a user", description = "Create a following relationship between two users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User followed", content = @Content),
            @ApiResponse(responseCode = "404", description = "User not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "User already followed", content = @Content)
    })
    public ResponseEntity<String> followUser(
            @PathVariable @NotNull @Positive(message = "User id must be positive") Integer userId,
            @RequestParam @NotNull @Positive(message = "User id must be positive") Integer followerId) {
        relationshipService.followUser(userId, followerId);
        return ResponseEntity.status(HttpStatus.CREATED).body("User followed");
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Unfollow a user", description = "Delete a following relationship between two users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User unfollowed", content = @Content),
            @ApiResponse(responseCode = "404", description = "User or relationship not found", content = @Content)
    })
    public ResponseEntity<String> unfollowUser(
            @PathVariable @NotNull @Positive(message = "User id must be positive") Integer userId,
            @RequestParam @NotNull @Positive(message = "User id must be positive") Integer followerId) {
        relationshipService.unfollowUser(userId, followerId);
        return ResponseEntity.ok("User unfollowed");
    }

    @GetMapping("/followers/{userId}")
    @Operation(summary = "Get the followers", description = "Get the followers of a user through userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Followers found", content = @Content(schema = @Schema(implementation = RelationshipInfoDTO.class)),
                    headers = {@io.swagger.v3.oas.annotations.headers.Header(name = "X-Rate-Limit", description = "Rate limit for the user", required = true, schema = @Schema(type = "integer"))}),
            @ApiResponse(responseCode = "404", description = "Followers not found", content = @Content)
    })
    public ResponseEntity<List<RelationshipInfoDTO>> getFollowers(
            @PathVariable @Positive(message = "User id must be positive") Integer userId,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        return new ResponseEntity<>(relationshipService.getFollowersByUserId(userId, page, size), HttpStatus.OK);
    }

    @GetMapping("/following/{userId}")
    @Operation(summary = "Get the following", description = "Get the following of a user through userId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Following found", content = @Content(schema = @Schema(implementation = RelationshipInfoDTO.class)),
                    headers = {@io.swagger.v3.oas.annotations.headers.Header(name = "X-Rate-Limit", description = "Rate limit for the user", required = true, schema = @Schema(type = "integer"))}),
            @ApiResponse(responseCode = "404", description = "Following not found", content = @Content)
    })
    public ResponseEntity<List<RelationshipInfoDTO>> getFollowing(
            @PathVariable @Positive(message = "User id must be positive") Integer userId,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ) {
        return new ResponseEntity<>(relationshipService.getFollowingByUserId(userId, page, size), HttpStatus.OK);
    }*/
}