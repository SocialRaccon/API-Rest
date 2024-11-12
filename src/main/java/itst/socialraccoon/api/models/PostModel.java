package itst.socialraccoon.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
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

    @Column(name = "dateCreated", nullable = false, columnDefinition = "datetime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "Date when the post was created", example = "2021-12-31 23:59:59")
    @JsonIgnore
    private LocalDateTime dateCreated;

    @NotNull(message = "El usuario no puede ser nulo")
    @ManyToOne()
    @JoinColumn(name = "idUser")
    @JsonBackReference(value = "user-post")
    @Schema(description = "User to which the post belongs")
    private UserModel user;

    @OneToOne(mappedBy = "idPost", cascade = CascadeType.ALL, orphanRemoval = true, optional = true)
    @JsonManagedReference(value = "post-description")
    @Schema(description = "Description of the post")
    private PostDescriptionModel idPostDescription;

    @JsonManagedReference(value = "post-comment")
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Comments made on the post")
    private List<CommentModel> comments = new ArrayList<>();

    @JsonManagedReference(value = "post-reaction")
    @OneToMany(mappedBy = "idPost", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Reactions made on the post")
    private List<ReactionModel> reactions = new ArrayList<>();

    @JsonManagedReference(value = "post-image")
    @OneToMany(mappedBy = "idPost", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Images uploaded to the post")
    private List<ImagePostModel> images = new ArrayList<>();

    public PostModel(Integer idPost, UserModel user, LocalDateTime dateCreated) {
        this.idPost = idPost;
        this.user = user;
        this.dateCreated = dateCreated;
    }

    public PostModel() {
        this.dateCreated = LocalDateTime.now();
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

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated.withNano(0);
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