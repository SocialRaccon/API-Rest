package itst.social_raccoon_api.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(description = "Data Transfer Object representing a user profile")
public class ProfileDTO {

    @Schema(description = "Unique identifier of the profile", example = "1")
    private Integer idProfile;

    @Schema(description = "Description of the profile", example = "This is a description")
    private String description;

    @Schema(description = "Name of the user", example = "Juan")
    private String userName;

    @Schema(description = "URL of the profile image", example = "/uploads/profile-image.jpg")
    private String profileImageUrl;

    @Schema(description = "List of posts made by the user")
    private List<PostDTO> posts;

    @Schema(description = "List of users followed by the user")
    private List<RelationshipInfoDTO> following;

    @Schema(description = "List of users following the user")
    private List<RelationshipInfoDTO> followers;

    // Constructor, Getters y Setters
    public ProfileDTO() {}

    public ProfileDTO(Integer idProfile, String description, String userName) {
        this.idProfile = idProfile;
        this.description = description;
        this.userName = userName;
    }

    public Integer getIdProfile() {
        return idProfile;
    }

    public void setIdProfile(Integer idProfile) {
        this.idProfile = idProfile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    //Getters and Setters for followers and following
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

    //Getters and Setters for profileImageUrl
    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    // Getters and Setters for posts
    public List<PostDTO> getPosts() {
        return posts;
    }

    public void setPosts(List<PostDTO> posts) {
        this.posts = posts;
    }
}