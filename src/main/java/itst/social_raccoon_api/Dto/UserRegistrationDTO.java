package itst.social_raccoon_api.Dto;

import itst.social_raccoon_api.Models.UserModel;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.web.multipart.MultipartFile;

public class UserRegistrationDTO {

    @Schema(description = "User object", implementation = UserModel.class, example = "{ \"name\": \"John\", \"lastName\": \"Doe\", \"secondLastName\": \"Smith\", \"email\": \"john.doe@example.com\", \"controlNumber\": \"12345678\", \"career\": { \"idCareer\": 1 } }")
    private UserModel user;

    @Schema(description = "Profile image file")
    private MultipartFile profileImage;

    // Getters and setters
    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public MultipartFile getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(MultipartFile profileImage) {
        this.profileImage = profileImage;
    }
}