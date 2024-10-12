package itst.social_raccoon_api.Models;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import itst.social_raccoon_api.Models.CompositeKeys.ReactionPK;
import jakarta.persistence.*;

@Entity()
@Table(name = "reaction")
@IdClass(ReactionPK.class)
@Schema(description = "Model representing a reaction")
public class ReactionModel {
    
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idReactionType")
    @Schema(description = "Unique identifier of the reaction type", example = "1")
    @JsonBackReference(value = "reactionType-reaction")
    private ReactionTypeModel idReactionType;  // Campo para el identificador de la reacción

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idPost")
    @Schema(description = "Unique identifier of the post", example = "1")
    @JsonBackReference(value = "post-reaction")
    private PostModel idPost;  // Campo para el identificador del post

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser")
    @Schema(description = "Unique identifier of the user", example = "1")
    @JsonBackReference(value = "user-reaction")
    private UserModel idUser;  // Campo para el identificador del usuario

    @Column(name = "date")
    @Schema(description = "Date when the reaction was made", example = "2021-10-10 10:00:00")
    private Timestamp date = new Timestamp(System.currentTimeMillis());  // Campo para la fecha de la reacción

    public ReactionModel() {
    }

    public ReactionModel(ReactionTypeModel idReaction, PostModel idPost, UserModel idUser, Timestamp date) {
        this.idReactionType = idReaction;
        this.idPost = idPost;
        this.idUser = idUser;
        this.date = date;
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