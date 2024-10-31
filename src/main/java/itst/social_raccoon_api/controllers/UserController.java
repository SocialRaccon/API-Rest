package itst.social_raccoon_api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import itst.social_raccoon_api.dtos.UserDTO;
import itst.social_raccoon_api.dtos.UserRequestDTO;
import itst.social_raccoon_api.models.*;
import itst.social_raccoon_api.services.CareerService;
import itst.social_raccoon_api.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Users", description = "Provide methods to manage users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CareerService careerService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping(value = "/withImage", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @Operation(
            summary = "Create a new user with image",
            description = "",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User object that needs to be added to the database",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CommentModel.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "User",
                                    value = "{\n  " +
                                            "\"name\": \"Josue\",\n  " +
                                            "\"lastName\": \"Fuentes\",\n  " +
                                            "\"secondLastName\": \"Luna\",\n  " +
                                            "\"email\": \"luna2227@hotmail.com\",\n  " +
                                            "\"controlNumber\": \"21TE0374\",\n  " +
                                            "\"password\": \"12345\",\n  " +
                                            "\"career\": 1\n}"
                            )
                    )
            )
    )
    public ResponseEntity<UserDTO> create(
            @RequestBody UserRequestDTO user,
            @RequestParam("image") MultipartFile imagen) {
        UserModel userModel = userService.save(convertToEntity(user), imagen);
        return new ResponseEntity<>(convertToDTO(userModel), HttpStatus.CREATED);
    }

    @PostMapping()
    @Operation(
            summary = "Create a new user",
            description = "",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User object that needs to be added to the database",
                    required = true,
                    content = @Content(
                            schema = @Schema(implementation = CommentModel.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "User",
                                    value = "{\n  " +
                                            "\"name\": \"Josue\",\n  " +
                                            "\"lastName\": \"Fuentes\",\n  " +
                                            "\"secondLastName\": \"Luna\",\n  " +
                                            "\"email\": \"luna2227@hotmail.com\",\n  " +
                                            "\"controlNumber\": \"21TE0374\",\n  " +
                                            "\"password\": \"12345\",\n  " +
                                            "\"career\": 1\n}"

                            )
                    )
            )
    )
    public ResponseEntity<UserDTO> create(
            @RequestBody UserRequestDTO user) {
        UserModel userModel = userService.save(convertToEntity(user));
        return new ResponseEntity<>(convertToDTO(userModel), HttpStatus.CREATED);
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

    public UserModel convertToEntity(UserRequestDTO userRequestDTO) {
        UserModel user = modelMapper.map(userRequestDTO, UserModel.class);
        CareerModel careerModel = careerService.findById(userRequestDTO.getCareer());
        AuthenticationModel authenticationModel = new AuthenticationModel();
        authenticationModel.setEmail(userRequestDTO.getEmail());
        authenticationModel.setPassword(userRequestDTO.getPassword());
        ProfileModel profileModel = new ProfileModel();
        profileModel.setDescription("");
        profileModel.setIdUser(user);
        user.setProfile(profileModel);
        user.setCareer(careerModel);
        user.setAuthentication(authenticationModel);
        return user;
    }

    public UserDTO convertToDTO(UserModel user) {
        UserDTO userModel = modelMapper.map(user, UserDTO.class);
        userModel.setEmail(user.getAuthentication().getEmail());
        return userModel;
    }

}
