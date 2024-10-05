package itst.social_raccoon_api.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "imagepost")
public class ImagePost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idImagePost;
    private String url;
    private String thumbnail;
    private int idPost;

    public int getIdImagePost() {
        return idImagePost;
    }

    public void setIdImagePost(int idImagePost) {
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

    public int getIdPost() {
        return idPost;
    }

    public void setIdPost(int idPost) {
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
