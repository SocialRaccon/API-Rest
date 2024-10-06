package itst.social_raccoon_api.Models;

import java.sql.Date;

import jakarta.persistence.*;

@Entity(name = "reaction")
public class ReactionModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer IdReaction;
    private Date ReactionDate;

    public ReactionModel(Integer IdReaction, Date ReactionDate) {
        this.IdReaction = IdReaction;
        this.ReactionDate = ReactionDate;
    }

    public ReactionModel() {
    }

    public Integer getIdReaction() {
        return IdReaction;
    }
    public void setIdReaction(Integer idReaction) {
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