package itst.social_raccoon_api.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object representing a follower relationship")
public class FollowerDTO {
    private Integer idUser;
    private String userName;
    private Integer idFollower;
    private String followerName;

    public FollowerDTO(Integer idUser, String userName, Integer idFollower, String followerName) {
        this.idUser = idUser;
        this.userName = userName;
        this.idFollower = idFollower;
        this.followerName = followerName;
    }

    public FollowerDTO() {
    }

    // Getters and Setters
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

    public Integer getIdFollower() {
        return idFollower;
    }

    public void setIdFollower(Integer idFollower) {
        this.idFollower = idFollower;
    }

    public String getFollowerName() {
        return followerName;
    }

    public void setFollowerName(String followerName) {
        this.followerName = followerName;
    }

}
