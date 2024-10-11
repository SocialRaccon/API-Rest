package itst.social_raccoon_api.Models;

import jakarta.persistence.GenerationType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;


@Entity(name = "user")
public class UserModel {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;
    private String name;
    private String lastName;
    private String secondLastName;
    private String controlNumber;

    @OneToOne (mappedBy = "user", cascade = CascadeType.ALL)
    private AuthenticationModel authentication;

    @OneToOne (mappedBy = "user", cascade = CascadeType.ALL)
    private ProfileModel profile;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCareer")
    @JsonBackReference
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private CareerModel career;

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }
    
    public AuthenticationModel getAuthentication() {
        return authentication;
    }
    
    public void setAuthentication(AuthenticationModel authentication) {
        this.authentication = authentication;
    }

    public ProfileModel getProfile() {
        return profile;
    }
    
    public void setProfile(ProfileModel profile) {
        this.profile = profile;
    }

    public CareerModel getCareer() {
        return career;
    }
    
    public void setCareer(CareerModel career) {
        this.career = career;
    }
    
    

    
}
