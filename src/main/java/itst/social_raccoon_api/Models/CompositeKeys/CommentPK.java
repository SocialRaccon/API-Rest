package itst.social_raccoon_api.Models.CompositeKeys;

import itst.social_raccoon_api.Models.PostModel;
import itst.social_raccoon_api.Models.UserModel;

import java.io.Serializable;
import java.util.Objects;

public class CommentPK implements Serializable {
    private Integer idComment;
    private PostModel post;
    private UserModel user;
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof CommentPK commentPK))
            return false;
        return post.getPost() == commentPK.post.getPost() && user.getIdUser() == commentPK.user.getIdUser() && idComment == commentPK.idComment;
    }
    @Override
    public int hashCode() {
        return Objects.hash(post.getPost(), user.getIdUser());
    }
    public PostModel getPost() {
        return post;
    }
    public UserModel getUser() {
        return user;
    }

    public Integer getIdComment() {
        return idComment;
    }
}
