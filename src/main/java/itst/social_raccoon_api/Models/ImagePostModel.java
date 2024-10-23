package itst.social_raccoon_api.Models;

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
    private Integer idImagePost;

    @Size(max = 255)
    @NotNull
    @Column(name = "imageUrl", nullable = false)
    private String imageUrl;

    @Size(max = 255)
    @NotNull
    @Column(name = "imageThumbnailUrl", nullable = false)
    private String imageThumbnailUrl;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPost", nullable = false)
    @JsonBackReference(value = "post-image")
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