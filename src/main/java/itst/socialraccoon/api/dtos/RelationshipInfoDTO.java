package itst.socialraccoon.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import itst.socialraccoon.api.models.ImageProfileModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;

@Schema(description = "Data Transfer Object representing basic information of a follower")
public class RelationshipInfoDTO {
    @Schema(description = "User id")
    private Integer idUser;
    @NotBlank(message = "The user name must not be null")
    @Schema(description = "Name of the user", example = "John Doe")
    @Size(max = 60, message = "The user name must be at most 60 characters")
    private String userName;

    @Schema(description = "Profile images")
    private Set<ImageProfileModel> images;

    public RelationshipInfoDTO() {
    }

    public RelationshipInfoDTO(Integer idUser, String userName, Set<ImageProfileModel> images) {
        this.idUser = idUser;
        this.userName = userName;
        this.images = images;
    }

    public Set<ImageProfileModel> getImages() {
        return images;
    }

    public void setImages(Set<ImageProfileModel> images) {
        this.images = images;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;

    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;

    }
}