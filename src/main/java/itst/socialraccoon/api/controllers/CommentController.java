package itst.socialraccoon.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itst.socialraccoon.api.services.CommentService;
import itst.socialraccoon.api.dtos.CommentDTO;
import itst.socialraccoon.api.models.CommentModel;
import itst.socialraccoon.api.models.PostModel;
import itst.socialraccoon.api.models.UserModel;
import itst.socialraccoon.api.services.PostService;
import itst.socialraccoon.api.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("comments")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Comments", description = "Provides methods to manage comments")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;
    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/post/{postId}")
    public ResponseEntity<CommentDTO> create(
            @PathVariable Integer postId,
            @org.springframework.web.bind.annotation.RequestBody CommentModel comment) {
        if (comment.getUser() == null) {
            throw new IllegalArgumentException("User must not be null");
        }

        UserModel user = userService.findById(comment.getUser().getIdUser());
        PostModel post = postService.findById(postId);

        if (user == null || post == null) {
            throw new EntityNotFoundException("User or Post not found");
        }

        comment.setPost(post);
        comment.setDate(LocalDateTime.now()); // Auto-generate date
        CommentModel createdComment = commentService.save(comment);
        return new ResponseEntity<>(convertToDto(createdComment), HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> delete(
            @PathVariable Integer commentId) {
        try {
            commentService.findById(commentId); // Verificar si existe
            commentService.delete(commentId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Comment not found");
        }
    }

    @GetMapping(value = "/post/{postId}", params = {"page", "pageSize"})
    public ResponseEntity<?> findByPostId(
            @PathVariable Integer postId,
            @RequestParam(required = false) Integer userId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        List<CommentModel> comments;
        try {
            if (userId != null) {
                comments = commentService.getCommentsByPostIdAndUserId(postId, userId, page, pageSize);
            } else {
                comments = commentService.getCommentsByPostId(postId, page, pageSize);
            }

            if (comments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No comments found for the given criteria.");
            }

            return new ResponseEntity<>(comments.stream().map(this::convertToDto).toList(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error retrieving comments: " + e.getMessage());
        }
    }

    @GetMapping(value = "/user/{userId}", params = {"page", "pageSize"})
    public ResponseEntity<?> findByUserId(
            @PathVariable Integer userId,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        try {
            List<CommentModel> comments = commentService.getCommentsByUserId(userId, page, pageSize);

            if (comments.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No comments found for the given user.");
            }

            return new ResponseEntity<>(comments.stream().map(this::convertToDto).toList(), HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error retrieving comments: " + e.getMessage());
        }
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> update(
            @PathVariable Integer commentId,
            @org.springframework.web.bind.annotation.RequestBody CommentModel comment) {
        try {
            CommentModel commentToUpdate = commentService.findById(commentId);

            commentToUpdate.setComment(comment.getComment());
            commentToUpdate.setDate(LocalDateTime.now()); // Auto-generate date

            CommentModel updatedComment = commentService.save(commentToUpdate);
            return new ResponseEntity<>(convertToDto(updatedComment), HttpStatus.OK);
        } catch (NoSuchElementException e) {
            throw new EntityNotFoundException("Comment not found with id: " + commentId);
        }
    }

    public CommentDTO convertToDto(CommentModel comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }
}