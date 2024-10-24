package itst.social_raccoon_api.Models;

import jakarta.persistence.GenerationType;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;


@Entity(name = "authentication")
public class AuthenticationModel {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAuthentication;
    private String email;
    private String password;

    @OneToOne
    @JoinColumn(name = "idUser")
    @JsonBackReference
    private UserModel user;

    public int getIdAuthentication() {
        return idAuthentication;
    }

    public void setIdAuthentication(int idAuthentication) {
        this.idAuthentication = idAuthentication;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserModel getUser() {
        return user;
    }
    
    public void setUser(UserModel user) {
        this.user = user;
    }
}
