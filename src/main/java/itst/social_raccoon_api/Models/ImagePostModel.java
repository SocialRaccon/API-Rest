package itst.social_raccoon_api.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity(name = "image_post")
public class ImagePostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImagePost;
    
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPost")
    @JsonBackReference(value = "post-image_post")
    private PostModel post;
    
    private String url;
    private String thumbnail;
    
    public ImagePostModel() {
    }

    public ImagePostModel(Integer idImagePost, String url, String thumbnail, PostModel post) {
        this.idImagePost = idImagePost;
        this.url = url;
        this.thumbnail = thumbnail;
        this.post = post;
    }
    
    public Integer getIdImagePost() {
        return idImagePost;
    }

    public void setIdImagePost(Integer idImagePost) {
        this.idImagePost = idImagePost;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public PostModel getPost() {
        return post;
    }

    public void setPost(PostModel idPost) {
        this.post = idPost;
    }
}
