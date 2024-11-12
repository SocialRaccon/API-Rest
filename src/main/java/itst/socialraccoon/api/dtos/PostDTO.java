package itst.socialraccoon.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import itst.socialraccoon.api.models.ImagePostModel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Data Transfer Object representing a post")
public class PostDTO {
    @NotNull(message = "El ID del post no puede ser nulo")
    private Integer post;

    @NotNull(message = "La fecha de creación no puede ser nula")
    private Timestamp dateCreated;

    @NotNull(message = "El ID del usuario no puede ser nulo")
    private Integer idUser;

    @NotBlank(message = "El nombre no puede estar vacío y debe contener al menos un carácter que no sea un espacio en blanco")
    @Size(min = 1, max = 100, message = "El nombre debe tener como máximo 100 caracteres y al menos un carácter")
    private String userName;

    @NotBlank(message = "El apellido no puede estar vacío y debe contener al menos un carácter que no sea un espacio en blanco")
    @Size(min = 1, max = 100, message = "El apellido debe tener como máximo 100 caracteres y al menos un carácter")
    private String userLastName;

    @NotBlank(message = "El segundo apellido no puede estar vacío y debe contener al menos un carácter que no sea un espacio en blanco")
    @Size(min = 1, max = 100, message = "El segundo apellido debe tener como máximo 100 caracteres y al menos un carácter")
    private String userSecondLastName;

    @NotBlank(message = "El número de control no puede estar vacío y debe contener al menos un carácter que no sea un espacio en blanco")
    @Size(min = 1, max = 50, message = "El número de control debe tener como máximo 50 caracteres y al menos un carácter")
    private String userControlNumber;

    @NotBlank(message = "La descripción del post no puede estar vacía y debe contener al menos un carácter que no sea un espacio en blanco")
    @Size(min = 1, max = 500, message = "La descripción del post debe tener como máximo 500 caracteres y al menos un carácter")
    private String postDescription;

    private List<CommentDTO> comments = new ArrayList<>();
    private List<ReactionDTO> reactions = new ArrayList<>();
    private List<ImagePostModel> images = new ArrayList<>();

    public PostDTO() {
    }

    public PostDTO(
            Integer idPost,
            Timestamp dateCreated,
            Integer idUser,
            String userName,
            String userLastName,
            String userSecondLastName,
            String userEmail,
            String userControlNumber,
            String postDescription,
            List<CommentDTO> comments,
            List<ReactionDTO> reactions,
            List<ImagePostModel> images) {
        this.post = idPost;
        this.dateCreated = dateCreated;
        this.idUser = idUser;
        this.userName = userName;
        this.userLastName = userLastName;
        this.userSecondLastName = userSecondLastName;
        this.userControlNumber = userControlNumber;
        this.postDescription = postDescription;
        this.comments = comments;
        this.reactions = reactions;
        this.images = images;
    }

    public Integer getIdPost() {
        return post;
    }

    public void setIdPost(Integer idPost) {
        this.post = idPost;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
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

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserSecondLastName() {
        return userSecondLastName;
    }

    public void setUserSecondLastName(String userSecondLastName) {
        this.userSecondLastName = userSecondLastName;
    }

    public String getUserControlNumber() {
        return userControlNumber;
    }

    public void setUserControlNumber(String userControlNumber) {
        this.userControlNumber = userControlNumber;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public List<CommentDTO> getComments() {
        return comments;
    }

    public void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public List<ReactionDTO> getReactions() {
        return reactions;
    }

    public void setReactions(List<ReactionDTO> reactions) {
        this.reactions = reactions;
    }

    public List<ImagePostModel> getImages() {
        return images;
    }

    public void setImages(List<ImagePostModel> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "idPost=" + post +
                ", dateCreated=" + dateCreated +
                ", idUser=" + idUser +
                ", userName='" + userName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userSecondLastName='" + userSecondLastName + '\'' +
                ", userControlNumber='" + userControlNumber + '\'' +
                ", postDescription='" + postDescription + '\'' +
                ", comments=" + comments +
                ", reactions=" + reactions +
                ", images=" + images +
                '}';
    }
}