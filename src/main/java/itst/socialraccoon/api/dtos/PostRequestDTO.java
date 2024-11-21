package itst.socialraccoon.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import itst.socialraccoon.api.models.PostDescriptionModel;
import itst.socialraccoon.api.models.PostModel;
import itst.socialraccoon.api.models.UserModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.time.LocalDateTime;


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
    @Size(max = 255, message = "The description must be at most 200 characters")
    private String postDescription;
    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Integer idUser;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Date when the post was created", example = "2021-12-31 23:59:59")
    @JsonIgnore
    private LocalDateTime dateCreated = LocalDateTime.now().withNano(0);

    public PostRequestDTO() {
    }

    public PostRequestDTO(Integer idPost, String postDescription, Integer idUser, LocalDateTime dateCreated) {
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

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }
}