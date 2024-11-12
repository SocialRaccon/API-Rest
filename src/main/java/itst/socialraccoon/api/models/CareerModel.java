package itst.socialraccoon.api.models;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "career")
@Schema(description = "Model representing a career")
public class CareerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCareer", nullable = false)
    @Schema(description = "Unique identifier of the career", example = "1")
    private Integer idCareer;

    @NotNull(message = "The name of career must not be null and must not be empty")
    @Size(min = 1, max = 80, message = "The name must not exceed 60 characters")
    @Column(name = "name", nullable = false, length = 80)
    @Schema(description = "Name of the career", example = "Computer Science")
    private String name;

    @NotNull(message = "Acronym must not be null and must not be empty")
    @Size(min = 1, max = 7, message = "The acronym must not exceed 7 characters")
    @Column(name = "acronym", nullable = false, length = 7)
    @Schema(description = "Acronym of the career", example = "CS")
    private String acronym;


    public CareerModel() {
    }

    public Integer getIdCareer() {
        return idCareer;
    }

    public void setIdCareer(Integer id) {
        this.idCareer = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    @Override
    public String toString() {
        return "CareerModel{" +
                "idCareer=" + idCareer +
                ", name='" + name + '\'' +
                ", acronym='" + acronym + '\'' +
                '}';
    }

}