package itst.socialraccoon.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.type.DateTime;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Schema(description = "Model representing a comment")
@Entity
@Table(name = "comment")
public class CommentModel {

    @Schema(description = "Unique identifier of the comment", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComment;

    @NotNull(message = "The user must not be null")
    @Schema(description = "User who made the comment", example = "1")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    @JsonBackReference(value = "user-comment")
    private UserModel user;

    @Schema(description = "Post where the comment was made", nullable = false, example = "1")
    @NotNull(message = "The post must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPost")
    @JsonBackReference(value = "post-comment")
    private PostModel post;

    @NotBlank(message = "The comment must not be null and must contain at least one non-whitespace character")
    @Size(min = 1, max = 200, message = "The comment must be at most 200 characters, and has at least one character")
    @Schema(description = "Content of the comment", example = "This is a comment")
    @Column(name = "comment", nullable = false, length = 200)
    private String comment;

    @Schema(description = "Date when the comment was made", example = "2021-10-10 10:00:00")
    @Column(name = "date", nullable = false, columnDefinition = "datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonIgnore
    private LocalDateTime date = LocalDateTime.now().withNano(0);

    public CommentModel(String comment, UserModel user, PostModel post) {
        this.comment = comment;
        this.user = user;
        this.post = post;
    }

    public CommentModel() {

    }

    public Integer getIdComment() {
        return idComment;
    }

    public void setIdComment(Integer idComment) {
        this.idComment = idComment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel idUser) {
        this.user = idUser;
    }

    public PostModel getPost() {
        return post;
    }

    public void setPost(PostModel idPost) {
        this.post = idPost;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "CommentModel{" +
                "idComment=" + idComment +
                ", user=" + user +
                ", post=" + post +
                ", comment='" + comment + '\'' +
                ", date=" + date +
                '}';
    }
}