package itst.socialraccoon.api.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommentRequestDTO {
    private Integer idComment;
    @NotNull(message = "The user must not be null")
    @Schema(description = "Unique identifier of the user", example = "1")
    private Integer idUser;
    @NotBlank(message = "The comment must not be null and must contain at least one non-whitespace character")
    @Size(min = 1, max = 200, message = "The comment must be at most 200 characters, and has at least one character")
    @Schema(description = "Content of the comment", example = "This is a comment")
    private String comment;

    public CommentRequestDTO() {
    }

    public CommentRequestDTO(Integer idComment, Integer idUser, String comment) {
        this.idComment = idComment;
        this.idUser = idUser;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
