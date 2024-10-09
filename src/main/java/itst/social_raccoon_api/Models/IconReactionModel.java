package itst.social_raccoon_api.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "iconreaction")
public class IconReactionModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idIconReaction;
    private String thumbnail;
    private int idTypeReaction;

    public int getIdIconReaction() {
        return idIconReaction;
    }

    public void setIdIconReaction(int idIconReaction) {
        this.idIconReaction = idIconReaction;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getIdTypeReaction() {
        return idTypeReaction;
    }

    public void setIdTypeReaction(int idTypeReaction) {
        this.idTypeReaction = idTypeReaction;
    }

    @Override
    public String toString() {
        return "IconReaction{" +
               "idIconReaction=" + idIconReaction +
               ", thumbnail='" + thumbnail + '\'' +
               ", idTypeReaction=" + idTypeReaction +
               '}';
    }
}
