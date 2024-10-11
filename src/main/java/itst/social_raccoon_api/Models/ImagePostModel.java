package itst.social_raccoon_api.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "image_post")
public class ImagePostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImagePost;
    private String url;
    private String thumbnail;
    private Integer idPost;

    public ImagePostModel() {
    }

    public ImagePostModel(Integer idImagePost, String url, String thumbnail, Integer idPost) {
        this.idImagePost = idImagePost;
        this.url = url;
        this.thumbnail = thumbnail;
        this.idPost = idPost;
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

    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }

    @Override
    public String toString() {
        return "ImagePost{" +
                "idImagePost=" + idImagePost +
                ", url='" + url + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", idPost=" + idPost +
                '}';
    }
}
