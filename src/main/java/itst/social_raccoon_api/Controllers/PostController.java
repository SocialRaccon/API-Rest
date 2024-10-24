package itst.social_raccoon_api.Controllers;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import itst.social_raccoon_api.Dto.PostDTO;
import itst.social_raccoon_api.Models.PostModel;
import itst.social_raccoon_api.Services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("posts")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "posts", description = "Proporciona métodos para gestionar posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{userId}")
    @Operation(summary = "Get posts by user ID",
            description = "Retrieves all posts associated with the specified user ID")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved posts")
    @ApiResponse(responseCode = "404", description = "No posts found for the user")
    public ResponseEntity<List<PostDTO>> getPostsByUserId(@PathVariable Integer userId) {
        List<PostModel> posts = postService.findByUser(userId);
        if (posts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<PostDTO> postDTOs = posts.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        List<PostDTO> PostDTO = List.of();
        return ResponseEntity.ok(PostDTO);
    }

    private PostDTO convertToDTO(PostModel post) {
        PostDTO dto = modelMapper.map(post, PostDTO.class);
        // Add any additional processing if needed
        return dto;
    }

    private PostModel convertToEntity(PostDTO dto) {
        return modelMapper.map(dto, PostModel.class);
    }

    @PostMapping
    @JsonBackReference
    @JsonManagedReference
    @Operation(summary = "Creación de un nuevo post",
            description = "Crea un nuevo post con la información proporcionada")
    @ApiResponse(responseCode = "201", description = "Post creado exitosamente")
    @ApiResponse(responseCode = "400", description = "Datos del post inválidos")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Datos del nuevo post",
            required = true,
            content = @io.swagger.v3.oas.annotations.media.Content(
                    mediaType = "application/json",
                    schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PostDTO.class),
                    examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                            name = "Nuevo post",
                            value = "{\n" +
                                    "  \"content\": \"Este es el contenido del nuevo post\",\n" +
                                    "  \"userId\": 1,\n" +
                                    "  \"imageUrl\": \"https://ejemplo.com/imagen.jpg\"\n" +
                                    "}"
                    )
            )
    )
    public ResponseEntity<PostDTO> createPost(@RequestBody PostDTO postDTO) {
        PostModel postModel = convertToEntity(postDTO);
        PostModel savedPost = postService.save(postModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDTO(savedPost));
    }

    public PostService getPostService() {
        return postService;
    }

    public void setPostService(PostService postService) {
        this.postService = postService;
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @GetMapping("/feed")
    @Operation(summary = "Obtener feed de posts",
            description = "Recupera un feed paginado de posts, ordenados por fecha de creación descendente")
    @ApiResponse(responseCode = "200", description = "Feed recuperado exitosamente")
    public ResponseEntity<Page<PostDTO>> getFeed(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PostModel> postPage = postService.getFeed(pageable);
        Page<PostDTO> postDTOPage = postPage.map(this::convertToDTO);
        return ResponseEntity.ok(postDTOPage);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un post",
            description = "Elimina un post existente por su ID")
    @ApiResponse(responseCode = "204", description = "Post eliminado exitosamente")
    @ApiResponse(responseCode = "404", description = "Post no encontrado")
    public ResponseEntity<Void> deletePost(@PathVariable Integer id) {
        try {
            postService.deletePost(id);
            return ResponseEntity.noContent().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un post",
            description = "Actualiza un post existente con la información proporcionada")
    @ApiResponse(responseCode = "200", description = "Post actualizado exitosamente")
    @ApiResponse(responseCode = "404", description = "Post no encontrado")
    public ResponseEntity<PostDTO> updatePost(@PathVariable Integer id, @RequestBody PostDTO postDTO) {
        try {
            PostModel postModel = convertToEntity(postDTO);
            PostModel updatedPost = postService.updatePost(id, postModel);
            return ResponseEntity.ok(convertToDTO(updatedPost));
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }
}