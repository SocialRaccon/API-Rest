package itst.socialraccoon.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import itst.socialraccoon.api.models.PostDescriptionModel;
import itst.socialraccoon.api.models.PostModel;
import itst.socialraccoon.api.models.UserModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;


/**
 * @author Alejandro Tejeda Moreno
 * @version 1.0
 * @apiNote This class is a Data Transfer Object (DTO) that represents a post
 * @see PostModel
 * @see UserModel
 * @see PostDescriptionModel
 * @since 1.0
 */
@Schema(description = "Data Transfer Object representing a post")
public class PostRequestDTO {
    @NotNull(message = "El ID del post no puede ser nulo")
    private Integer idPost;
    @NotBlank(message = "La descripción del post no puede estar vacía y debe contener al menos un carácter que no sea un espacio en blanco")
    @Size(min = 1, max = 500, message = "La descripción del post debe tener como máximo 500 caracteres y al menos un carácter")
    private String postDescription;
    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Integer idUser;
    @NotNull(message = "La fecha de creación no puede ser nula")
    private Timestamp dateCreated = new Timestamp(System.currentTimeMillis());

    public PostRequestDTO() {
    }

    public PostRequestDTO(Integer idPost, String postDescription, Integer idUser, Timestamp dateCreated) {
        this.idPost = idPost;
        this.postDescription = postDescription;
        this.idUser = idUser;
        this.dateCreated = dateCreated;
    }

    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }
}