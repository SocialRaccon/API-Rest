package itst.socialraccoon.api.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import itst.socialraccoon.api.annotations.GlobalApiResponses;
import itst.socialraccoon.api.dtos.PostDTO;
import itst.socialraccoon.api.dtos.PostRequestDTO;
import itst.socialraccoon.api.models.ImagePostModel;
import itst.socialraccoon.api.models.PostDescriptionModel;
import itst.socialraccoon.api.models.PostModel;
import itst.socialraccoon.api.models.UserModel;
import itst.socialraccoon.api.services.PostService;
import itst.socialraccoon.api.services.UserService;
import itst.socialraccoon.api.validators.handlers.ImageValidationHandler;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("posts")
@Validated
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Posts", description = "Provides methods to manage posts")
@GlobalApiResponses
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private ImageValidationHandler validator;

    @GetMapping("/{userId}")
    @Operation(summary = "Get posts by user ID with pagination",
            description = "Retrieves all posts associated with the specified user ID")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Posts successfully recovered",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @Schema(implementation = PostDTO.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Posts not found")
    })
    public ResponseEntity<List<PostDTO>> getPostsByUserId(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<PostModel> posts = postService.findByUser(userId, page, size);
        if (posts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<PostDTO> postDTOs = posts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(postDTOs);
    }

    @GetMapping("/feed")
    @Operation(summary = "Get posts feed",
            description = "Retrieves a paginated feed of posts, sorted by descending creation date")
    @ApiResponse(responseCode = "200", description = "Feed successfully recovered")
    public ResponseEntity<Page<PostDTO>> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostModel> postPage = postService.getFeed(pageable);
        Page<PostDTO> postDTOPage = postPage.map(this::convertToDTO);
        return ResponseEntity.ok(postDTOPage);
    }
    @GetMapping("/feed/career/{acronym}")
    @Operation(summary = "Get random posts feed by career acronym",
            description = "Retrieves a paginated random feed of posts for users in a specific career")
    @ApiResponse(responseCode = "200", description = "Random feed successfully recovered")
    public ResponseEntity<Page<PostDTO>> getRandomCareerFeed(
            @PathVariable String acronym,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostModel> postPage = postService.getRandomCareerFeed(acronym, pageable);
        Page<PostDTO> postDTOPage = postPage.map(this::convertToDTO);
        return ResponseEntity.ok(postDTOPage);
    }

    @GetMapping("/feed/{userId}")
    @Operation(summary = "Get posts feed by user ID",
            description = "Retrieves a paginated feed of posts according following users of the specified user ID")
    @ApiResponse(responseCode = "200", description = "Feed successfully recovered")
    public ResponseEntity<Page<PostDTO>> getFollowingFeed(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostModel> postPage = postService.getFollowingFeed(userId, pageable);
        Page<PostDTO> postDTOPage = postPage.map(this::convertToDTO);
        return ResponseEntity.ok(postDTOPage);
    }

    @PostMapping(value = "/withImage/{userId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(
            summary = "Create a post with an image",
            description = "Create a new post with an image attached"
    )
    public ResponseEntity<PostDTO> createPost(
            @RequestParam(value = "postDescription", required = false) String postDescription,
            @PathVariable Integer userId,
            @RequestParam("image") MultipartFile image) {
        validator.validateImage(image);
        PostRequestDTO postRequestDTO = new PostRequestDTO();
        postRequestDTO.setPostDescription(Objects.requireNonNullElse(postDescription, ""));
        postRequestDTO.setIdUser(userId);
        PostModel postModel = convertPostRequestToEntity(postRequestDTO);
        PostModel savedPost = postService.save(postModel, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedPost));
    }

    @PostMapping("/{userId}")
    @Operation(
            summary = "Create a post without an image",
            description = "Create a new post without an image attached"
    )
    public ResponseEntity<PostDTO> createPost(
            @RequestParam(value = "postDescription") String postDescription,
            @PathVariable Integer userId) {
        PostRequestDTO postRequestDTO = new PostRequestDTO();
        postRequestDTO.setPostDescription(postDescription);
        postRequestDTO.setIdUser(userId);
        PostModel postModel = convertPostRequestToEntity(postRequestDTO);
        PostModel savedPost = postService.save(postModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedPost));
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "Delete a post by user ID and post ID",
            description = "Deletes a post if it belongs to the specified user ID")
    @ApiResponse(responseCode = "200", description = "Post deleted successfully")
    @ApiResponse(responseCode = "404", description = "Post not found or does not belong to the user")
    public ResponseEntity<String> delete(
            @PathVariable() Integer postId) {
        postService.delete(postId);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @DeleteMapping(value = "/images/{postId}")
    @Operation(summary = "Delete an image from a post",
            description = "Deletes an image from a post if it belongs to the specified user ID")
    @ApiResponse(responseCode = "200", description = "Image deleted successfully")
    @ApiResponse(responseCode = "404", description = "Image or post not found or does not belong to the user")
    public ResponseEntity<String> deleteImageFromPost(
            @PathVariable Integer postId,
            @RequestParam Integer imageId) {
        postService.deleteImage(postId, imageId);
        return ResponseEntity.ok("Image deleted successfully");
    }

    @PostMapping(value = "/images/{postId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Add an image to a post",
            description = "Add an image to a post if it belongs to the specified user ID")
    @ApiResponse(responseCode = "200", description = "Image added successfully")
    @ApiResponse(responseCode = "404", description = "Post not found or does not belong to the user")
    public ResponseEntity<String> addImageToPost(
            @PathVariable Integer postId,
            @RequestParam("image") MultipartFile image) {
        validator.validateImage(image);
        postService.addImage(postId, image);
        return ResponseEntity.ok("Image added successfully");
    }

    @GetMapping("/images/{postId}")
    @Operation(summary = "Get images from a post",
            description = "Get all images from a post if it belongs to the specified user ID")
    @ApiResponse(responseCode = "200", description = "Images retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Post not found or does not belong to the user")
    public ResponseEntity<List<ImagePostModel>> getImagesFromPost(
            @PathVariable Integer postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<ImagePostModel> images = postService.getImages(postId, page, size);
        return ResponseEntity.ok(images);
    }

    @PutMapping(value = "/images/{postId}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Update an image from a post",
            description = "Update an image from a post if it belongs to the specified user ID")
    @ApiResponse(responseCode = "200", description = "Image updated successfully")
    @ApiResponse(responseCode = "404", description = "Image or post not found or does not belong to the user")
    public ResponseEntity<String> updateImageFromPost(
            @PathVariable Integer postId,
            @RequestParam Integer imageId,
            @RequestParam("image") MultipartFile image) {
        validator.validateImage(image);
        postService.update(postId, imageId, image);
        return ResponseEntity.ok("Image updated successfully");
    }

    @PutMapping("/{postId}")
    @Operation(summary = "Update description of a post",
            description = "Update the description of a post by its ID")
    @ApiResponse(responseCode = "200", description = "Post updated successfully")
    @ApiResponse(responseCode = "404", description = "Post not found")
    public ResponseEntity<PostDTO> update(@PathVariable Integer postId, @NotBlank @RequestParam("postDescription") String postDescription) {
        PostModel updatedPost = postService.update(postId, postDescription);
        return ResponseEntity.ok(convertToDTO(updatedPost));
    }

    private PostDTO convertToDTO(PostModel post) {
        PostDTO dto = modelMapper.map(post, PostDTO.class);
        return dto;
    }

    private PostModel convertPostRequestToEntity(PostRequestDTO postRequestDTO) {
        PostModel postModel = modelMapper.map(postRequestDTO, PostModel.class);
        UserModel user = userService.findById(postRequestDTO.getIdUser());
        if (user == null) {
            throw new NoSuchElementException("User not found");
        }
        postModel.setUser(user);
        PostDescriptionModel postDescriptionModel = new PostDescriptionModel();
        postDescriptionModel.setDescription(postRequestDTO.getPostDescription());
        postDescriptionModel.setIdPost(postModel);
        postModel.setIdPostDescription(postDescriptionModel);
        return postModel;
    }

}
