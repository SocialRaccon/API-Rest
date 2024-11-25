package itst.socialraccoon.api.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import itst.socialraccoon.api.annotations.GlobalApiResponses;
import itst.socialraccoon.api.dtos.UserRequestDTO;
import itst.socialraccoon.api.models.AuthenticationModel;
import itst.socialraccoon.api.models.CareerModel;
import itst.socialraccoon.api.models.ProfileModel;
import itst.socialraccoon.api.models.UserModel;
import itst.socialraccoon.api.services.CareerService;
import itst.socialraccoon.api.services.UserService;
import itst.socialraccoon.api.dtos.UserDTO;
import itst.socialraccoon.api.validators.FileValidator;
import itst.socialraccoon.api.validators.ImageFileValidationStrategy;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("users")
@Validated
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE })
@Tag(name = "Users", description = "Provides methods to manage users")
@GlobalApiResponses
public class UserController {

        @Autowired
        private UserService userService;

        @Autowired
        private CareerService careerService;

        @Autowired
        private ModelMapper modelMapper;

        @Autowired
        private FileValidator fileValidator;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @PostMapping()
        @Operation(summary = "Create a new user", description = "", requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "User object that needs to be added to the database", required = true, content = @Content(examples = @io.swagger.v3.oas.annotations.media.ExampleObject(name = "User", value = "{\n  "
                        +
                        "\"name\": \"Josue\",\n  " +
                        "\"lastName\": \"Fuentes\",\n  " +
                        "\"secondLastName\": \"Luna\",\n  " +
                        "\"email\": \"luna2227@hotmail.com\",\n  " +
                        "\"controlNumber\": \"21TE0374\",\n  " +
                        "\"password\": \"12345\",\n  " +
                        "\"career\": 1\n}"

        ))))
        public ResponseEntity<UserDTO> create(
                        @Valid @RequestBody UserRequestDTO user) {
                UserModel userModel = userService.save(convertToEntity(user));
                return new ResponseEntity<>(convertToDTO(userModel), HttpStatus.CREATED);
        }

        @PostMapping(value = "/withImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        @Operation(summary = "Create a new user with an image", description = "Creates a new user with profile image")
        public ResponseEntity<UserDTO> createWithImage(
                        @RequestPart("file") @Schema(type = "string", format = "binary") MultipartFile file,
                        @RequestPart("user") @Parameter(schema = @Schema(implementation = UserRequestDTO.class)) String userJson)
                        throws IOException {
                ImageFileValidationStrategy imageFileValidationStrategy = new ImageFileValidationStrategy();
                fileValidator.setStrategy(imageFileValidationStrategy);
                if (!fileValidator.validate(file)) {
                        throw new IllegalArgumentException(
                                        "Image file must be " + imageFileValidationStrategy.getAllowedTypes());
                }
                UserRequestDTO user = new ObjectMapper().readValue(userJson, UserRequestDTO.class);
                UserModel userModel = userService.save(convertToEntity(user), file);
                return new ResponseEntity<>(convertToDTO(userModel), HttpStatus.CREATED);
        }

        @DeleteMapping("/{userId}")
        @Operation(summary = "Delete a user", description = "Delete a user from the database", responses = {
                        @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "User deleted", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Map.class), examples = @ExampleObject(name = "Deleted", value = "{ \"deleted\": true }"))) })
        public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Integer userId) {
                userService.deleteById(userId);
                Map<String, Boolean> response = Map.of("deleted", Boolean.TRUE);
                return new ResponseEntity<>(response, HttpStatus.OK);
        }

        @GetMapping("/current")
        public ResponseEntity<UserDTO> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
                String email = userDetails.getUsername();
                UserModel user = userService.findByEmail(email);
                UserDTO userDTO = convertToDTO(user);
                return ResponseEntity.ok(userDTO);
        }

        public UserModel convertToEntity(UserRequestDTO userRequestDTO) {
                UserModel user = modelMapper.map(userRequestDTO, UserModel.class);
                CareerModel careerModel = careerService.findById(userRequestDTO.getCareer());
                if (careerModel == null) {
                        throw new NoSuchElementException("Career not found");
                }
                AuthenticationModel authenticationModel = new AuthenticationModel();
                authenticationModel.setEmail(userRequestDTO.getEmail());
                authenticationModel.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
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
