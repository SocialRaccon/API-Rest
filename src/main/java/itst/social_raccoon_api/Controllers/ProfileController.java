package itst.social_raccoon_api.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itst.social_raccoon_api.Dto.ProfileDTO;
import itst.social_raccoon_api.Services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("profiles")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
@Tag(name = "profiles", description = "Provide methods to manage user profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/{userId}")
    @Operation(summary = "Get profile by user ID", description = "Retrieve a user's profile by their ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User profile found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ProfileDTO.class))
            }),
            @ApiResponse(responseCode = "404", description
                    = "User profile not found", content = @Content)
    })
    public ResponseEntity<ProfileDTO> getProfileByUserId(@PathVariable Integer userId) {
        ProfileDTO profileDTO = profileService.getProfileByUserId(userId);
        return new ResponseEntity<>(profileDTO, HttpStatus.OK);
    }
}