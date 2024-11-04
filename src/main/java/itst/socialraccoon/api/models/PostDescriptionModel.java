package itst.socialraccoon.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "post_description")
@Schema(description = "Model representing a post description")
public class PostDescriptionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPostDescription", nullable = false)
    @Schema(description = "Unique identifier of the post description", example = "1")
    private Integer idPostDescription;

    @Size(max = 150)
    @NotNull
    @Column(name = "description", nullable = false, length = 150)
    @Schema(description = "Description of the post", example = "This is a post")
    private String description;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idPost", nullable = false)
    @JsonBackReference(value = "post-description")
    @Schema(description = "Post to which the description belongs")
    private PostModel idPost;

    public Integer getIdPostDescription() {
        return idPostDescription;
    }

    public void setIdPostDescription(Integer idPostDescription) {
        this.idPostDescription = idPostDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PostModel getIdPost() {
        return idPost;
    }

    public void setIdPost(PostModel idPost) {
        this.idPost = idPost;
    }

}