package itst.social_raccoon_api.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Timestamp;

/**
 * @author Alejandro Tejeda Moreno
 * @version 1.0
 * @apiNote This class is a Data Transfer Object (DTO) that represents a post
 * @see itst.social_raccoon_api.Models.PostModel
 * @see itst.social_raccoon_api.Models.UserModel
 * @see itst.social_raccoon_api.Models.PostDescriptionModel
 * @since 1.0
 */
@Schema(description = "Data Transfer Object representing a post")
public class PostRequestDTO {
    @Schema(description = "Unique identifier of the post", example = "1")
    private Integer idPost;
    @Schema(description = "Description of the post", example = "This is a post")
    private String postDescription;
    @Schema(description = "Unique identifier of the user", example = "1")
    private Integer idUser;
    @Schema(description = "Date when the post was created", example = "2021-12-31 23:59:59")
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