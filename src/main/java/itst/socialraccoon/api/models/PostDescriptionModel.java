package itst.socialraccoon.api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @Size(max = 150, message = "La descripción debe tener como máximo 150 caracteres")
    @NotBlank(message = "La descripción no puede estar vacía y debe contener al menos un carácter que no sea un espacio en blanco")
    @Column(name = "description", nullable = false, length = 150)
    @Schema(description = "Description of the post", example = "This is a post")
    private String description;

    @NotNull(message = "El post no puede ser nulo")
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