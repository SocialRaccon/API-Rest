package itst.socialraccoon.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Data Transfer Object representing a follower relationship")
public class RelationshipDTO {
    @Schema(description = "Followers of the user")
    private List<RelationshipInfoDTO> followers;
    @Schema(description = "Users followed by the user")
    private List<RelationshipInfoDTO> following;

    public RelationshipDTO() {
    }

    public RelationshipDTO(List<RelationshipInfoDTO> followers, List<RelationshipInfoDTO> following) {
        this.followers = followers;
        this.following = following;
    }

    // Getters and Setters
    public List<RelationshipInfoDTO> getFollowers() {
        return followers;
    }

    public void setFollowers(List<RelationshipInfoDTO> followers) {
        this.followers = followers;
    }

    public List<RelationshipInfoDTO> getFollowing() {
        return following;
    }

    public void setFollowing(List<RelationshipInfoDTO> following) {
        this.following = following;
    }

}
