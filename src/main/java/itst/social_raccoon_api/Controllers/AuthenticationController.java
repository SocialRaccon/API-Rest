package itst.social_raccoon_api.Controllers;

import itst.social_raccoon_api.Models.AuthenticationModel;
import itst.social_raccoon_api.Services.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;

import java.util.Optional;
import java.net.URI;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    // Create Auhtentication
    @PostMapping
    @Operation(summary = "Create Authentication", description = "Create user authentication")

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
    @Operation(summary = "Login", description = "Login user")
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
    @Operation(summary = "Recover password", description = "Recover user password")
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