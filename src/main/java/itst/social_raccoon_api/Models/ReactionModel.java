package itst.social_raccoon_api.Models;

import java.sql.Timestamp;
import jakarta.persistence.*;

@Entity(name = "reaction")
public class ReactionModel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idReaction;

    private Timestamp dateCreated;  // Campo para la fecha de creación

    // Constructor con todos los campos
    public ReactionModel(Integer idReaction, Timestamp dateCreated) {
        this.idReaction = idReaction;
        this.dateCreated = dateCreated;
    }

    // Constructor vacío
    public ReactionModel() {
    }

    // Getters y Setters
    public Integer getIdReaction() {
        return idReaction;
    }

    public void setIdReaction(Integer idReaction) {
        this.idReaction = idReaction;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    // Método toString para depuración
    @Override
    public String toString() {
        return "ReactionModel [idReaction=" + idReaction + ", dateCreated=" + dateCreated + "]";
    }
}