package itst.socialraccoon.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "profile")
@Schema(description = "Model representing a profile")
public class ProfileModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProfile", nullable = false)
    @Schema(description = "Unique identifier of the profile", example = "1")
    private Integer idProfile;

    @Size(max = 150, message = "The description must not exceed 150 characters")
    @Column(name = "description", nullable = false, length = 150)
    @Schema(description = "Description of the profile", example = "This is a description")
    private String description = "";

    @NotNull(message = "The user must not be null")
    @OneToOne
    @JoinColumn(name = "idUser", referencedColumnName = "idUser", nullable = false)
    @JsonBackReference(value = "user-profile")
    @Schema(description = "User to which the profile belongs")
    private UserModel idUser;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference(value = "profile-image")
    private Set<ImageProfileModel> images;

    public Integer getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(Integer id) {
        this.idProfile = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserModel getIdUser() {
        return idUser;
    }

    public void setIdUser(UserModel idUser) {
        this.idUser = idUser;
    }

    public Set<ImageProfileModel> getImages() {
        return images;
    }

    public void setImages(Set<ImageProfileModel> images) {
        this.images = images;
    }

}