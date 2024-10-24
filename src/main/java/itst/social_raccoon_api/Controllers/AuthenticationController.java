package itst.social_raccoon_api.Controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import itst.social_raccoon_api.Models.AuthenticationModel;
import itst.social_raccoon_api.Services.AuthenticationService;

@RestController
@RequestMapping("/authentications")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Authentications", description = "Provides methods to manage authentications.")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    // Create Auhtentication
    @PostMapping
    @Operation(
            summary = "Create Authentication",
            description = "Create user authentication of a user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Authentication to be created",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AuthenticationModel.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "Authentication",
                                    value = "{\n"
                                    + "  \"email\": \"alex2227@hotmail.com\",\n"
                                    + "  \"password\": \"password\",\n"
                                    + "  \"user\": {\n"
                                    + "    \"idUser\": 1\n"
                                    + "  }\n"
                                    + "}"
                            )
                    )
            )
    )
    public ResponseEntity<String> create(@RequestBody AuthenticationModel authentication, UriComponentsBuilder ucb) {
        AuthenticationModel saveAutentication = authenticationService.save(authentication);
        URI uri = ucb
                .path("/authentication{id}")
                .buildAndExpand(saveAutentication.getIdAuthentication())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    // Login
    @PostMapping("/login")
    @Operation(
            summary = "Login",
            description = "Login user",
            tags = {"Authentications"},
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User email and password",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AuthenticationModel.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "Login",
                                    value = "{\n"
                                    + "  \"email\": \"alex2227@hotmail.com\",\n"
                                    + "  \"password\": \"password\"\n"
                                    + "}"
                            )
                    )
            )
    )
    public ResponseEntity<String> login(@RequestBody AuthenticationModel authentication) {
        AuthenticationModel authenticationOptional = authenticationService.findByEmail(authentication.getEmail());
        if (authenticationOptional != null) {
            if (authenticationOptional.getPassword().equals(authentication.getPassword())) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.badRequest().body("Invalid password");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Recover password
    @PostMapping("/recover")
    @Operation(
            summary = "Recover password",
            description = "Recover user password",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User email",
                    required = true,
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = AuthenticationModel.class),
                            examples = @io.swagger.v3.oas.annotations.media.ExampleObject(
                                    name = "Recover",
                                    value = "{\n"
                                    + "  \"email\": \"alex2227@hotmail.com \"\n"
                                    + "}"
                            )
                    )
            )
    )
    public ResponseEntity<String> recoverPassword(@RequestBody AuthenticationModel authentication) {
        AuthenticationModel authenticationOptional = authenticationService.findById(authentication.getIdAuthentication());
        if (authenticationOptional != null) {
            if (authenticationOptional.getEmail().equals(authentication.getEmail())) {
                return ResponseEntity.ok("Password recovery email sent");
            } else {
                return ResponseEntity.badRequest().body("Invalid email");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Change password
    @PutMapping("/change")
    @Operation(summary = "Change password", description = "Change user password")
    public ResponseEntity<String> changePassword(@RequestBody AuthenticationModel authentication) {
        AuthenticationModel authenticationOptional = authenticationService.findByEmail(authentication.getEmail());
        if (authenticationOptional != null) {
            if (authenticationOptional.getPassword().equals(authentication.getPassword())) {
                authenticationOptional.setPassword(authentication.getPassword());
                authenticationService.save(authenticationOptional);
                return ResponseEntity.ok("Password changed successfully");
            } else {
                return ResponseEntity.badRequest().body("Invalid password");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
