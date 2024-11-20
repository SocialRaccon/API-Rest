package itst.socialraccoon.api.models;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Model representing a profile image")
@Entity
@Table(name = "image_profile")
public class ImageProfileModel {

    @Schema(description = "Unique identifier of the image profile", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idImageProfile")
    @JsonProperty("idImageProfile")
    private Integer idImageProfile;

    @ManyToOne()
    @JoinColumn(name = "idProfile", nullable = false)
    @JsonProperty("idProfile")
    @Schema(description = "Profile to which the image belongs", example = "1")
    @JsonBackReference(value = "profile-image")
    @NotNull(message = "El perfil no puede ser nulo")
    private ProfileModel profile;

    @Schema(description = "URL of the image", example = "https://www.example.com/image.jpg")
    @Column(name = "imageUrl", nullable = false, length = 500)
    @Size(max = 500, message = "The URL of the image must not exceed 500 characters")
    @NotBlank(message = "The URL of the image must not be empty and must contain at least one character that is not a whitespace")
    @Pattern(regexp = "^(http|https)://firebasestorage\\.googleapis\\.com/v0/b/[^/]+/o/[^?]+\\.(jpg|jpeg|png)\\?alt=media$",
            message = "The URL of the image must be a valid Firebase Storage URL ending in JPG, JPEG, or PNG")
    private String imageUrl = "https://firebasestorage.googleapis.com/v0/b/socialraccoon-990a3.appspot.com/o/user.png?alt=media&token=c303a942-13e8-4758-a578-e5b6e70400a1";

    @Schema(description = "Thumbnail of the image", example = "https://www.example.com/image.jpg")
    @Column(name = "imageThumbnailUrl", nullable = false, length = 500)
    @Size(max = 500, message = "The URL of the image thumbnail must not exceed 500 characters")
    @NotBlank(message = "The URL of the image thumbnail must not be empty and must contain at least one character that is not a whitespace")
    @Pattern(regexp = "^(http|https)://firebasestorage\\.googleapis\\.com/v0/b/[^/]+/o/[^?]+\\.(jpg|jpeg|png)\\?alt=media$",
            message = "The URL of the image must be a valid Firebase Storage URL ending in JPEG, or PNG")
    private String imageThumbnailUrl = "https://firebasestorage.googleapis.com/v0/b/socialraccoon-990a3.appspot.com/o/user.png?alt=media&token=c303a942-13e8-4758-a578-e5b6e70400a1";

    public ImageProfileModel(Integer idImageProfile, ProfileModel profile, String imageUrl, String imageThumbnailUrl) {
        this.idImageProfile = idImageProfile;
        this.profile = profile;
        this.imageUrl = imageUrl;
        this.imageThumbnailUrl = imageThumbnailUrl;
    }

    public ImageProfileModel() {
    }

    public Integer getIdImageProfile() {
        return idImageProfile;
    }

    public void setIdImageProfile(Integer idImageProfile) {
        this.idImageProfile = idImageProfile;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageThumbnailUrl() {
        return imageThumbnailUrl;
    }

    public void setImageThumbnailUrl(String imageThumbnailUrl) {
        this.imageThumbnailUrl = imageThumbnailUrl;
    }

    public ProfileModel getProfile() {
        return profile;
    }

    public void setProfile(ProfileModel Profile) {
        this.profile = Profile;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ImageProfileModel{");
        sb.append("idImageProfile=").append(idImageProfile);
        sb.append(", profile=").append(profile);
        sb.append(", imageUrl='").append(imageUrl).append('\'');
        sb.append(", imageThumbnailUrl='").append(imageThumbnailUrl).append('\'');
        sb.append('}');
        return sb.toString();
    }

}