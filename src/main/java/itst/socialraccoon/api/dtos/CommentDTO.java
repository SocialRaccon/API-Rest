package itst.socialraccoon.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CommentDTO {
    private Integer idComment;
    private Integer idUser;
    private Integer idPost;
    
    @NotBlank(message = "The comment must not be null and must contain at least one non-whitespace character")
    @Size(min = 1, max = 200, message = "The comment must be at most 200 characters, and has at least one character")
    @Schema(description = "Content of the comment", example = "This is a comment")
    private String comment;

    @NotNull(message = "The date must not be null")
    @Schema(description = "Date when the comment was made", example = "2021-10-10 10:00:00")
    private String date;

    public CommentDTO() {
    }

    public CommentDTO(Integer idComment, Integer idUser, Integer idPost, String comment, String date) {
        this.idComment = idComment;
        this.idUser = idUser;
        this.idPost = idPost;
        this.comment = comment;
        this.date = date;
    }

    public Integer getIdComment() {
        return idComment;
    }

    public void setIdComment(Integer idComment) {
        this.idComment = idComment;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}