package itst.socialraccoon.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class CommentDTO {
    private Integer idComment;
    @NotNull(message = "The user must not be null")
    @Schema(description = "Unique identifier of the user", example = "1")
    private Integer idUser;
    @NotNull(message = "The post must not be null")
    @Schema(description = "Unique identifier of the post", example = "1")
    private Integer idPost;
    @NotBlank(message = "The comment must not be null and must contain at least one non-whitespace character")
    @Size(min = 1, max = 200, message = "The comment must be at most 200 characters, and has at least one character")
    @Schema(description = "Content of the comment", example = "This is a comment")
    private String comment;
    @Schema(description = "Date when the comment was made", example = "2021-10-10 10:00:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private LocalDateTime date = LocalDateTime.now().withNano(0);
    public CommentDTO() {
    }

    public CommentDTO(Integer idComment, Integer idUser, Integer idPost, String comment, LocalDateTime date) {
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}