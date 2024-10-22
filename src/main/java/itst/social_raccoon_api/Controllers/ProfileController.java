package itst.social_raccoon_api.Controllers;

import itst.social_raccoon_api.Models.ProfileModel;
import itst.social_raccoon_api.Repositories.ProfileRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import io.swagger.v3.oas.annotations.Operation;

import java.util.Optional;
import java.net.URI;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private ProfileRepository profileRepository;

    // Find all
    @GetMapping
    @Operation(summary = "Get all Profile ", description = "Get all Profile from the database")
    public ResponseEntity<Iterable<ProfileModel>> findAll() {
        return ResponseEntity.ok(profileRepository.findAll());
    }

    // Find By Id
    @GetMapping("/{id}")
    @Operation(summary = "Get profile by Id ", description = "Get Profile by id from the database")
    public ResponseEntity<ProfileModel> findById(@PathVariable Integer idProfile) {
        Optional<ProfileModel> profileOptional = profileRepository.findById(idProfile);
        if (profileOptional.isPresent()) {
            return ResponseEntity.ok(profileOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create Profile
    @PostMapping
    @Operation(summary = "Create Profile ", description = "Create Profile on the database")
    public ResponseEntity<String> create(@RequestBody ProfileModel profile, UriComponentsBuilder ucb) {
        ProfileModel saveProfile = profileRepository.save(profile);
        URI uri = ucb
                .path("/profile/{id}")
                .buildAndExpand(saveProfile.getIdProfile())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    // Update Profile
    @PutMapping("/{id}")
    @Operation(summary = "Update Profile ", description = "Update a Profile in the database")
    public ResponseEntity<String> update(@PathVariable Integer idProfile, @RequestBody ProfileModel profileAct) {
        ProfileModel profileAnt = profileRepository.findById(idProfile).get();
        if (profileAnt != null) {
            profileAct.setIdProfile(profileAnt.getIdProfile());
            profileRepository.save(profileAct);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Delete Profile
    @DeleteMapping("/{idProfile}")
    @Operation(summary = "Delete Profile ", description = "Delete a Profile in the database")
    public ResponseEntity<String> delete(@PathVariable Integer idProfile) {
        if (profileRepository.findById(idProfile).get() != null) {
            profileRepository.deleteById(idProfile);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}