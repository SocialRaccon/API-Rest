package itst.social_raccoon_api.Controllers;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import itst.social_raccoon_api.Dto.FollowerDTO;
import itst.social_raccoon_api.Dto.ReactionDTO;
import itst.social_raccoon_api.Models.PostModel;
import itst.social_raccoon_api.Models.ReactionTypeModel;
import itst.social_raccoon_api.Models.UserModel;
import itst.social_raccoon_api.Services.PostService;
import itst.social_raccoon_api.Services.ReactionTypeService;
import itst.social_raccoon_api.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import itst.social_raccoon_api.Models.ReactionModel;
import itst.social_raccoon_api.Services.ReactionService;

@RestController
@RequestMapping("reaction")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Reaction", description = "Provides methods to manage reactions.")
public class ReactionController {

    @Autowired
    private ReactionService reactionService;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;
    @Autowired
    private ReactionTypeService reactionTypeService;

    @Operation(summary = "Get all reactions")
    @GetMapping
    public List<ReactionDTO> getAll() {
        List<ReactionModel> reactions = reactionService.getAll();
        if (reactions.isEmpty()) {
            throw new NoSuchElementException();
        }
        List<ReactionDTO> reactionDTOs = reactions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return reactionDTOs;
    }

    @Operation(summary = "Get a reaction by its post id and user id")
    @GetMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<ReactionDTO> getById(@PathVariable Integer postId, @PathVariable Integer userId) {
        ReactionModel reaction = reactionService.getReactionByPostIdAndUserId(postId, userId);
        if (reaction == null) {
            throw new NoSuchElementException();
        }
        ReactionDTO reactionDTO = convertToDTO(reaction);
        return new ResponseEntity<>(reactionDTO, HttpStatus.OK);
    }

    @Operation(summary = "Get all reactions by post id")
    @GetMapping("/post/{postId}")
    public List<ReactionDTO> getReactionsByPostId(@PathVariable Integer postId) {
        List<ReactionModel> reactions = reactionService.getReactionsByPostId(postId);
        if (reactions.isEmpty()) {
            throw new NoSuchElementException();
        }
        List<ReactionDTO> reactionDTOs = reactions.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return reactionDTOs;
    }

    @Operation(summary = "Get all reactions by user id")
    @GetMapping("/user/{userId}")
    public List<ReactionDTO> getReactionsByUserId(@PathVariable Integer userId) {
        List<ReactionModel> reactions = reactionService.getReactionsByUserId(userId);
        if (reactions.isEmpty()) {
            throw new NoSuchElementException();
        }
        return reactions.stream().map(this::convertToDTO).toList();
    }

    @Operation(summary = "Get reaction count by post id")
    @GetMapping("/count/post/{postId}")
    public Integer getReactionCountByPostId(@PathVariable Integer postId) {
        return reactionService.getReactionCountByPostId(postId);
    }

    // React or update a reaction
    @PostMapping("/post/{postId}/user/{userId}/reaction/{reactionTypeId}")
    @Operation(summary = "React to a post or update an existing reaction.")
    public ResponseEntity<ReactionDTO> reactOrUpdate(
            @PathVariable Integer postId,
            @PathVariable Integer userId,
            @PathVariable Integer reactionTypeId) {
        ReactionModel reaction = reactionService.reactOrUpdate(postId, userId, reactionTypeId);
        ReactionDTO reactionDTO = convertToDTO(reaction);
        return new ResponseEntity<>(reactionDTO, HttpStatus.OK);
    }

    // Delete a reaction
    @DeleteMapping("/post/{postId}/user/{userId}")
    @Operation(summary = "Delete a user's reaction to a post.")
    public ResponseEntity<Map<String, Boolean>> deleteReaction(
            @PathVariable Integer postId,
            @PathVariable Integer userId) {
        boolean deleted = reactionService.deleteReaction(postId, userId);
        if (deleted) {
            return new ResponseEntity<>(Map.of("deleted", true), HttpStatus.OK);
        } else {
            throw new NoSuchElementException();
        }
    }

    private ReactionDTO convertToDTO(ReactionModel reaction) {
        return modelMapper.map(reaction, ReactionDTO.class);
    }
}