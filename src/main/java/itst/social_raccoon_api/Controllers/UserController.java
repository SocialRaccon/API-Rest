package itst.social_raccoon_api.Controllers;

import io.swagger.v3.oas.annotations.Operation;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("user")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "User", description = "Provide methods to manage users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private CareerService careerService;
    @Autowired
    private ModelMapper modelMapper;

    @GetMapping()
    @Operation(summary = "Get all users", description = "Get all users from the database", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Found users",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserModel.class),
                            examples = @ExampleObject(
                                    name = "Users Example",
                                    value = "{" +
                                            "  \"idUser\": 2,\n" +
                                            "  \"lastName\": \"Pérez\",\n" +
                                            "  \"secondLastName\": \"Gómez\",\n" +
                                            "  \"email\": \"juan@gmail.com\",\n" +
                                            "  \"controlNumber\": \"21TE0121\",\n" +
                                            "  \"career\": {\n" +
                                            "    \"id\": 1,\n" +
                                            "    \"name\": \"Ingeniería en Sistemas Computacionales\",\n" +
                                            "    \"acronym\": \"ISC\"\n" +
                                            "  },\n" +
                                            "  \"name\": \"Juan\"\n" +
                                            "}"
                            )
                    )
            )}
    )
    public ResponseEntity<List<UserDTO>> findAll() {
        List<UserModel> users = userService.findAll();
        List<UserDTO> userDTOS = users.stream()
                .map(this::convertToDTO)
                .toList();
        return new ResponseEntity<>(userDTOS, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get user by id", description = "Get a user by its id", responses = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "User found",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserModel.class),
                            examples = @ExampleObject(
                                    name = "User Example",
                                    value = "{" +
                                            "  \"idUser\": 2,\n" +
                                            "  \"lastName\": \"Pérez\",\n" +
                                            "  \"secondLastName\": \"Gómez\",\n" +
                                            "  \"email\": \"juan@gmail.com\",\n" +
                                            "  \"controlNumber\": \"21TE0121\",\n" +
                                            "  \"career\": {\n" +
                                            "    \"id\": 1,\n" +
                                            "    \"name\": \"Ingeniería en Sistemas Computacionales\",\n" +
                                            "    \"acronym\": \"ISC\"\n" +
                                            "  },\n" +
                                            "  \"name\": \"Juan\"\n" +
                                            "}"
                            )
                    )
            )}
    )
    public ResponseEntity<UserModel> findById(@PathVariable Integer id) {
        UserModel user = userService.findById(id);
        if (user == null) {
            throw new NoSuchElementException();
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping()
    @Operation(
            summary = "Create a new user",
            description = "Create a new user in the database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserModel.class),
                            examples = @ExampleObject(
                                    name = "User Example",
                                    value = "{ \"name\": \"John\", \"lastName\": \"Doe\", \"secondLastName\": \"Smith\", \"email\": \"john.doe@example.com\", \"controlNumber\": \"123456\", \"career\": { \"idCareer\": 1 } }"
                            )
                    )
            )
    )
    public ResponseEntity<UserModel> create(@RequestBody UserModel user) {
        CareerModel career = careerService.findById(user.getCareer().getIdCareer());
        if (career == null) {
            throw new NoSuchElementException();
        }
        user.setCareer(career);
        UserModel newUser = userService.save(user);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @Operation(
            summary = "Update a user",
            description = "Update a user in the database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserModel.class),
                            examples = @ExampleObject(
                                    name = "User Example",
                                    value = "{ \"idUser\": 1, \"name\": \"John\", \"lastName\": \"Doe\", \"secondLastName\": \"Smith\", \"email\": \"john.doe@example.com\", \"controlNumber\": \"123456\" }"
                            )
                    ))
    )
    public ResponseEntity<UserModel> update(@PathVariable Integer id, @RequestBody UserModel user) {
        UserModel existingUser = userService.findById(id);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        user.setIdUser(id);
        UserModel updatedUser = userService.save(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
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
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Integer id) {
        userService.deleteById(id);
        Map<String, Boolean> response = Map.of("deleted", Boolean.TRUE);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public UserDTO convertToDTO(UserModel user) {
        return modelMapper.map(user, UserDTO.class);
    }

    //Commit
    /*
      fixed create and update for user
     */
}
