package itst.social_raccoon_api.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Timestamp;

@Schema(description = "Data Transfer Object representing a reaction")
public class ReactionDTO {

    private Integer idUser;
    private String userName;
    private Integer idPost;
    private Timestamp date;
    private Integer idReactionType;
    private String reactionType;

    public ReactionDTO() {
    }

    public ReactionDTO(Integer idUser, String userName, Integer idPost, Timestamp date, Integer idReactionType, String reactionType) {
        this.idUser = idUser;
        this.userName = userName;
        this.idPost = idPost;
        this.date = date;
        this.idReactionType = idReactionType;
        this.reactionType = reactionType;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getIdPost() {
        return idPost;
    }

    public void setIdPost(Integer idPost) {
        this.idPost = idPost;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getIdReactionType() {
        return idReactionType;
    }

    public void setIdReactionType(Integer idReactionType) {
        this.idReactionType = idReactionType;
    }

    public String getReactionType() {
        return reactionType;
    }

    public void setReactionType(String reactionType) {
        this.reactionType = reactionType;
    }

    @Override
    public String toString() {
        return "ReactionDTO{" +
                "idUser=" + idUser +
                ", userName='" + userName + '\'' +
                ", idPost=" + idPost +
                ", date=" + date +
                ", idReactionType=" + idReactionType +
                ", reactionType='" + reactionType + '\'' +
                '}';
    }
}
