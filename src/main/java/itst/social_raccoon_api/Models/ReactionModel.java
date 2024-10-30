package itst.social_raccoon_api.Models;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import itst.social_raccoon_api.Models.CompositeKeys.ReactionPK;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity()
@Table(name = "reaction")
@IdClass(ReactionPK.class)
@Schema(description = "Model representing a reaction")
public class ReactionModel {

    @ManyToOne(fetch = FetchType.LAZY)  // Changed from @OneToOne to @ManyToOne
    @JoinColumn(name = "idReactionType", referencedColumnName = "idReactionType")
    @Schema(description = "Unique identifier of the reaction type", example = "1")
    @JsonBackReference(value = "reactionType-reaction")
    @JsonProperty("idReactionType")
    private ReactionTypeModel idReactionType;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPost")
    @Schema(description = "Unique identifier of the post", example = "1")
    @JsonBackReference(value = "post-reaction")
    @JsonProperty("idPost")
    private PostModel idPost;  // Post identifier field

    @Id
    @ManyToOne()
    @JoinColumn(name = "idUser", nullable = false)
    @Schema(description = "Unique identifier of the user", example = "1")
    @JsonBackReference(value = "user-reaction")
    @JsonProperty("idUser")
    private UserModel idUser;  // User ID field

    @Column(name = "createdDate")
    @Schema(description = "Date when the reaction was made", example = "2021-10-10 10:00:00")
    @JsonProperty("createdDate")
    private Timestamp date = new Timestamp(System.currentTimeMillis());  // Reaction date field

    public ReactionModel() {
    }

    public ReactionModel(ReactionTypeModel idReaction, PostModel idPost, UserModel idUser) {
        this.idReactionType = idReaction;
        this.idPost = idPost;
        this.idUser = idUser;
    }

    public ReactionTypeModel getIdReactionType() {
        return idReactionType;
    }

    public void setIdReactionType(ReactionTypeModel idReaction) {
        this.idReactionType = idReaction;
    }

    public PostModel getIdPost() {
        return idPost;
    }

    public void setIdPost(PostModel idPost) {
        this.idPost = idPost;
    }

    public UserModel getIdUser() {
        return idUser;
    }

    public void setIdUser(UserModel idUser) {
        this.idUser = idUser;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ReactionModel{" +
                "idReaction=" + idReactionType +
                ", idPost=" + idPost +
                ", idUser=" + idUser +
                ", date=" + date +
                '}';
    }
}