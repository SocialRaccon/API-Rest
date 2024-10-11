package itst.social_raccoon_api.Models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "reaction_type")
@Schema(description = "Model representing a reaction type")
public class ReactionTypeModel {

    @Schema(description = "Unique identifier of the reaction type", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReactionType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "reactionIcon", referencedColumnName = "idReactionIcon")
    private ReactionIconModel reactionIcon;

    @Schema(description = "Type of reaction", example = "Like")
    @Column(name = "reactionType")
    @JsonProperty("reactionType")
    private String reactionType;

    public ReactionTypeModel() {
    }

    public ReactionTypeModel(Integer idReactionType, String reactionType) {
        this.idReactionType = idReactionType;
        this.reactionType = reactionType;
    }

    public ReactionTypeModel(String reactionType) {
        this.reactionType = reactionType;
    }

    public Integer getIdReactionType() {
        return idReactionType;
    }

    public void setIdReactionType(Integer idReactionType) {
        this.idReactionType = idReactionType;
    }

    public String getReactionType() {
        return reactionType;
    }

    public void setReactionType(String reactionType) {
        this.reactionType = reactionType;
    }

    @Override
    public String toString() {
        return "ReactionTypeModel{" +
                "idReactionType=" + idReactionType +
                ", reactionType='" + reactionType + '\'' +
                '}';
    }

}