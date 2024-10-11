package itst.social_raccoon_api.Models;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity(name = "reaction")
public class ReactionModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdReaction;
    private Date ReactionDate;

    public ReactionModel(int IdReaction, Date ReactionDate) {
        this.IdReaction = IdReaction;
        this.ReactionDate = ReactionDate;
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
        StringBuilder sb = new StringBuilder();
        sb.append("ReactionModel{");
        sb.append("IdReaction=").append(IdReaction);
        sb.append(", ReactionDate=").append(ReactionDate);
        sb.append('}');
        return sb.toString();
    }

}