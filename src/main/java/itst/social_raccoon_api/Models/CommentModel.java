package itst.social_raccoon_api.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Schema(description = "Model representing a comment")
@Entity
@Table(name = "comment")
public class CommentModel {

    @Schema(description = "Unique identifier of the comment", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComment;

    @Schema(description = "User who made the comment")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    @JsonBackReference(value = "user-comment")
    private UserModel user;

    @Schema(description = "Post where the comment was made")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPost")
    @JsonBackReference(value = "post-comment")
    private PostModel post;

    @Schema(description = "Content of the comment", example = "This is a comment")
    @Column(name = "comment", nullable = false)
    private String comment;

    @Schema(description = "Date when the comment was made", example = "2021-10-10 10:00:00")
    private Timestamp date;

    public CommentModel(String comment, UserModel user, Timestamp date, PostModel post) {
        this.comment = comment;
        this.user = user;
        this.date = date;
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

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public PostModel getPost() {
        return post;
    }

    public void setPost(PostModel idPost) {
        this.post = idPost;
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