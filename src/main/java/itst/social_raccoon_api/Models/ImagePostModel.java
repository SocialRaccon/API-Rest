package itst.social_raccoon_api.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity()
@Table(name = "image_post")
@Schema(description = "Model representing an image post")
public class ImagePostModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the image post", example = "1")
    private Integer idImagePost;

    @Schema(description = "URL of the image", example = "https://www.example.com/image.jpg")
    private String imageUrl;

    @Schema(description = "Thumbnail of the image", example = "https://www.example.com/thumbnail.jpg")
    private String imageThumbnailUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPost")
    @Schema(description = "Post to which the image belongs")
    @JsonBackReference(value = "post-image")
    private PostModel idPost;

    public ImagePostModel() {
    }

    public ImagePostModel(Integer idImagePost, String url, String thumbnail, PostModel idPost) {
        this.idImagePost = idImagePost;
        this.imageUrl = url;
        this.imageThumbnailUrl = thumbnail;
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

    public void setImageUrl(String url) {
        this.imageUrl = url;
    }

    public String getImageThumbnailUrl() {
        return imageThumbnailUrl;
    }

    public void setImageThumbnailUrl(String thumbnail) {
        this.imageThumbnailUrl = thumbnail;
    }

    public PostModel getIdPost() {
        return idPost;
    }

    public void setIdPost(PostModel idPost) {
        this.idPost = idPost;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ImagePostModel{");
        sb.append("idImagePost=").append(idImagePost);
        sb.append(", url=").append(imageUrl);
        sb.append(", thumbnail=").append(imageThumbnailUrl);
        sb.append('}');
        return sb.toString();
    }
}
