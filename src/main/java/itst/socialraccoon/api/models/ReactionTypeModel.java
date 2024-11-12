package itst.socialraccoon.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "reaction_type")
@Schema(description = "Model representing a reaction type")
public class ReactionTypeModel {

    @Schema(description = "Unique identifier of the reaction type", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idReactionType")
    @JsonProperty("idReactionType")
    private Integer idReactionType;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idReactionIcon")
    @JsonProperty("reactionIcon")
    @Schema(description = "Icon of the reaction", example = "1")
    private ReactionIconModel reactionIcon;

    @Schema(description = "Name of the reaction", example = "Like")
    @Column(name = "name", nullable = false, length = 30)
    @NotBlank(message = "The reaction name must not be empty")
    @Pattern(regexp = "^(me enmapacha|me gusta|me enoja|me divierte)$",
            message = "The reaction name must be one of the following: me enmapacha, me gusta, me enoja, me divierte")
    @Size(max = 30, message = "The reaction name must be at most 30 characters")
    private String name;

    public ReactionTypeModel() {
    }

    public ReactionTypeModel(Integer idReactionType, ReactionIconModel reactionIcon, String name) {
        this.idReactionType = idReactionType;
        this.reactionIcon = reactionIcon;
        this.name = name;
    }

    public Integer getIdReactionType() {
        return idReactionType;
    }

    public void setIdReactionType(Integer idReactionType) {
        this.idReactionType = idReactionType;
    }

    public ReactionIconModel getReactionIcon() {
        return reactionIcon;
    }

    public void setReactionIcon(ReactionIconModel reactionIcon) {
        this.reactionIcon = reactionIcon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ReactionTypeModel{" +
                "idReactionType=" + idReactionType +
                ", reactionIcon=" + reactionIcon +
                ", name='" + name + '\'' +
                '}';
    }

}