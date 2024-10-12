package itst.social_raccoon_api.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itst.social_raccoon_api.Models.CommentModel;
import itst.social_raccoon_api.Models.PostModel;
import itst.social_raccoon_api.Models.UserModel;
import itst.social_raccoon_api.Services.CommentService;
import itst.social_raccoon_api.Services.PostService;
import itst.social_raccoon_api.Services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;

@RestController
@RequestMapping("comment")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Comment", description = "Provides methods to manage comments.")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;
    @Autowired
    private PostService postService;

    @PostMapping()
    @Operation(
            summary = "Create a comment",
            description = "Create a new comment in the database",
            requestBody = @RequestBody(
                    description = "Comment to be created",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CommentModel.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "Comment",
                                    value = "{\n" +
                                            "  \"comment\": \"This is a comment\",\n" +
                                            "  \"date\": \"2021-10-03T05:00:00.000+00:00\",\n" +
                                            "  \"user\": {\n" +
                                            "    \"idUser\": 1\n" +
                                            "  },\n" +
                                            "  \"post\": {\n" +
                                            "    \"idPost\": 1\n" +
                                            "  }\n" +
                                            "}"
                            )
                    )
            )
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "201",
                    description = "Comment created",
                    content = @Content(
                            schema = @Schema(implementation = CommentModel.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "Comment",
                                    value = "{\n" +
                                            "  \"idComment\": 1,\n" +
                                            "  \"comment\": \"This is a comment\",\n" +
                                            "  \"date\": \"2021-10-03T05:00:00.000+00:00\",\n" +
                                            "}"
                            )
                    )
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "400",
                    description = "User or post not found",
                    content = @Content
            )
    })
    public ResponseEntity<CommentModel> create(@org.springframework.web.bind.annotation.RequestBody CommentModel comment) {
        if (comment.getUser() == null || comment.getPost() == null) {
            throw new IllegalArgumentException("User and Post must not be null");
        }

        UserModel user = userService.findById(comment.getUser().getIdUser());
        PostModel post = postService.findById(comment.getPost().getIdPost());

        if (user == null || post == null) {
            throw new EntityNotFoundException("User or post not found");
        }

        CommentModel createdComment = commentService.save(comment);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @DeleteMapping({"/post/{postId}/user/{userId}/comment/{commentId}"})
    @Operation(summary = "Delete comment", description = "Delete a comment by its id")
    public ResponseEntity<Void> delete(@PathVariable Integer postId, @PathVariable Integer userId, @PathVariable Integer commentId) {
        commentService.deleteComment(postId, userId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/post/{postId}")
    @Operation(summary = "Get comments by post id", description = "Get all comments by post id")
    public ResponseEntity<List<CommentModel>> findByPostId(@PathVariable Integer postId) {
        List<CommentModel> comments = commentService.getCommentsByPostId(postId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping(value = "/post/{postId}/pagination", params = {"page", "pageSize"})
    @Operation(summary = "Get comments by post id with pagination", description = "Get all comments by post id with pagination")
    public ResponseEntity<List<CommentModel>> findByPostIdPaginated(@PathVariable Integer postId, @RequestParam(value = "page", defaultValue = "0", required = false) int page, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        List<CommentModel> comments = commentService.getCommentsByPostId(postId, page, pageSize);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get comments by user id", description = "Get all comments by user id")
    public ResponseEntity<List<CommentModel>> findByUserId(@PathVariable Integer userId) {
        List<CommentModel> comments = commentService.getCommentsByUserId(userId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{userId}/pagination", params = {"page", "pageSize"})
    @Operation(summary = "Get comments by user id with pagination", description = "Get all comments by user id with pagination")
    public ResponseEntity<List<CommentModel>> findByUserIdPaginated(@PathVariable Integer userId, @RequestParam(value = "page", defaultValue = "0", required = false) int page, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        List<CommentModel> comments = commentService.getCommentsByUserId(userId, page, pageSize);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}/user/{userId}")
    @Operation(summary = "Get comments by post id and user id", description = "Get all comments by post id and user id")
    public ResponseEntity<List<CommentModel>> findByPostIdAndUserId(@PathVariable Integer postId, @PathVariable Integer userId) {
        List<CommentModel> comments = commentService.getCommentsByPostIdAndUserId(postId, userId);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @GetMapping("/post/{postId}/user/{userId}/pagination")
    @Operation(summary = "Get comments by post id and user id with pagination", description = "Get all comments by post id and user id with pagination")
    public ResponseEntity<List<CommentModel>> findByPostIdAndUserIdPaginated(@PathVariable Integer postId, @PathVariable Integer userId, @RequestParam(value = "page", defaultValue = "0", required = false) int page, @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        List<CommentModel> comments = commentService.getCommentsByPostIdAndUserId(postId, userId, page, pageSize);
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

}
