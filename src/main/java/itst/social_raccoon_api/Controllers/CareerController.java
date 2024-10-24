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
@RequestMapping("careers")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Careers", description = "Provides methods to manage careers.")
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
}
