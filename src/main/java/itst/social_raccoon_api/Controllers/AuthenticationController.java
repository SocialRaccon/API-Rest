package itst.social_raccoon_api.Controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import itst.social_raccoon_api.Dto.AuthenticationDTO;
import itst.social_raccoon_api.Models.AuthenticationModel;
import itst.social_raccoon_api.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;

import java.net.URI;

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
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect password");
            }
        } else {
            return ResponseEntity.badRequest().body("Email not found");
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
        AuthenticationModel authenticationOptional = authenticationService.findByEmail(authentication.getEmail());
        if (authenticationOptional != null) {
            return ResponseEntity.ok("Password recovery email sent");
        } else {
            return ResponseEntity.badRequest().body("Email not found");
        }
    }

    // Change password
    @PutMapping("/change")
    @Operation(summary = "Change password", description = "Change user password")
    public ResponseEntity<String> changePassword(@RequestBody AuthenticationDTO authenticationDTO) {
        AuthenticationModel authenticationModel = authenticationService.findByEmail(authenticationDTO.getEmail());
        if (authenticationModel != null) {
            if (authenticationModel.getPassword().equals(authenticationDTO.getPassword())) {
                // Actualizar la contraseña directamente en la entidad AuthenticationModel
                authenticationModel.setPassword(authenticationDTO.getNewPassword());
                // Guardar la entidad AuthenticationModel con la nueva contraseña
                authenticationService.save(authenticationModel);
                return ResponseEntity.ok("Password changed successfully");
            } else {
                return ResponseEntity.badRequest().body("Incorrect password");
            }
        } else {
            return ResponseEntity.badRequest().body("Email not found");
        }
    }

}