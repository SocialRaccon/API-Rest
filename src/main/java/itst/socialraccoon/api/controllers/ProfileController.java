package itst.socialraccoon.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itst.socialraccoon.api.annotations.GlobalApiResponses;
import itst.socialraccoon.api.services.ProfileService;
import itst.socialraccoon.api.services.ImageProfileService;
import itst.socialraccoon.api.validators.handlers.ImageValidationHandler;
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

    @PostMapping(value = "/images/{profileId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Add profile image", description = "Add a user's profile image by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profile image added", content = @Content),
            @ApiResponse(responseCode = "404", description = "User profile not found", content = @Content)
    })
    public ResponseEntity<String> addProfileImage(
            @PathVariable Integer profileId,
            @RequestParam(value = "image") MultipartFile image
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
            @RequestParam(value = "imageId") Integer imageId,
            @RequestParam(value = "image") MultipartFile image
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
            @RequestParam(value = "imageId") Integer imageId
    ) {
        imageProfileService.delete(imageId);
        return new ResponseEntity<>("Profile image deleted successfully", HttpStatus.OK);
    }
}