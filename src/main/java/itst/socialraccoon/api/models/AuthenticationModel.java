package itst.socialraccoon.api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@Entity(name = "authentication")
@Schema(description = "Model representing an authentication")
public class AuthenticationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the authentication", example = "1")
    private Integer idAuthentication;

    @Column(nullable = false, unique = true, length = 60)
    @Size(min = 1, max = 60, message = "The email must not exceed 60 characters")
    @Schema(description = "Email of the user", example = "L21TE0279@teziutlan.tecnm.mx", format = "email")
    @NotNull(message = "email must not be null and must not be empty")
    @Email(message = "Email should be valid", regexp = "[a-zA-Z0-9.]+@teziutlan\\.tecnm\\.mx")
    private String email;

    @Schema(description = "Password of the user", example = "1234!#$", format = "password")
    @Column(nullable = false, length = 60)
    @Size(min = 8, max = 60, message = "The password must be at least 8 characters long")
    @NotNull(message = "password must not be null and must not be empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "The password must contain at least one lowercase letter, one uppercase letter, one digit, and must be at least 8 characters long")
    private String password;

    @OneToOne(mappedBy = "authentication", fetch = FetchType.LAZY)
    @JsonIgnore
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