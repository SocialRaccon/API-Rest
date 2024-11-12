package itst.socialraccoon.api.models;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

@Entity
@Table(name = "user")
@JsonIgnoreProperties({"posts", "followers", "following", "comments"})
@Schema(description = "Model representing a user")
public class UserModel {
    @Id
    @Column(name = "idUser", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the user", example = "1")
    @JsonProperty("idUser")
    private Integer idUser;

    @JsonManagedReference(value = "user-post")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Posts made by the user")
    private List<PostModel> posts;

    @JsonManagedReference(value = "user-follower")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Followers of the user")
    private List<RelationshipModel> followers;

    @JsonManagedReference(value = "user-following")
    @OneToMany(mappedBy = "followerUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Users followed by the user")
    private List<RelationshipModel> following;

    @JsonManagedReference(value = "user-comment")
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Comments made by the user")
    private List<CommentModel> comments;

    @JsonManagedReference(value = "user-reaction")
    @OneToMany(mappedBy = "idUser", orphanRemoval = true)
    @Schema(description = "Reactions made by the user")
    private List<ReactionModel> reactions;

    @NotNull(message = "The profile must not be null")
    @JsonManagedReference(value = "user-profile")
    @OneToOne(mappedBy = "idUser", cascade = CascadeType.ALL, orphanRemoval = true)
    @Schema(description = "Profile of the user", example = "1")
    @JsonProperty("profile")
    private ProfileModel profile;

    @NotNull(message = "The authentication must not be null")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "idAuthentication", referencedColumnName = "idAuthentication", nullable = false)
    @Schema(description = "Authentication data of the user")
    @JsonProperty("authentication")
    private AuthenticationModel authentication;

    @NotBlank(message = "name must not be null and must not be empty")
    @Size(min = 1, max = 60, message = "The name must be 1 to 60 characters long")
    @Column(nullable = false, name = "name", length = 60)
    @Schema(description = "Name of the user", example = "Juan")
    @JsonProperty("name")
    private String name;

    @NotBlank(message = "lastName must not be null and must not be empty")
    @Size(min = 1, max = 60, message = "The last name must be 1 to 60 characters long")
    @Column(nullable = false, name = "lastName", length = 60)
    @Schema(description = "Last name of the user", example = "Perez")
    @JsonProperty("lastName")
    private String lastName;

    @NotBlank(message = "secondLastName must not be null and must not be empty")
    @Size(min = 1, max = 60, message = "The second last name must be 1 to 60 characters long")
    @Column(nullable = false, name = "secondLastName", length = 60)
    @Schema(description = "Second last name of the user", example = "Gomez")
    @JsonProperty("secondLastName")
    private String secondLastName;

    @NotBlank(message = "controlNumber must not be null and must not be empty")
    @Pattern(regexp = "[0-2][0-9]TE[0-9]{4}", message = "The control number must be a string of 8 digits like 21TE0284")
    @Column(nullable = false, name = "controlNumber", length = 8, unique = true)
    @Schema(description = "Control number of the user", example = "21TE0284")
    @Size(min = 8, max = 8, message = "The control number must be 8 characters long")
    @JsonProperty("controlNumber")
    private String controlNumber;

    @NotNull(message = "The career must not be null")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idCareer", referencedColumnName = "idCareer", nullable = false)
    @Schema(description = "Career of the user")
    @JsonProperty("career")
    private CareerModel career;

    public UserModel() {

    }

    public UserModel(Integer idUser, List<PostModel> posts, List<RelationshipModel> followers, List<RelationshipModel> following, List<CommentModel> comments, ProfileModel profile, AuthenticationModel authentication, String name, String lastName, String secondLastName, String controlNumber, CareerModel career) {
        this.idUser = idUser;
        this.posts = posts;
        this.followers = followers;
        this.following = following;
        this.comments = comments;
        this.profile = profile;
        this.authentication = authentication;
        this.name = name;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.controlNumber = controlNumber;
        this.career = career;
    }

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
    }

    public List<PostModel> getPosts() {
        return posts;
    }

    public void setPosts(List<PostModel> posts) {
        this.posts = posts;
    }

    public List<RelationshipModel> getFollowers() {
        return followers;
    }

    public void setFollowers(List<RelationshipModel> followers) {
        this.followers = followers;
    }

    public List<RelationshipModel> getFollowing() {
        return following;
    }

    public void setFollowing(List<RelationshipModel> following) {
        this.following = following;
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }

    public ProfileModel getProfile() {
        return profile;
    }

    public void setProfile(ProfileModel profile) {
        this.profile = profile;
    }

    public AuthenticationModel getAuthentication() {
        return authentication;
    }

    public void setAuthentication(AuthenticationModel authentication) {
        this.authentication = authentication;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }

    public CareerModel getCareer() {
        return career;
    }

    public void setCareer(CareerModel career) {
        this.career = career;
    }
}