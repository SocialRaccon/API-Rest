package itst.social_raccoon_api.Models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class ReactionIconModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdReactionIcon;

    @OneToOne(mappedBy = "ReactionIconModel", cascade = CascadeType.ALL)
    private ReactionTypeModel reactionTypeModel;

    public ReactionIconModel(int reactionIcon) {
        IdReactionIcon = reactionIcon;
    }

    public ReactionIconModel() {
    }

    public int getReactionIcon() {
        return IdReactionIcon;
    }

    public void setReactionIcon(int reactionIcon) {
        IdReactionIcon = reactionIcon;
    }

    @Override
    public String toString() {
        return "Reaction_IconModel [ReactionIcon=" + IdReactionIcon + "]";
    }
    
}
