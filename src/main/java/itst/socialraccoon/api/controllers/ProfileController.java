package itst.socialraccoon.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itst.socialraccoon.api.annotations.GlobalApiResponses;
import itst.socialraccoon.api.dtos.ProfileDTO;
import itst.socialraccoon.api.models.ProfileModel;
import itst.socialraccoon.api.services.ProfileService;
import itst.socialraccoon.api.services.ImageProfileService;
import itst.socialraccoon.api.validators.ContentModerationValidationStrategy;
import itst.socialraccoon.api.validators.handlers.ImageValidationHandler;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("profiles")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Profiles", description = "Provides methods to manage profiles")
@GlobalApiResponses
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @Autowired
    private ImageProfileService imageProfileService;

    @Autowired
    private ImageValidationHandler Validator;

    @Autowired
    private ContentModerationValidationStrategy contentModerationValidationStrategy;

    @PostMapping(value = "/images/{profileId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Add profile image", description = "Add a user's profile image by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile image added", content = @Content),
            @ApiResponse(responseCode = "404", description = "User profile not found", content = @Content)
    })
    public ResponseEntity<String> addProfileImage(
            @PathVariable Integer profileId,
            @NotNull @RequestParam(value = "image") MultipartFile image
    ) {
        Validator.validateImage(image);
        imageProfileService.addProfileImage(profileId, image);
        return new ResponseEntity<>("Profile image added successfully", HttpStatus.OK);
    }

    @PutMapping(value = "/images/{imageId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "update profile image", description = "update a user's profile image by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile image added/updated", content = @Content),
            @ApiResponse(responseCode = "404", description = "User profile not found", content = @Content)
    })
    public ResponseEntity<String> UpdateProfileImage(
            @PathVariable(value = "imageId") @Positive Integer imageId,
            @NotNull @RequestParam(value = "image") MultipartFile image
    ) {
        Validator.validateImage(image);
        imageProfileService.updateProfileImage(imageId, image);
        return new ResponseEntity<>("Profile image updated successfully", HttpStatus.OK);
    }

    @DeleteMapping("/images/{imageId}")
    @Operation(summary = "Delete profile image", description = "Delete a user's profile image by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile image deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "User profile not found", content = @Content)
    })
    public ResponseEntity<String> deleteProfileImage(
            @PathVariable(value = "imageId") @Positive Integer imageId
    ) {
        imageProfileService.delete(imageId);
        return new ResponseEntity<>("Profile image deleted successfully", HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update profile description by user ID", description = "Update a user's profile description by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile updated", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileDTO.class))
            }),
            @ApiResponse(responseCode = "404", description
                    = "User profile not found", content = @Content)
    })
    public ResponseEntity<ProfileDTO> updateProfileByUserId(
            @PathVariable Integer userId,
            @NotBlank @RequestParam String description) {
        contentModerationValidationStrategy.isValid(description);
        ProfileModel profileModel = profileService.findByUserId(userId);
        profileModel.setDescription(description);
        ProfileDTO updateProfile = profileService.updateWithDTO(profileModel);
        return new ResponseEntity<>(updateProfile, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get profile by user ID", description = "Get a user's profile by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "User profile not found", content = @Content)
    })
    public ResponseEntity<ProfileDTO> getProfileByUserId(
            @PathVariable Integer userId) {
        ProfileDTO profile = profileService.getProfileByUserId(userId);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }

    @GetMapping("/controlNumber/{controlNumber}")
    @Operation(summary = "Get profile by control number", description = "Get a user's profile by their control number")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileDTO.class))
            }),
            @ApiResponse(responseCode = "404", description = "User profile not found", content = @Content)
    })
    public ResponseEntity<ProfileDTO> getProfileByControlNumber(
            @PathVariable String controlNumber) {
        ProfileDTO profile = profileService.getProfileByControlNumber(controlNumber);
        return new ResponseEntity<>(profile, HttpStatus.OK);
    }
}