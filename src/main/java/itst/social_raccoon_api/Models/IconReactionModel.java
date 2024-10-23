package itst.social_raccoon_api.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity(name = "icon_reaction")
public class IconReactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idIconReaction;
    private String thumbnail;
    
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idReactionType")
    @JsonBackReference(value = "reaction_type-icon_reaction")
    private ReactionTypeModel reactionType;


    public Integer getIdIconReaction() {
        return idIconReaction;
    }

    public void setIdIconReaction(Integer idIconReaction) {
        this.idIconReaction = idIconReaction;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public ReactionTypeModel getReactionType() {
        return reactionType;
    }

    public void setReactionType(ReactionTypeModel idTypeReaction) {
        this.reactionType = idTypeReaction;
    }
}
