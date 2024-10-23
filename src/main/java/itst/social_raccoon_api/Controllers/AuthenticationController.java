package itst.social_raccoon_api.Controllers;

import itst.social_raccoon_api.Models.AuthenticationModel;
import itst.social_raccoon_api.Repositories.AuthenticationRepository;
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
    private AuthenticationRepository authenticationRepository;

    // Find all
    @GetMapping
    @Operation(summary = "Get all Auhtentication ", description = "Get all Auhtentication from the database")
    public ResponseEntity<Iterable<AuthenticationModel>> findAll() {
        return ResponseEntity.ok(authenticationRepository.findAll());
    }

    // Find By Id
    @GetMapping("/{id}")
    @Operation(summary = "Get authentication by id", description = "Get all authentications by id from the database")
    public ResponseEntity<AuthenticationModel> findById(@PathVariable Integer idAuthentication) {
        Optional<AuthenticationModel> authenticationOptional = authenticationRepository.findById(idAuthentication);
        if (authenticationOptional.isPresent()) {
            return ResponseEntity.ok(authenticationOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create Auhtentication
    @PostMapping
    @Operation(summary = "Create a new career", description = "Create a new career in the database")
    
    public ResponseEntity<String> create(@RequestBody AuthenticationModel authentication, UriComponentsBuilder ucb) {
        AuthenticationModel saveAutentication = authenticationRepository.save(authentication);
        URI uri = ucb
                .path("/authentication{id}")
                .buildAndExpand(saveAutentication.getIdAuthentication())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    // Delete Auhtentication
    @DeleteMapping("/{idAuthentication}")
    @Operation(summary = "Delete a Auhtentication", description = "Delete a Auhtentication in the database")
    public ResponseEntity<String> delete(@PathVariable Integer idAuthentication) {
        if (authenticationRepository.findById(idAuthentication).get() != null) {
            authenticationRepository.deleteById(idAuthentication);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    
    // Login
    @PostMapping("/login")
    @Operation(summary = "Login", description = "Login user")
    public ResponseEntity<String> login(@RequestBody AuthenticationModel authentication) {
        Optional<AuthenticationModel> authenticationOptional = authenticationRepository.findById(authentication.getIdAuthentication());

        if (authenticationOptional.isPresent()) {
            AuthenticationModel auth = authenticationOptional.get();
            if (auth.getEmail().equals(authentication.getEmail()) && auth.getPassword().equals(authentication.getPassword())) {
                return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.badRequest().body("Invalid email or password");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Recover password
    @PostMapping("/recover")
    @Operation(summary = "Recover password", description = "Recover user password")
    public ResponseEntity<String> recoverPassword(@RequestBody AuthenticationModel authentication) {
        Optional<AuthenticationModel> authenticationOptional = authenticationRepository.findById(authentication.getIdAuthentication());

        if (authenticationOptional.isPresent()) {
            AuthenticationModel auth = authenticationOptional.get();
            if (auth.getEmail().equals(authentication.getEmail())) {
                // Send email with reset link or new password
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
        Optional<AuthenticationModel> authenticationOptional = authenticationRepository.findById(authentication.getIdAuthentication());

        if (authenticationOptional.isPresent()) {
            AuthenticationModel auth = authenticationOptional.get();
            if (auth.getPassword().equals(authentication.getPassword())) {
                auth.setPassword(authentication.getPassword());
                authenticationRepository.save(auth);
                return ResponseEntity.ok("Password changed successfully");
            } else {
                return ResponseEntity.badRequest().body("Invalid password");
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
}