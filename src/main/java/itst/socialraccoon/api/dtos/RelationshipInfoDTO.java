package itst.socialraccoon.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Data Transfer Object representing basic information of a follower")
public class RelationshipInfoDTO {
    @Schema(description = "User id")
    private Integer idUser;
    @NotBlank(message = "The user name must not be null")
    @Schema(description = "Name of the user", example = "John Doe")
    @Size(max = 60, message = "The user name must be at most 60 characters")
    private String userName;

    public RelationshipInfoDTO() {
    }

    public RelationshipInfoDTO(Integer idUser, String userName) {
        this.idUser = idUser;
        this.userName = userName;
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
