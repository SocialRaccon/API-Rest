package itst.social_raccoon_api.Models;
import java.sql.Date;

import jakarta.persistence.*;

@Entity(name = "reaction")
public class ReactionModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReaction;
    private Date reactionDate;

    public ReactionModel(Integer IdReaction, Date ReactionDate) {
        this.idReaction = IdReaction;
        this.reactionDate = ReactionDate;
    }

    public ReactionModel() {
    }

    public Integer getIdReaction() {
        return idReaction;
    }
    public void setIdReaction(Integer idReaction) {
        this.idReaction = idReaction;
    }
    public Date getReactionDate() {
        return reactionDate;
    }
    public void setReactionDate(Date reactionDate) {
        this.reactionDate = reactionDate;
    }

    @Override
    public String toString() {
        return "ReactionModel [idReaction=" + idReaction + ", reactionDate=" + reactionDate + "]";
    }

}