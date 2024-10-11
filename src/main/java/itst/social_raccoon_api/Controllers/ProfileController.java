package itst.social_raccoon_api.Controllers;

import itst.social_raccoon_api.Models.ProfileModel;
import itst.social_raccoon_api.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.net.URI;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileRepository profileRepository;

    // Find all
    @GetMapping
    public ResponseEntity<Iterable<ProfileModel>> findAll() {
        return ResponseEntity.ok(profileRepository.findAll());
    }

    // Find By Id
    @GetMapping("/{id}")
    public ResponseEntity<ProfileModel> findById(@PathVariable Long idProfile) {
        Optional<ProfileModel> profileOptional = profileRepository.findById(idProfile);
        if (profileOptional.isPresent()) {
            return ResponseEntity.ok(profileOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create User
    @PostMapping
    public ResponseEntity<String> create(@RequestBody ProfileModel profile, UriComponentsBuilder ucb) {
        ProfileModel saveProfile = profileRepository.save(profile);
        URI uri = ucb
                .path("/profile/{id}")
                .buildAndExpand(saveProfile.getIdProfile())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    // Update User
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long idProfile, @RequestBody ProfileModel profileAct) {
        ProfileModel profileAnt = profileRepository.findById(idProfile).get();
        if (profileAnt != null) {
            profileAct.setIdProfile(profileAnt.getIdProfile());
            profileRepository.save(profileAct);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/{idProfile}")
    public ResponseEntity<String> delete(@PathVariable Long idProfile) {
        if (profileRepository.findById(idProfile).get() != null) {
            profileRepository.deleteById(idProfile);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}