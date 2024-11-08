package itst.socialraccoon.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


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