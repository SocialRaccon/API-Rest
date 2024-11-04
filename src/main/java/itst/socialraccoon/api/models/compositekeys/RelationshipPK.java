package itst.socialraccoon.api.models.compositekeys;

import itst.socialraccoon.api.models.UserModel;

import java.io.Serializable;
import java.util.Objects;

public class RelationshipPK implements Serializable {
    private UserModel user;
    private UserModel followerUser;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof RelationshipPK relationshipPK))
            return false;
        return user.getIdUser() == relationshipPK.user.getIdUser() && followerUser.getIdUser() == relationshipPK.followerUser.getIdUser();
    }

    @Override
    public int hashCode() {
        return Objects.hash(user.getIdUser(), followerUser.getIdUser());
    }

    public UserModel getUser() {
        return user;
    }

    public UserModel getFollowerUser() {
        return followerUser;
    }

}
