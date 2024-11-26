package itst.socialraccoon.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import itst.socialraccoon.api.models.ImageProfileModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;


@Schema(description = "Data Transfer Object representing a user profile")
public class ProfileDTO {

    @Schema(description = "Unique identifier of the profile", example = "1")
    @NotNull(message = "El ID del perfil no puede ser nulo")
    private Integer idProfile;

    @Schema(description = "Description of the profile", example = "This is a description")
    @NotBlank(message = "La descripción no puede estar vacía y debe contener al menos un carácter que no sea un espacio en blanco")
    @Size(min = 1, max = 500, message = "La descripción debe tener como máximo 500 caracteres y al menos un carácter")
    private String description;

    @Schema(description = "Name of the user", example = "Juan")
    @NotBlank(message = "El nombre no puede estar vacío y debe contener al menos un carácter que no sea un espacio en blanco")
    @Size(min = 1, max = 100, message = "El nombre debe tener como máximo 100 caracteres y al menos un carácter")
    private String userName;

    @Schema(description = "Profile images")
    private Set<ImageProfileModel> images;

    @Schema(description = "Number of followers", example = "100")
    private int followersCount;

    @Schema(description = "Number of following", example = "50")
    private int followingCount;

    @Schema(description = "Control number of the user", example = "21TE0284")
    @NotNull(message = "The control number must not be null")
    @Size(min = 8, max = 8, message = "The control number must be 8 characters long")
    private String controlNumber;

    public ProfileDTO() {
    }

    public ProfileDTO(Integer idProfile, String description, String userName, Set<ImageProfileModel> images, int followersCount, int followingCount, String controlNumber) {
        this.idProfile = idProfile;
        this.description = description;
        this.userName = userName;
        this.images = images;
        this.followersCount = followersCount;
        this.followingCount = followingCount;
        this.controlNumber = controlNumber;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Set<ImageProfileModel> getImages() {
        return images;
    }

    public void setImages(Set<ImageProfileModel> images) {
        this.images = images;
    }

    public int getFollowersCount() {
        return followersCount;
    }

    public void setFollowersCount(int followersCount) {
        this.followersCount = followersCount;
    }

    public int getFollowingCount() {
        return followingCount;
    }

    public void setFollowingCount(int followingCount) {
        this.followingCount = followingCount;
    }

    @Override
    public String toString() {
        return "ProfileDTO{" +
                "idProfile=" + idProfile +
                ", description='" + description + '\'' +
                ", userName='" + userName + '\'' +
                ", images=" + images +
                ", followersCount=" + followersCount +
                ", followingCount=" + followingCount +
                '}';
    }
}