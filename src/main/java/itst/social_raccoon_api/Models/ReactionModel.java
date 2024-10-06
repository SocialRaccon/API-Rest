package itst.social_raccoon_api.Models;

import java.sql.Date;

import jakarta.persistence.*;

@Entity
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
        StringBuilder sb = new StringBuilder();
        sb.append("ReactionModel{");
        sb.append("IdReaction=").append(IdReaction);
        sb.append(", ReactionDate=").append(ReactionDate);
        sb.append('}');
        return sb.toString();
    }

    

}