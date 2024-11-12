package itst.socialraccoon.api.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Schema(description = "Data Transfer Object representing a user")
public class UserDTO {
    @Schema(description = "Unique identifier of the user", example = "1")
    private Integer idUser;
    @Schema(description = "Name of the user", example = "Alejandro")
    @NotNull(message = "The name must not be null")
    @Size(min = 1, max = 60, message = "The name must be at 1 to 60 characters long")
    private String name;
    @Schema(description = "Last name of the user", example = "Tejeda")
    @NotNull(message = "The last name must not be null")
    @Size(min = 1, max = 60, message = "The last name must be at 1 to 60 characters long")
    private String lastName;
    @Schema(description = "Second last name of the user", example = "Moreno")
    @NotNull(message = "The second last name must not be null")
    @Size(min = 1, max = 60, message = "The second last name must be at 1 to 60 characters long")
    private String secondLastName;
    @Schema(description = "Email of the user", example = "L21TE0284@teziutlan.tecnm.mx")
    @NotNull(message = "The email must not be null")
    @Pattern(regexp = "[a-zA-Z0-9.]+@teziutlan\\.tecnm\\.mx", message = "The email must be a valid email from the TecNM campus Teziutlan")
    @Size(min = 1, max = 60, message = "The email must be 1 to 60 characters long")
    private String email;
    @Schema(description = "Control number of the user", example = "21TE0284")
    @NotNull(message = "The control number must not be null")
    @Pattern(regexp = "([0-1][0-9])TE[0-9]{4}", message = "The control number must be a string of 8 digits like 21TE284")
    @Size(min = 8, max = 8, message = "The control number must be 8 characters long")
    private String controlNumber;
    @Schema(description = "Career of the user", example = "Ingenieria en Sistemas Computacionales")
    @NotNull(message = "The career must not be null")
    @Size(min = 1, max = 80, message = "The career must be at 1 to 80 characters long")
    private String careerName;

    public UserDTO() {
    }

    public UserDTO(Integer idUser, String name, String lastName, String secondLastName, String email, String controlNumber, String career) {
        this.idUser = idUser;
        this.name = name;
        this.lastName = lastName;
        this.secondLastName = secondLastName;
        this.email = email;
        this.controlNumber = controlNumber;
        this.careerName = career;
    }

    // Getters and Setters

    public Integer getIdUser() {
        return idUser;
    }

    public void setIdUser(Integer idUser) {
        this.idUser = idUser;
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

    public String getCareerName() {
        return careerName;
    }

    public void setCareerName(String careerName) {
        this.careerName = careerName;
    }
}