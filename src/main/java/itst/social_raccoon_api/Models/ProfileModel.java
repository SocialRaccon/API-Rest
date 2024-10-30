package itst.social_raccoon_api.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
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

    @Size(max = 150)
    @NotNull
    @Column(name = "description", nullable = false, length = 150)
    @Schema(description = "Description of the profile", example = "This is a description")
    private String description;

    @OneToOne
    @JoinColumn(name = "idUser", referencedColumnName = "idUser", nullable = false)
    @JsonBackReference(value = "user-profile")
    private UserModel idUser;

    @OneToMany(mappedBy = "profile", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<ImageProfileModel> images = new LinkedHashSet<>();

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

}