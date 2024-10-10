package itst.social_raccoon_api.Models;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "user")
public class UserModel {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUser;

    @JsonManagedReference(value = "user-post")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostModel> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference(value = "user-follower")
    private List<FollowerModel> followers;

    @OneToMany(mappedBy = "followerUser", cascade = CascadeType.ALL, orphanRemoval = true)
    //@JsonManagedReference(value = "user-following")
    private List<FollowerModel> following;

    @JsonManagedReference(value = "user-comment")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentModel> comments;

    @NotBlank(message = "This content must not be null and must not be empty")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    @Column(nullable = false, name = "name")
    @Schema(description = "Name of the user", example = "Juan")
    @JsonProperty("name")
    private String name;

    @Column(nullable = false)
    @Schema(description = "Last name of the user", example = "Perez")
    private String lastName;

    @Column(nullable = false)
    @Schema(description = "Second last name of the user", example = "Gomez")
    private String secondLastName;

    @Column(nullable = false)
    @Schema(description = "Email of the user", example = "alex2227@hotmail.com")
    private String email;

    @Column(nullable = false)
    @Schema(description = "Control number of the user", example = "21TE284")
    private String controlNumber;

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }

}
