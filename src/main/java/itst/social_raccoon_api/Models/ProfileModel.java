package itst.social_raccoon_api.Models;

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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUser", nullable = false)
    @Schema(description = "User to which the profile belongs")
    private UserModel idUser;

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