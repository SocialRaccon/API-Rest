package itst.social_raccoon_api.Models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "image_profile")
public class ImageProfileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImageProfile;
    private String url;
    private String thumbnail;
    private Integer idUser;

    public ImageProfileModel(Integer idImageProfile, String url, String thumbnail, Integer idUser) {
        this.idImageProfile = idImageProfile;
        this.url = url;
        this.thumbnail = thumbnail;
        this.idUser = idUser;
    }

    public Integer getIdImageProfile() {
        return idImageProfile;
    }

    public void setIdImageProfile(Integer idImageProfile) {
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

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

}