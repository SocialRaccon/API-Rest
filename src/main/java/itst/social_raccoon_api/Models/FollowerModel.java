package itst.social_raccoon_api.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import itst.social_raccoon_api.Dto.UserDTO;
import itst.social_raccoon_api.Models.CompositeKeys.FollowerPK;
import itst.social_raccoon_api.Services.MappingService;
import jakarta.persistence.*;

@Entity
@Table(name = "follower")
@IdClass(FollowerPK.class)
public class FollowerModel {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser", nullable = false)
    //@JsonBackReference(value = "user-follower")
    private UserModel user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idFollower", nullable = false)
    //@JsonBackReference(value = "user-following")
    private UserModel followerUser;

    public FollowerModel(UserModel user, UserModel followerUser) {
        this.user = user;
        this.followerUser = followerUser;
    }

    public FollowerModel() {
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public UserModel getFollowerUser() {
        return followerUser;
    }

    public void setFollowerUser(UserModel followerUser) {
        this.followerUser = followerUser;
    }
}