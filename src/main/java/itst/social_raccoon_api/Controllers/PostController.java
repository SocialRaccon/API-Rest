package itst.social_raccoon_api.Controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import itst.social_raccoon_api.Dto.PostRequestDTO;
import itst.social_raccoon_api.Models.ImagePostModel;
import itst.social_raccoon_api.Models.PostDescriptionModel;
import itst.social_raccoon_api.Models.UserModel;
import itst.social_raccoon_api.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import itst.social_raccoon_api.Dto.PostDTO;
import itst.social_raccoon_api.Models.PostModel;
import itst.social_raccoon_api.Services.PostService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("posts")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Posts", description = "Provide methods to manage posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get posts by user ID with pagination",
            description = "Retrieves all posts associated with the specified user ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved posts")
    @ApiResponse(responseCode = "404", description = "No posts found for the user")
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

    @PostMapping(value = "/withImage", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(
            summary = "Create a post with an image",
            description = "Create a new post with an image attached"
    )
    public ResponseEntity<PostDTO> createPost(
            @RequestParam("postDescription") String postDescription,
            @RequestParam("userId") Integer userId,
            @RequestParam("image") MultipartFile image) {
        PostRequestDTO postRequestDTO = new PostRequestDTO();
        postRequestDTO.setPostDescription(postDescription);
        postRequestDTO.setIdUser(userId);
        PostModel postModel = convertPostRequestToEntity(postRequestDTO);
        PostModel savedPost = postService.save(postModel, image);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedPost));
    }

    @PostMapping()
    @Operation(
            summary = "Create a post without an image",
            description = "Create a new post without an image attached"
    )
    public ResponseEntity<PostDTO> createPost(
            @RequestParam("postDescription") String postDescription,
            @RequestParam("userId") Integer userId) {
        PostRequestDTO postRequestDTO = new PostRequestDTO();
        postRequestDTO.setPostDescription(postDescription);
        postRequestDTO.setIdUser(userId);
        PostModel postModel = convertPostRequestToEntity(postRequestDTO);
        PostModel savedPost = postService.save(postModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedPost));
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete a post by user ID and post ID",
            description = "Deletes a post if it belongs to the specified user ID")
    @ApiResponse(responseCode = "200", description = "Post deleted successfully")
    @ApiResponse(responseCode = "404", description = "Post not found or does not belong to the user")
    public ResponseEntity<String> delete(
            @PathVariable() Integer userId,
            @RequestParam() Integer postId) {
        postService.delete(postId, userId);
        return ResponseEntity.ok("Post deleted successfully");
    }

    @DeleteMapping(value = "/{userId}/images")
    @Operation(summary = "Delete an image from a post",
            description = "Deletes an image from a post if it belongs to the specified user ID")
    @ApiResponse(responseCode = "200", description = "Image deleted successfully")
    @ApiResponse(responseCode = "404", description = "Image or post not found or does not belong to the user")
    public ResponseEntity<String> deleteImageFromPost(
            @PathVariable Integer userId,
            @RequestParam Integer postId,
            @RequestParam Integer imageId) {
        postService.deleteImage(userId, postId, imageId);
        return ResponseEntity.ok("Image deleted successfully");
    }

    @PostMapping(value = "/{userId}/images", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Add an image to a post",
            description = "Add an image to a post if it belongs to the specified user ID")
    @ApiResponse(responseCode = "200", description = "Image added successfully")
    @ApiResponse(responseCode = "404", description = "Post not found or does not belong to the user")
    public ResponseEntity<String> addImageToPost(
            @PathVariable Integer userId,
            @RequestParam Integer postId,
            @RequestParam("image") MultipartFile image) {
        postService.addImage(userId, postId, image);
        return ResponseEntity.ok("Image added successfully");
    }

    @GetMapping("/{userId}/images")
    @Operation(summary = "Get images from a post",
            description = "Get images from a post if it belongs to the specified user ID")
    @ApiResponse(responseCode = "200", description = "Images retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Post not found or does not belong to the user")
    public ResponseEntity<List<ImagePostModel>> getImagesFromPost(
            @PathVariable Integer userId,
            @RequestParam Integer postId) {
        List<ImagePostModel> images = postService.getImages(userId, postId);
        return ResponseEntity.ok(images);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update a post",
            description = "Update an existing post with the information provided")
    @ApiResponse(responseCode = "200", description = "Post updated successfully")
    @ApiResponse(responseCode = "404", description = "Post not found")
    public ResponseEntity<PostDTO> update(
            @PathVariable Integer userId,
            @RequestParam("postId") Integer postId,
            @RequestParam("postDescription") String postDescription
    ) {
        PostModel updatedPost = postService.update(postId, postDescription, userId);
        return ResponseEntity.ok(convertToDTO(updatedPost));
    }

    private PostDTO convertToDTO(PostModel post) {
        PostDTO dto = modelMapper.map(post, PostDTO.class);
        // Add any additional processing if needed
        return dto;
    }

    private PostModel convertToEntity(PostDTO dto) {
        return modelMapper.map(dto, PostModel.class);
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