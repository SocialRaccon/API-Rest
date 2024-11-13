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
import jakarta.validation.constraints.Pattern;
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

    @NotNull(message = "The URL of the image must not be null")
    @Size(min = 1,max = 500, message = "The URL of the image must not exceed 500 characters")
    @Column(name = "imageUrl", nullable = false, length = 500)
    @Pattern(regexp = "^(http|https)://firebasestorage\\.googleapis\\.com/v0/b/[^/]+/o/[^?]+\\.(jpg|jpeg|png)\\?alt=media$",
            message = "The URL of the image must be a valid Firebase Storage URL ending in JPG, JPEG, or PNG")
    @Schema(description = "URL of the image", example = "https://www.example.com/image.jpg")
    private String imageUrl;

    @NotNull(message = "The URL of the image thumbnail must not be null")
    @Pattern(regexp = "^(http|https)://firebasestorage\\.googleapis\\.com/v0/b/[^/]+/o/[^?]+\\.(jpg|jpeg|png)\\?alt=media$",
            message = "The URL of the image must be a valid Firebase Storage URL ending in JPG, JPEG, or PNG")
    @Size(min = 1,max = 500, message = "The URL of the image thumbnail must not exceed 500 characters")
    @Column(name = "imageThumbnailUrl", nullable = false, length = 500)
    @Schema(description = "URL of the image thumbnail", example = "https://www.example.com/image-thumbnail.jpg")
    private String imageThumbnailUrl;

    @NotNull(message = "The post must not be null")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPost", nullable = false)
    @JsonBackReference(value = "post-image")
    @Schema(description = "Post where the image was uploaded", example = "1")
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