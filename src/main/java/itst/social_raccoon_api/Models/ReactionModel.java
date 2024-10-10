package itst.social_raccoon_api.Models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class ReactionModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdReaction;
    private Date ReactionDate;

    @OneToOne
    @JoinColumn(name = "idReactionType")
    private ReactionTypeModel reactionTypeModel;
    
    public ReactionModel(int IdReaction, Date ReactionDate) {
        this.IdReaction = IdReaction;
        this.ReactionDate = ReactionDate;
    }

    public ReactionModel() {
    }

    public int getIdReaction() {
        return IdReaction;
    }
    public void setIdReaction(int idReaction) {
        IdReaction = idReaction;
    }
    public Date getReactionDate() {
        return ReactionDate;
    }
    public void setReactionDate(Date reactionDate) {
        ReactionDate = reactionDate;
    }

    @Override
    public String toString() {
        return "ReactionModel [IdReaction=" + IdReaction + ", ReactionDate=" + ReactionDate + "]";
    }

}