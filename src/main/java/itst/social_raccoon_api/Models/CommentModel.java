package itst.social_raccoon_api.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import itst.social_raccoon_api.Models.CompositeKeys.CommentPK;
import jakarta.persistence.*;

import java.sql.Timestamp;

@Schema(description = "Model representing a comment")
@Entity
@Table(name = "comment")
@IdClass(CommentPK.class)
public class CommentModel {

    @Schema(description = "Unique identifier of the comment", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idComment;

    @Schema(description = "User who made the comment")
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    @JsonBackReference(value = "user-comment")
    private UserModel user;

    @Schema(description = "Post where the comment was made")
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPost")
    @JsonBackReference(value = "post-comment")
    private PostModel post;

    @Schema(description = "Content of the comment", example = "This is a comment")
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
}
