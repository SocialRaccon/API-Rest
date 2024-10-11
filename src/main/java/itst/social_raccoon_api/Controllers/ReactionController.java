package itst.social_raccoon_api.Controllers;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

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

    @GetMapping()
    @Operation(summary = "Get all reactions", description = "Get all reactions from the database")
    public ResponseEntity<List<ReactionModel>> findAll() {
        List<ReactionModel> reactions = reactionService.findAll();
        return new ResponseEntity<>(reactions, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get reaction by id", description = "Get a reaction by id")
    public ResponseEntity<ReactionModel> findById(@PathVariable Integer id) {
        ReactionModel reaction = reactionService.findById(id);
        if (reaction == null) {
            throw new NoSuchElementException();
        }
        return new ResponseEntity<>(reaction, HttpStatus.OK);
    }

    @PostMapping()
    @Operation(
            summary = "Create a reaction",
            description = "Create a new reaction in the database",
            requestBody = @RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ReactionModel.class)
                    )
            )
    )
    public ResponseEntity<ReactionModel> create() {
        ReactionModel reaction = new ReactionModel();
        reaction.setDateCreated(new Timestamp(System.currentTimeMillis()));
        ReactionModel newReaction = reactionService.save(reaction);
        return new ResponseEntity<>(newReaction, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @Operation(
            summary = "Update a reaction",
            description = "Update a reaction in the database"
    )
    public ResponseEntity<ReactionModel> update(@PathVariable Integer id) {
        ReactionModel existingReaction = reactionService.findById(id);
        existingReaction.setDateCreated(new Timestamp(System.currentTimeMillis()));
        if (existingReaction == null) {
            throw new NoSuchElementException();
        }
        ReactionModel updatedReaction = reactionService.save(existingReaction);
        return new ResponseEntity<>(updatedReaction, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete a reaction", description = "Delete a reaction from the database")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Integer id) {
        reactionService.deleteById(id);
        Map<String, Boolean> response = Map.of("deleted", Boolean.TRUE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}