package itst.social_raccoon_api.Controllers;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import itst.social_raccoon_api.Dto.ReactionDTO;
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

    @Operation(summary = "Get all reactions")
    @GetMapping
    public List<ReactionDTO> getAll() {
        List<ReactionModel> reactions = reactionService.getAll();
        if (reactions.isEmpty()) {
            throw new NoSuchElementException();
        }
        return reactions.stream().map(this::convertToDTO).toList();
    }

    @Operation(summary = "Get a reaction by its post id and user id")
    @GetMapping("/post/{postId}/user/{userId}")
    public ResponseEntity<ReactionDTO> getById(@PathVariable int postId, @PathVariable int userId) {
        ReactionModel reaction = reactionService.getReactionByPostIdAndUserId(postId, userId);
        if (reaction == null) {
            throw new NoSuchElementException();
        }
        ReactionDTO reactionDTO = convertToDTO(reaction);
        return new ResponseEntity<>(reactionDTO, HttpStatus.OK);
    }

    @Operation(summary = "Get all reactions by post id")
    @GetMapping("/post/{postId}")
    public List<ReactionDTO> getReactionsByPostId(@PathVariable int postId) {
        List<ReactionModel> reactions = reactionService.getReactionsByPostId(postId);
        if (reactions.isEmpty()) {
            throw new NoSuchElementException();
        }
        return reactions.stream().map(this::convertToDTO).toList();
    }

    @Operation(summary = "Get all reactions by user id")
    @GetMapping("/user/{userId}")
    public List<ReactionDTO> getReactionsByUserId(@PathVariable int userId) {
        List<ReactionModel> reactions = reactionService.getReactionsByUserId(userId);
        if (reactions.isEmpty()) {
            throw new NoSuchElementException();
        }
        return reactions.stream().map(this::convertToDTO).toList();
    }

    @Operation(summary = "Get reaction count by post id")
    @GetMapping("/count/post/{postId}")
    public Integer getReactionCountByPostId(@PathVariable int postId) {
        return reactionService.getReactionCountByPostId(postId);
    }

    @Operation(summary = "Create a reaction")
    @PostMapping
    public ResponseEntity<ReactionDTO> save(@RequestBody ReactionModel reaction) {
        reactionService.save(reaction);
        ReactionDTO reactionDTO = convertToDTO(reaction);
        return new ResponseEntity<>(reactionDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a reaction")
    @PutMapping
    public ResponseEntity<ReactionDTO> update(@RequestBody ReactionModel reaction) {
        reactionService.update(reaction);
        ReactionDTO reactionDTO = convertToDTO(reaction);
        return new ResponseEntity<>(reactionDTO, HttpStatus.OK);
    }

    @Operation(summary = "Delete a reaction")
    @DeleteMapping
    public ResponseEntity<Map<String, Boolean>> delete(@RequestBody ReactionModel reaction) {
        reactionService.delete(reaction);
        return new ResponseEntity<>(Map.of("deleted", true), HttpStatus.OK);
    }

    private ReactionDTO convertToDTO(ReactionModel reaction) {
        return modelMapper.map(reaction, ReactionDTO.class);
    }
}