package itst.social_raccoon_api.Models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "follower")
public class FollowerModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFollower;
    private Integer idUser;
    private Integer idFollowerUser;

    public FollowerModel() {
    }

    public FollowerModel(Integer idUser, Integer idFollowerUser) {
        this.idUser = idUser;
        this.idFollowerUser = idFollowerUser;
    }

    public Integer getIdFollower() {
        return idFollower;
    }

    public void setIdFollower(Integer idFollower) {
        this.idFollower = idFollower;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public Integer getIdFollowerUser() {
        return idFollowerUser;
    }

    public void setIdFollowerUser(Integer idFollowerUser) {
        this.idFollowerUser = idFollowerUser;
    }
}
