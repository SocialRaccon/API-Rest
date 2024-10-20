package itst.social_raccoon_api.Models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "post")
@Schema(description = "Model representing a post")
public class PostModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPost")
    @Schema(description = "Unique identifier of the post", example = "1")
    private Integer idPost;

    @Column(name = "dateCreated")
    @Schema(description = "Date when the post was created", example = "2021-12-31 23:59:59")
    private Timestamp dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    @JsonBackReference(value = "user-post")
    @Schema(description = "User to which the post belongs")
    private UserModel user;

   // @JsonManagedReference(value = "post-comment")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentModel> comments;

    @JsonManagedReference(value = "post-reaction")
    @OneToMany(mappedBy = "idPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReactionModel> reactions;

    @JsonManagedReference(value = "post-image")
    @OneToMany(mappedBy = "idPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagePostModel> images;

    public PostModel(Integer idPost, UserModel user, Timestamp dateCreated) {
        this.idPost = idPost;
        this.user = user;
        this.dateCreated = dateCreated;
    }

    public PostModel() {
    }
    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel idUser) {
        this.user = idUser;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String toString() {
        return "PostModel{" +
                "idPost=" + idPost +
                ", dateCreated=" + dateCreated +
                '}';
    }
}
