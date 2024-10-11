package itst.social_raccoon_api.Controllers;

import itst.social_raccoon_api.Models.UserModel;
import itst.social_raccoon_api.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    // Find all
    @GetMapping
    public ResponseEntity<Iterable<UserModel>> findAll() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // Find by Name
    @GetMapping("/nombre/{name}")
    public ResponseEntity<Iterable<UserModel>> findAllByName(@PathVariable String name) {
        Iterable<UserModel> userOptional = userRepository.getUserByName(name);
        return ResponseEntity.ok(userOptional);
    }

    // Find By Id
    @GetMapping("/{id}")
    public ResponseEntity<UserModel> findById(@PathVariable Long idUser) {
        Optional<UserModel> userOptional = userRepository.findById(idUser);
        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create User
    @PostMapping
    public ResponseEntity<String> create(@RequestBody UserModel user, UriComponentsBuilder ucb) {
        UserModel savedUser = userRepository.save(user);
        URI uri = ucb
                .path("/user/{id}")
                .buildAndExpand(savedUser.getIdUser())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    // Update User
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long idUser, @RequestBody UserModel userAct) {
        UserModel userAnt = userRepository.findById(idUser).get();
        if (userAnt != null) {
            userAct.setIdUser(userAnt.getIdUser());
            userRepository.save(userAct);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/{idUser}")
    public ResponseEntity<String> delete(@PathVariable Long idUser) {
        if (userRepository.findById(idUser).get() != null) {
            userRepository.deleteById(idUser);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}