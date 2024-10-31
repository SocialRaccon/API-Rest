package itst.social_raccoon_api.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import itst.social_raccoon_api.models.compositekeys.RelationshipPK;
import jakarta.persistence.*;

@Schema(description = "Model representing a relationship")
@Entity
@Table(name = "relationship")
@IdClass(RelationshipPK.class)
public class RelationshipModel {

    @Schema(description = "User who is being followed")
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUser", nullable = false)
    @JsonBackReference(value = "user-follower")
    private UserModel user;

    @Schema(description = "User who is following")
    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idFollower", nullable = false)
    @JsonBackReference(value = "user-following")
    private UserModel followerUser;

    public RelationshipModel(UserModel user, UserModel followerUser) {
        this.user = user;
        this.followerUser = followerUser;
    }

    public RelationshipModel() {
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