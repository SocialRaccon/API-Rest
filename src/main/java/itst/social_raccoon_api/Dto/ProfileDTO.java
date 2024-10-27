package itst.social_raccoon_api.Dto;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object representing a user profile")
public class ProfileDTO {

    @Schema(description = "Unique identifier of the profile", example = "1")
    private Integer idProfile;

    @Schema(description = "Description of the profile", example = "This is a description")
    private String description;

    @Schema(description = "Name of the user", example = "Juan")
    private String userName;

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

    public ProfileDTO(Integer idProfile, String description, String userName) {
        this.idProfile = idProfile;
        this.description = description;
        this.userName = userName;
    }

    public ProfileDTO() {
    }
}