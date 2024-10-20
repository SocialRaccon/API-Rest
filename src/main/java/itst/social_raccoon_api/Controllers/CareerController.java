package itst.social_raccoon_api.Controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import itst.social_raccoon_api.Models.CareerModel;
import itst.social_raccoon_api.Services.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("career")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Career", description = "Provides methods to manage careers.")
public class CareerController {

    @Autowired
    private CareerService careerService;

    @GetMapping()
    @Operation(summary = "Get all careers", description = "Get all careers from the database")
    public ResponseEntity<List<CareerModel>> findAll() {
        List<CareerModel> careers = careerService.findAll();
        return new ResponseEntity<>(careers, HttpStatus.OK);
    }

    @GetMapping("acronym/{acronym}")
    @Operation(summary = "Get career by acronym", description = "Get a career by its acronym")
    public ResponseEntity<CareerModel> findByAcronym(@PathVariable String acronym) {
        CareerModel career = careerService.findByAcronym(acronym);
        if (career == null) {
            throw new NoSuchElementException();
        }
        return new ResponseEntity<>(career, HttpStatus.OK);
    }

    @GetMapping("{id}")
    @Operation(summary = "Get career by id", description = "Get a career by its id")
    public ResponseEntity<CareerModel> findById(@PathVariable Integer id) {
        CareerModel career = careerService.findById(id);
        if (career == null) {
            throw new NoSuchElementException();
        }
        return new ResponseEntity<>(career, HttpStatus.OK);
    }

    @PostMapping()
    @Operation(
            summary = "Create a new career",
            description = "Create a new career in the database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CareerModel.class)
                    )
            )
    )
    public ResponseEntity<CareerModel> create(@RequestBody CareerModel career) {
        careerService.save(career);
        return new ResponseEntity<>(career, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    @Operation(
            summary = "Update a career",
            description = "Update a career in the database",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = CareerModel.class)
                    )
            )
    )
    public ResponseEntity<CareerModel> update(@PathVariable Integer id, @RequestBody CareerModel career) {
        CareerModel careerToUpdate = careerService.findById(id);
        if (careerToUpdate == null) {
            throw new NoSuchElementException();
        }
        career.setIdCareer(id);
        CareerModel updatedCareer = careerService.save(career);
        return new ResponseEntity<>(updatedCareer, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Delete a career",
            description = "Delete a career in the database"
    )
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        careerService.delete(id);
        return new ResponseEntity<>("Career deleted", HttpStatus.OK);
    }
}
