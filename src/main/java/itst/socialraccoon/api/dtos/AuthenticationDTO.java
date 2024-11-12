package itst.socialraccoon.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

public class AuthenticationDTO {

    @Schema(description = "Email of the user", example = "L21TE0284@teziutlan.tecnm.mx")
    @NotBlank(message = "email must not be null and must not be empty")
    @Pattern(regexp = "[a-zA-Z0-9.]+@teziutlan\\.tecnm\\.mx", message = "The email must be a valid email from the TecNM campus Teziutlan")
    @Size(min = 1, max = 60, message = "The email must be 1 to 60 characters long")
    private String email;

    @Schema(description = "Password of the user", example = "s3cureP@ssw0rd")
    @NotBlank(message = "password must not be null and must not be empty")
    @Size(min = 8, max = 60, message = "The password must be at least 8 characters long and at most 60 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message = "The password must contain at least one lowercase letter, one uppercase letter, one digit, and must be at least 8 characters long")
    private String password;

    @Schema(description = "New password of the user", example = "n3wP@ssw0rd")
    @NotBlank(message = "newPassword must not be null and must not be empty")
    @Size(min = 8, max = 60, message = "The new password must be at least 8 characters long and at most 60 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$",
            message = "The password must contain at least one lowercase letter, one uppercase letter, one digit, and must be at least 8 characters long")
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