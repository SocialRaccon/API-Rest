package itst.social_raccoon_api.dto;

public class UserRequestDTO {
    private Integer userId;
    private String name;
    private String lastName;
    private String secondLastName;
    private String email;
    private String controlNumber;
    private String password;
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
