package itst.socialraccoon.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Data Transfer Object representing a user request")
public class UserRequestDTO {
    @Schema(description = "User id")
    private Integer userId;
    @Schema(description = "Name of the user", example = "Alejandro")
    @NotBlank(message = "name must not be null and must not be empty")
    @Size(min = 1,max = 60, message = "The name must be 1 to 60 characters long")
    private String name;
    @Schema(description = "Last name of the user", example = "Tejeda")
    @NotBlank(message = "lastName must not be null and must not be empty")
    @Size(min = 1,max = 60, message = "The last name must be 1 to 60 characters long")
    private String lastName;
    @Schema(description = "Second last name of the user", example = "Moreno")
    @NotBlank(message = "secondLastName must not be null and must not be empty")
    @Size(min = 1,max = 60, message = "The second last name must be 1 to 60 characters long")
    private String secondLastName;
    @Schema(description = "Email of the user", example = "L21TE0284@teziutlan.tecnm.mx")
    @NotBlank(message = "email must not be null and must not be empty")
    @Pattern(regexp = "[a-zA-Z0-9.]+@teziutlan\\.tecnm\\.mx", message = "The email must be a valid email from the TecNM campus Teziutlan")
    @Size(min = 1, max = 60, message = "The email must be 1 to 60 characters long")
    private String email;
    @Schema(description = "Control number of the user", example = "21TE0284")
    @NotBlank(message = "controlNumber must not be null and must not be empty")
    @Pattern(regexp = "[0-2][0-9]TE[0-9]{4}", message = "The control number must be a string of 8 digits like 21TE0284")
    @Size(min = 8, max = 8, message = "The control number must be 8 characters long")
    private String controlNumber;
    @Schema(description = "Password of the user", example = "s3cureP@ssw0rd")
    @NotBlank(message = "password must not be null and must not be empty")
    @Size(min = 8, max = 60, message = "The password must be at least 8 characters long and at most 60 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$",
            message = "The password must contain at least one lowercase letter, one uppercase letter, one digit, and must be at least 8 characters long")
    private String password;
    @Schema(description = "Career of the user", example = "Ingenieria en Sistemas Computacionales")
    @NotNull(message = "The career must not be null")
    private Integer career;

    public UserRequestDTO() {

    }

    public UserRequestDTO(Integer userId, String name, String lastName, String secondLastName, String email, String controlNumber, String password, Integer career) {
        this.userId = userId;
        this.name = name;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.email = email;
        this.controlNumber = controlNumber;
        this.password = password;
        this.career = career;
    }

    // Getters and Setters


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }

    public Integer getCareer() {
        return career;
    }

    public void setCareer(Integer career) {
        this.career = career;
    }
}
