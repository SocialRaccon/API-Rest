package itst.socialraccoon.api.models.compositekeys;

import itst.socialraccoon.api.models.UserModel;

import java.io.Serializable;
import java.util.Objects;

public class RelationshipPK implements Serializable {
    private UserModel user;
    private UserModel followerUser;

    // Constructor vacío
    public RelationshipPK() {}

    // Constructor que recibe UserModel
    public RelationshipPK(UserModel user, UserModel followerUser) {
        this.user = user;
        this.followerUser = followerUser;
    }

    // Getters y Setters
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

    // equals() y hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof RelationshipPK relationshipPK))
            return false;
        return user.getIdUser() == relationshipPK.user.getIdUser()
                && followerUser.getIdUser() == relationshipPK.followerUser.getIdUser();
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getIdUser(), followerUser.getIdUser());
    }
}