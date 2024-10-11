package itst.social_raccoon_api.Models;

import jakarta.persistence.GenerationType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity(name = "career")
public class CareerModel {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCareer;
    private String careerName;

    @OneToMany(mappedBy = "career", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<UserModel> user = new ArrayList<>();

    public int getIdCareer() {
        return idCareer;
    }

    public void setIdCareer(int idCareer) {
        this.idCareer = idCareer;
    }

    public String getCareerName() {
        return careerName;
    }

    public void setCareerName(String careerName) {
        this.careerName = careerName;
    }


    public List<UserModel> getUser() {
        return user;
    }

    public void setUsers(List<UserModel> user) {
        this.user = user;
    }
    
    
    

    
}
