package itst.social_raccoon_api.Models;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;


@Entity(name = "authentication")
public class AuthenticationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAuthentication;

    @Column(nullable = false, unique = true)
    @Schema(description = "Email of the user", example = "josuejoss10@gmail.com")
    private String email;


    @Schema(description = "Password of the user", example = "1234!#$")
    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "authentication", fetch = FetchType.LAZY)
    private UserModel user;

    public AuthenticationModel() {
    }

    public AuthenticationModel(Integer idAuthentication, String email, String password, UserModel user) {
        this.idAuthentication = idAuthentication;
        this.email = email;
        this.password = password;
        this.user = user;
    }

    public Integer getIdAuthentication() {
        return idAuthentication;
    }

    public void setIdAuthentication(Integer idAuthentication) {
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
