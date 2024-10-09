package itst.social_raccoon_api.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "imageprofile")
public class ImageProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idImageProfile;
    private String url;
    private String thumbnail;
    private int idProfile;

    public int getIdImageProfile() {
        return idImageProfile;
    }

    public void setIdImageProfile(int idImageProfile) {
        this.idImageProfile = idImageProfile;
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

    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    @Override
    public String toString() {
        return "ProfileImage{" +
               "idImageProfile=" + idImageProfile +
               ", url='" + url + '\'' +
               ", thumbnail='" + thumbnail + '\'' +
               ", idProfile=" + idProfile +
               '}';
    }
}
