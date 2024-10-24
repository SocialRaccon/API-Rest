package itst.social_raccoon_api.Controllers;

import itst.social_raccoon_api.Dto.AuthenticationDTO;
import itst.social_raccoon_api.Models.AuthenticationModel;
import itst.social_raccoon_api.Models.UserModel;
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
        AuthenticationModel authenticationModel = authenticationService.findByEmail(authentication.getEmail());
        if (authenticationModel != null) {
            if (authenticationModel.getPassword().equals(authentication.getPassword())) {
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
        AuthenticationModel authenticationModel = authenticationService
                .findById(authentication.getIdAuthentication());
        if (authenticationModel != null) {
            if (authenticationModel.getEmail().equals(authentication.getEmail())) {
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
            return ResponseEntity.badRequest().body("Invalid password");
        }
    } else {
        return ResponseEntity.notFound().build();
    }
}

}