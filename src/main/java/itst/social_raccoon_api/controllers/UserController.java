package itst.social_raccoon_api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.*;
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

    @PostMapping()
    @Operation(
            summary = "Create a new user",
            description = "",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User object that needs to be added to the database",
                    required = true,
                    content = @Content(
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

    @PostMapping(value = "/withImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Create a new user with an image", description = "Creates a new user with profile image")
    public ResponseEntity<UserDTO> createWithImage(
            @RequestPart("file")
            @Schema(type = "string", format = "binary") MultipartFile file,
            @RequestPart("user") @Parameter(schema = @Schema(implementation = UserRequestDTO.class))
            String userJson) throws IOException {
        UserRequestDTO user = new ObjectMapper().readValue(userJson, UserRequestDTO.class);
        UserModel userModel = userService.save(convertToEntity(user), file);
        return new ResponseEntity<>(convertToDTO(userModel), HttpStatus.CREATED);
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
