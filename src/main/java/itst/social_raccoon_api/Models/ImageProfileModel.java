package itst.social_raccoon_api.Models;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    private ProfileModel profile;

    @Schema(description = "URL of the image", example = "https://www.example.com/image.jpg")
    @Column(name = "imageUrl")
    @Size(max = 255)
    @NotBlank(message = "This content must not be null and must not be empty")
    private String imageUrl;

    @Schema(description = "Thumbnail of the image", example = "https://www.example.com/image.jpg")
    @Column(name = "imageThumbnailUrl")
    @Size(max = 255)
    @NotBlank(message = "This content must not be null and must not be empty")
    private String imageThumbnailUrl;

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