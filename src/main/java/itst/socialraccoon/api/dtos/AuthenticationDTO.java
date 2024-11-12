package itst.socialraccoon.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class AuthenticationDTO {

    @NotNull(message = "email must not be null and must not be empty")
    @Email(message = "Email should be valid", regexp = "[a-zA-Z0-9.]+@teziutlan\\.tecnm\\.mx")
    @Schema(description = "Email of the user", example = "josuejoss10@gmail.com")
    private String email;

    @NotNull(message = "password must not be null and must not be empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "The password must contain at least one lowercase letter, one uppercase letter, one digit, and must be at least 8 characters long")
    @Schema(description = "Current password of the user", example = "1234!#$")
    private String password;

    @NotNull(message = "newPassword must not be null and must not be empty")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$", message = "The password must contain at least one lowercase letter, one uppercase letter, one digit, and must be at least 8 characters long")
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