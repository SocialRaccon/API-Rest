package itst.social_raccoon_api.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity(name = "image_profile")
public class ImageProfileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idImageProfile;

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    @JsonBackReference(value = "user-image_profile")
    private UserModel user;

    private String url;
    private String thumbnail;

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

    public UserModel getIdProfile() {
        return user;
    }

    public void setIdProfile(UserModel idUser) {
        this.user = idUser;
    }
}
