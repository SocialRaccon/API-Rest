package itst.social_raccoon_api.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import itst.social_raccoon_api.Dto.UserDTO;
import itst.social_raccoon_api.Models.CareerModel;
import itst.social_raccoon_api.Models.UserModel;
import itst.social_raccoon_api.Services.CareerService;
import itst.social_raccoon_api.Services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Users", description = "Provide methods to manage users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping()
    @Operation(summary = "Create a user", description = "Create a user in the database",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "User created",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation
                                            = UserDTO.class)

                            )
                    )},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema
                                    = @Schema(implementation
                                    = UserModel.class),
                            examples = @ExampleObject(
                                    name = "User Example",
                                    value = "{ \"name\": \"John\", \"lastName\": \"Doe\", \"secondLastName\": \"Smith\", \"email\": \"john.doe@example.com\", \"controlNumber\": \"123456\", \"career\": { \"idCareer\": 1 } }"
                            )
                    )
            )
    )
    public ResponseEntity<UserDTO> create(@RequestBody UserModel user) {
        UserModel newUser = userService.save(user);
        UserDTO userDTO = convertToDTO(newUser);
        return new ResponseEntity<>(userDTO, HttpStatus.CREATED);
    }

    @PostMapping(value = "/{userId}/profileImage", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(summary = "Upload a user's profile image", description = "Upload a user's profile image to the database",
            responses = {
                    @io.swagger.v3.oas.annotations.responses.ApiResponse(
                            responseCode = "201",
                            description = "Profile image uploaded",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = String.class),
                                    examples = @ExampleObject(
                                            name = "Uploaded",
                                            value = "Profile image uploaded"
                                    )
                            )
                    )}
    )
    public ResponseEntity<String> uploadProfileImage(
            @PathVariable Integer userId,
            @RequestParam("file") MultipartFile profileImage
    ) throws IOException {
        userService.updateProfileImage(userId, profileImage);
        return new ResponseEntity<>("Profile image uploaded", HttpStatus.CREATED);
    }


    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete a user", description = "Delete a user from the database", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "User deleted",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(
                                    name = "Deleted",
                                    value = "{ \"deleted\": true }"
                            )
                    )
            )}
    )
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Integer userId) {
        userService.deleteById(userId);
        Map<String, Boolean> response = Map.of("deleted", Boolean.TRUE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}/profileImage")
    @Operation(summary = "Delete a user's profile image", description = "Delete a user's profile image from the database", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Profile image deleted",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class),
                            examples = @ExampleObject(
                                    name = "Deleted",
                                    value = "{ \"deleted\": true }"
                            )
                    )
            )}
    )
    public ResponseEntity<Map<String, Boolean>> deleteProfileImage(@PathVariable Integer userId) {
        boolean deleted = userService.deleteProfileImage(userId);
        Map<String, Boolean> response = Map.of("deleted", deleted);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    public UserDTO convertToDTO(UserModel user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
