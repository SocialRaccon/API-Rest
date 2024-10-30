package itst.social_raccoon_api.Dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class AuthenticationDTO {
    @Schema(description = "Email of the user", example = "josuejoss10@gmail.com")
    private String email;

    @Schema(description = "Current password of the user", example = "1234!#$")
    private String password;

    @Schema(description = "New password of the user", example = "!#$1234")
    private String newPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}