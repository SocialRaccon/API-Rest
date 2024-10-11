package itst.social_raccoon_api.Controllers;

import itst.social_raccoon_api.Models.AuthenticationModel;
import itst.social_raccoon_api.Repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.net.URI;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {
    @Autowired
    private AuthenticationRepository authenticationRepository;

    // Find all
    @GetMapping
    public ResponseEntity<Iterable<AuthenticationModel>> findAll() {
        return ResponseEntity.ok(authenticationRepository.findAll());
    }

    // Find By Id
    @GetMapping("/{id}")
    public ResponseEntity<AuthenticationModel> findById(@PathVariable Long idAuthentication) {
        Optional<AuthenticationModel> authenticationOptional = authenticationRepository.findById(idAuthentication);
        if (authenticationOptional.isPresent()) {
            return ResponseEntity.ok(authenticationOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create User
    @PostMapping
    public ResponseEntity<String> create(@RequestBody AuthenticationModel authentication, UriComponentsBuilder ucb) {
        AuthenticationModel saveAutentication = authenticationRepository.save(authentication);
        URI uri = ucb
                .path("/authentication{id}")
                .buildAndExpand(saveAutentication.getIdAuthentication())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    // Update User
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long idAuthentication, @RequestBody AuthenticationModel autenticationAct) {
        AuthenticationModel autenticationAnt = authenticationRepository.findById(idAuthentication).get();
        if (autenticationAnt != null) {
            autenticationAct.setIdAuthentication(autenticationAnt.getIdAuthentication());
            authenticationRepository.save(autenticationAct);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/{idAuthentication}")
    public ResponseEntity<String> delete(@PathVariable Long idAuthentication) {
        if (authenticationRepository.findById(idAuthentication).get() != null) {
            authenticationRepository.deleteById(idAuthentication);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}