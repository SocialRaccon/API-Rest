package itst.social_raccoon_api.Models;

import jakarta.persistence.GenerationType;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


@Entity(name = "profile")
public class ProfileModel {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProfile;
    private String description;

    @OneToOne
    @JoinColumn(name = "idUser")
    @JsonBackReference
    private UserModel user;


    public int getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(int idProfile) {
        this.idProfile = idProfile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserModel getUser() {
        return user;
    }
    
    public void setUser(UserModel user) {
        this.user = user;
    }
}
