package itst.socialraccoon.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;

@Schema(description = "Model representing a reaction icon")
@Entity
@Table(name = "reaction_icon")
public class ReactionIconModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReactionIcon")
    @JsonProperty("idReactionIcon")
    @Schema(description = "Unique identifier of the reaction icon", example = "1")
    private Integer idReactionIcon;

    @Schema(description = "URL of the reaction icon", example = "https://www.example.com/icon.jpg")
    @Column(name = "iconUrl")
    @Size(min = 1, max = 500, message = "The icon URL must be at most 500 characters")
    @NotBlank(message = "The icon URL must not be empty")
    @Pattern(regexp = "^(http|https)://.*\\.(jpg|jpeg|png)$",
            message = "The icon URL must be a valid image URL ending in JPG, JPEG, or PNG")
    private String iconUrl;

    @Schema(description = "Thumbnail of the reaction icon", example = "https://www.example.com/icon-thumbnail.jpg")
    @Column(name = "iconThumbnailUrl")
    @Size(min = 1, max = 500, message = "The icon thumbnail URL must be at most 500 characters")
    @NotBlank(message = "The icon thumbnail URL must not be empty")
    @Pattern(regexp = "^(http|https)://.*\\.(jpg|jpeg|png)$",
            message = "The icon thumbnail URL must be a valid image URL ending in JPG, JPEG, or PNG")
    private String iconThumbnailUrl;

    public ReactionIconModel(Integer idReactionIcon, String iconUrl, String iconThumbnailUrl) {
        this.idReactionIcon = idReactionIcon;
        this.iconUrl = iconUrl;
        this.iconThumbnailUrl = iconThumbnailUrl;
    }

    public ReactionIconModel() {
    }

    public Integer getIdReactionIcon() {
        return idReactionIcon;
    }

    public void setIdReactionIcon(Integer idReactionIcon) {
        this.idReactionIcon = idReactionIcon;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String icon) {
        this.iconUrl = icon;
    }

    public String getIconThumbnailUrl() {
        return iconThumbnailUrl;
    }

    public void setIconThumbnailUrl(String iconThumbnailUrl) {
        this.iconThumbnailUrl = iconThumbnailUrl;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ReactionIconModel{");
        sb.append("IdReactionIcon=").append(idReactionIcon);
        sb.append(", Icon=").append(iconUrl);
        sb.append('}');
        return sb.toString();
    }
}