package itst.social_raccoon_api.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object representing basic information of a follower")
public class RelationshipInfoDTO {
    private Integer idUser;
    private String userName;

    public RelationshipInfoDTO() {
    }

    public RelationshipInfoDTO(Integer idUser, String userName) {
        this.idUser = idUser;
        this.userName = userName;
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
}
