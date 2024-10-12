package itst.social_raccoon_api.Models.CompositeKeys;

import itst.social_raccoon_api.Models.PostModel;
import itst.social_raccoon_api.Models.ReactionTypeModel;
import itst.social_raccoon_api.Models.UserModel;

import java.io.Serializable;

public class ReactionPK implements Serializable {
    private UserModel idUser;
    private PostModel idPost;
    private ReactionTypeModel idReactionType;

    public ReactionPK() {
    }

    public ReactionPK(UserModel idUser, PostModel idPost, ReactionTypeModel idReaction) {
        this.idUser = idUser;
        this.idPost = idPost;
        this.idReactionType = idReaction;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ReactionPK reactionPK))
            return false;
        return idUser.getIdUser() == reactionPK.idUser.getIdUser() && idPost.getIdPost() == reactionPK.idPost.getIdPost() && idReactionType.getIdReactionType() == reactionPK.idReactionType.getIdReactionType();
    }

    @Override
    public int hashCode() {
        return idUser.getIdUser() + idPost.getIdPost() + idReactionType.getIdReactionType();
    }

    public UserModel getIdUser() {
        return idUser;
    }

    public PostModel getIdPost() {
        return idPost;
    }

    public ReactionTypeModel getIdReactionType() {
        return idReactionType;
    }
}
