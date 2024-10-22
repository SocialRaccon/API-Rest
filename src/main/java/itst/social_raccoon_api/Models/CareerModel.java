package itst.social_raccoon_api.Models;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
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

    @Size(max = 65)
    @NotNull
    @Column(name = "name", nullable = false, length = 65)
    @Schema(description = "Name of the career", example = "Computer Science")
    private String name;

    @Size(max = 10)
    @NotNull
    @Column(name = "acronym", nullable = false, length = 10)
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