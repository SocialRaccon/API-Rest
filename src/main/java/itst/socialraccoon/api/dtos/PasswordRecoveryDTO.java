package itst.socialraccoon.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class PasswordRecoveryDTO {

    @Schema(description = "Email of the user", example = "alex2227@hotmail.com")
    @NotBlank(message = "Email must not be null or empty")
    @Pattern(regexp = "[a-zA-Z0-9.]+@teziutlan\\.tecnm\\.mx", message = "The email must be a valid email from the TecNM campus Teziutlan")
    @Size(min = 1, max = 60, message = "The email must be 1 to 60 characters long")
    private String email;

    // Getters and setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
