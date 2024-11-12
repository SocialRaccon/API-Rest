package itst.socialraccoon.api.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.validation.constraints.*;

@Schema(description = "Data Transfer Object representing a reaction")
public class ReactionDTO {

    @NotNull(message = "User ID cannot be null")
    private Integer idUser;
    @Schema(description = "Username of the user", example = "Alejandro")
    @NotNull(message = "The username must not be null")
    @Size(max = 30, message = "The username must be at most 20 characters")
    private String userName;
    @NotNull(message = "The post ID must not be null")
    private Integer idPost;
    @Schema(description = "Date of the post or reaction", example = "2023-11-08T12:30:00")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime date = LocalDateTime.now().withNano(0);
    @NotNull(message = "The reaction type ID must not be null")
    private Integer idReactionType;
    @Schema(description = "Reaction name", example = "me enmapacha")
    @NotBlank(message = "The reaction name must not be empty")
    @Pattern(regexp = "^(me enmapacha|me gusta|me enoja|me divierte)$",
            message = "The reaction name must be one of the following: me enmapacha, me gusta, me enoja, me divierte")
    private String reactionName;
    @Schema(description = "URL of the reaction icon image", example = "https://example.com/reaction-icon.png")
    @NotBlank(message = "The reaction icon URL must not be empty")
    @Pattern(regexp = "^(http|https)://.*\\.(jpg|jpeg|png)$",
            message = "The reaction icon URL must be a valid image URL ending in JPG, JPEG or PNG")
    private String reactionIcon;

    public ReactionDTO() {
    }

    public ReactionDTO(Integer idUser, String userName, Integer idPost, LocalDateTime date, Integer idReactionType, String reactionName, String reactionIcon) {
        this.idUser = idUser;
        this.userName = userName;
        this.idPost = idPost;
        this.date = date;
        this.idReactionType = idReactionType;
        this.reactionName = reactionName;
        this.reactionIcon = reactionIcon;
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

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Integer getIdReactionType() {
        return idReactionType;
    }

    public void setIdReactionType(Integer idReactionType) {
        this.idReactionType = idReactionType;
    }

    public String getReactionName() {
        return reactionName;
    }

    public void setReactionName(String reactionName) {
        this.reactionName = reactionName;
    }

    public String getReactionIcon() {
        return reactionIcon;
    }

    public void setReactionIcon(String reactionIcon) {
        this.reactionIcon = reactionIcon;
    }

    @Override
    public String toString() {
        return "ReactionDTO{" +
                "idUser=" + idUser +
                ", userName='" + userName + '\'' +
                ", idPost=" + idPost +
                ", date=" + date +
                ", idReactionType=" + idReactionType +
                ", reactionName='" + reactionName + '\'' +
                ", reactionIcon='" + reactionIcon + '\'' +
                '}';
    }
}