package itst.social_raccoon_api.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Model representing an image post")
@Entity
@Table(name = "image_post")
public class ImagePostModel {

    @Schema(description = "Unique identifier of the image post", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idImagePost")
    @JsonProperty("idImagePost")
    private Integer idImagePost;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idPost")
    @JsonProperty("idPost")
    @Schema(description = "Post to which the image belongs", example = "1")
    private PostModel post;

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

    public ImagePostModel(Integer idImagePost, String imageUrl, String imageThumbnailUrl, PostModel post) {
        this.idImagePost = idImagePost;
        this.imageUrl = imageUrl;
        this.imageThumbnailUrl = imageThumbnailUrl;
        this.post = post;
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

    public PostModel getPost() {
        return post;
    }

    public void setPost(PostModel post) {
        this.post = post;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ImagePostModel{");
        sb.append("idImagePost=").append(idImagePost);
        sb.append(", url='").append(imageUrl).append('\'');
        sb.append(", imageThumbnailUrl='").append(imageThumbnailUrl).append('\'');
        sb.append(", post=").append(post);
        sb.append('}');
        return sb.toString();
    }
}
