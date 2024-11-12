package itst.socialraccoon.api.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import itst.socialraccoon.api.annotations.GlobalApiResponses;
import itst.socialraccoon.api.models.CareerModel;
import itst.socialraccoon.api.services.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("careers")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "Careers", description = "Provides methods to manage careers")
@GlobalApiResponses
public class CareerController {

    @Autowired
    private CareerService careerService;

    @GetMapping()
    @Operation(summary = "Get all careers", description = "Get all careers from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Careers found"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "No careers found")
    })
    public ResponseEntity<List<CareerModel>> findAll() {
        List<CareerModel> careers = careerService.findAll();
        return new ResponseEntity<>(careers, HttpStatus.OK);
    }

    @GetMapping("acronym/{acronym}")
    @Operation(summary = "Get career by acronym", description = "Get a career by its acronym")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Career found"),
            @ApiResponse(responseCode = "400", description = "Invalid request", content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = String.class))),
            @ApiResponse(responseCode = "404", description = "Career not found")
    })
    public ResponseEntity<CareerModel> findByAcronym(@PathVariable String acronym) {
        CareerModel career = careerService.findByAcronym(acronym);
        if (career == null) {
            throw new NoSuchElementException();
        }
        return new ResponseEntity<>(career, HttpStatus.OK);
    }
}
