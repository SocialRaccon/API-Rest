package itst.social_raccoon_api.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "reaction_types")
public class ReactionTypeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idReactionType;
    private String reactionType;

    public ReactionTypeModel() {
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
