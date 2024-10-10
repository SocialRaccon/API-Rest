package itst.social_raccoon_api.Models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class ReactionTypeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdReactionType;

    @OneToOne(mappedBy = "reactionType", cascade = CascadeType.ALL)
    private ReactionModel reactionModel;

    @OneToOne
    @JoinColumn(name = "IdReactionIcon")
    private ReactionIconModel reactionIconModel;

    public ReactionTypeModel(int idReactionType) {
        IdReactionType = idReactionType;
    }

    public ReactionTypeModel() {
    }

    public int getIdReactionType() {
        return IdReactionType;
    }

    public void setIdReactionType(int idReactionType) {
        IdReactionType = idReactionType;
    }

    @Override
    public String toString() {
        return "Reaction_TypeModel [IdReactionType=" + IdReactionType + "]";
    }

    
    
}
