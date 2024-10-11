package itst.social_raccoon_api.Models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Model representing a reaction icon")
@Entity
@Table(name = "reaction_icon")
public class ReactionIconModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the reaction icon", example = "1")
    @Column(name = "idReactionIcon")
    private Integer idReactionIcon;

    @Schema(description = "Thumbnail of the reaction icon", example = "https://www.example.com/icon.jpg")
    @Column(name = "thumbnail")
    @Size(max = 255)
    @NotBlank(message = "This content must not be null and must not be empty")
    private String thumbnail;

    public ReactionIconModel(Integer IdReactionIcon, String thumbnail) {
        this.idReactionIcon = IdReactionIcon;
        this.thumbnail = thumbnail;
    }

    public ReactionIconModel() {
    }

    public Integer getIdReactionIcon() {
        return idReactionIcon;
    }
    public void setIdReactionIcon(Integer idReactionIcon) {
        this.idReactionIcon = idReactionIcon;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(String icon) {
        this.thumbnail = icon;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ReactionIconModel{");
        sb.append("IdReactionIcon=").append(idReactionIcon);
        sb.append(", Icon=").append(thumbnail);
        sb.append('}');
        return sb.toString();
    }
}