package itst.social_raccoon_api.Models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    private Timestamp dateCreated = new Timestamp(System.currentTimeMillis());

    @ManyToOne()
    @JoinColumn(name = "idUser")
    @JsonBackReference(value = "user-post")
    @Schema(description = "User to which the post belongs")
    private UserModel user;

    @OneToOne(mappedBy = "idPost", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "post-description")
    private PostDescriptionModel idPostDescription;

    @JsonManagedReference(value = "post-comment")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CommentModel> comments = new ArrayList<>();

    @JsonManagedReference(value = "post-reaction")
    @OneToMany(mappedBy = "idPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReactionModel> reactions = new ArrayList<>();

    @JsonManagedReference(value = "post-image")
    @OneToMany(mappedBy = "idPost", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImagePostModel> images = new ArrayList<>();

    public PostModel(Integer idPost, UserModel user, Timestamp dateCreated) {
        this.idPost = idPost;
        this.user = user;
        this.dateCreated = dateCreated;
        this.dateCreated = new Timestamp(System.currentTimeMillis());
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
    public PostDescriptionModel getIdPostDescription() {
        return idPostDescription;
    }
    public void setIdPostDescription(PostDescriptionModel idPostDescription) {
        this.idPostDescription = idPostDescription;
    }
    public List<CommentModel> getComments() {
        return comments;
    }
    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }
    public List<ReactionModel> getReactions() {
        return reactions;
    }
    public void setReactions(List<ReactionModel> reactions) {
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
        return "PostModel{" +
                "idPost=" + idPost +
                ", dateCreated=" + dateCreated +
                '}';
    }

}