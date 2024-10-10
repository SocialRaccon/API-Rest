package itst.social_raccoon_api.Models.CompositeKeys;

import itst.social_raccoon_api.Models.UserModel;

import java.io.Serializable;
import java.util.Objects;

public class FollowerPK implements Serializable {
    private UserModel user;
    private UserModel followerUser;

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof FollowerPK followerPK))
            return false;
        return user.getIdUser() == followerPK.user.getIdUser() && followerUser.getIdUser() == followerPK.followerUser.getIdUser();
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
