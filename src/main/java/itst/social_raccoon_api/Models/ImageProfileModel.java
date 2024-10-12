package itst.social_raccoon_api.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "image_profile")
@Schema(description = "Model representing an image profile", name = "ImageProfile")
public class ImageProfileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idImageProfile", nullable = false)
    @Schema(description = "Unique identifier of the image profile", example = "1")
    private Integer idImageProfile;

    @Size(max = 255)
    @NotNull
    @Column(name = "imageUrl", nullable = false)
    @Schema(description = "URL of the image", example = "https://www.example.com/image.jpg")
    private String imageUrl;

    @Size(max = 255)
    @NotNull
    @Column(name = "imageThumbnailUrl", nullable = false)
    @Schema(description = "Thumbnail of the image", example = "https://www.example.com/thumbnail.jpg")
    private String imageThumbnailUrl;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idProfile", nullable = false)
    @Schema(description = "User to which the image profile belongs", example = "1")
    private ProfileModel profile;

    public Integer getId() {
        return idImageProfile;
    }

    public void setId(Integer id) {
        this.idImageProfile = id;
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

    public void setProfile(ProfileModel profile) {
        this.profile = profile;
    }
}