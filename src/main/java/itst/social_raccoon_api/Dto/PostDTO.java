package itst.social_raccoon_api.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import itst.social_raccoon_api.Models.CareerModel;
import itst.social_raccoon_api.Models.CommentModel;
import itst.social_raccoon_api.Models.ImagePostModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Schema(description = "Data Transfer Object representing a post")
public class PostDTO {
    private Integer post;
    private Timestamp dateCreated;
    private Integer idUser;
    private String userName;
    private String userLastName;
    private String userSecondLastName;
    private String userControlNumber;
    private String postDescription = String.valueOf(new ArrayList<>());
    private List<CommentModel> comments = new ArrayList<>();
    private List<ReactionDTO> reactions = new ArrayList<>();
    private List<ImagePostModel> images = new ArrayList<>();

    public PostDTO() {
    }

    public PostDTO(
            Integer idPost,
            Timestamp dateCreated,
            Integer idUser,
            String userName,
            String userLastName,
            String userSecondLastName,
            String userEmail,
            String userControlNumber,
            String postDescription,
            List<CommentModel> comments,
            List<ReactionDTO> reactions,
            List<ImagePostModel> images) {
        this.post = idPost;
        this.dateCreated = dateCreated;
        this.idUser = idUser;
        this.userName = userName;
        this.userLastName = userLastName;
        this.userSecondLastName = userSecondLastName;
        this.userControlNumber = userControlNumber;
        this.postDescription = postDescription;
        this.comments = comments;
        this.reactions = reactions;
        this.images = images;
    }

    public Integer getIdPost() {
        return post;
    }

    public void setIdPost(Integer idPost) {
        this.post = idPost;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
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

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public String getUserSecondLastName() {
        return userSecondLastName;
    }

    public void setUserSecondLastName(String userSecondLastName) {
        this.userSecondLastName = userSecondLastName;
    }

    public String getUserControlNumber() {
        return userControlNumber;
    }

    public void setUserControlNumber(String userControlNumber) {
        this.userControlNumber = userControlNumber;
    }

    public String getPostDescription() {
        return postDescription;
    }

    public void setPostDescription(String postDescription) {
        this.postDescription = postDescription;
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }

    public List<ReactionDTO> getReactions() {
        return reactions;
    }

    public void setReactions(List<ReactionDTO> reactions) {
        this.reactions = reactions;
    }

    public List<ImagePostModel> getImages() {
        return images;
    }

    public void setImages(List<ImagePostModel> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "idPost=" + post +
                ", dateCreated=" + dateCreated +
                ", idUser=" + idUser +
                ", userName='" + userName + '\'' +
                ", userLastName='" + userLastName + '\'' +
                ", userSecondLastName='" + userSecondLastName + '\'' +
                ", userControlNumber='" + userControlNumber + '\'' +
                ", postDescription='" + postDescription + '\'' +
                ", comments=" + comments +
                ", reactions=" + reactions +
                ", images=" + images +
                '}';
    }
}