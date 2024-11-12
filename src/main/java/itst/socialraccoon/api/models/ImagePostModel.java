package itst.socialraccoon.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "image_post")
@Schema(description = "Model representing an image post")
public class ImagePostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idImagePost", nullable = false)
    @Schema(description = "Unique identifier of the image post", example = "1")
    private Integer idImagePost;

    @Size(max = 255)
    @NotNull
    @Column(name = "imageUrl", nullable = false)
    @Schema(description = "URL of the image", example = "https://www.example.com/image.jpg")
    private String imageUrl;

    @Size(max = 255)
    @NotNull
    @Column(name = "imageThumbnailUrl", nullable = false)
    @Schema(description = "URL of the image thumbnail", example = "https://www.example.com/image-thumbnail.jpg")
    private String imageThumbnailUrl;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPost", nullable = false)
    @JsonBackReference(value = "post-image")
    @Schema(
            description = "Post to which the image belongs",
            implementation = PostModel.class,
            required = true,
            example = "{\n" +
                    "  \"idPost\": 1\n" +
                    "}",
            name = "idPost",
            type = "object",
            format = "int32"
    )
    private PostModel idPost; 

    // Constructor, Getters and Setters
    public ImagePostModel() {
    }

    public ImagePostModel(Integer idImagePost, String imageUrl, String imageThumbnailUrl, PostModel idPost) {
        this.idImagePost = idImagePost;
        this.imageUrl = imageUrl;
        this.imageThumbnailUrl = imageThumbnailUrl;
        this.idPost = idPost;
    }

    public Integer getIdImagePost() {
        return idImagePost;
    }

    public void setIdImagePost(Integer idImagePost) {
        this.idImagePost = idImagePost;
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

    public PostModel getIdPost() {
        return idPost;
    }

    public void setIdPost(PostModel idPost) {
        this.idPost = idPost;
    }
}