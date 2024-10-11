package itst.social_raccoon_api.Controllers;

import itst.social_raccoon_api.Models.CareerModel;
import itst.social_raccoon_api.Repository.CareerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.net.URI;

@RestController
@RequestMapping("/career")
public class CarrerController {
    @Autowired
    private CareerRepository careerRepository;

    // Find all
    @GetMapping
    public ResponseEntity<Iterable<CareerModel>> findAll() {
        return ResponseEntity.ok(careerRepository.findAll());
    }

    // Find By Id
    @GetMapping("/{id}")
    public ResponseEntity<CareerModel> findById(@PathVariable Long idCareer) {
        Optional<CareerModel> careerOptional = careerRepository.findById(idCareer);
        if (careerOptional.isPresent()) {
            return ResponseEntity.ok(careerOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Create User
    @PostMapping
    public ResponseEntity<String> create(@RequestBody CareerModel career, UriComponentsBuilder ucb) {
        CareerModel saveCareer = careerRepository.save(career);
        URI uri = ucb
                .path("/career/{id}")
                .buildAndExpand(saveCareer.getIdCareer())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    // Update User
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Long idCareer, @RequestBody CareerModel careerAct) {
        CareerModel careerAnt = careerRepository.findById(idCareer).get();
        if (careerAnt != null) {
            careerAct.setIdCareer(careerAnt.getIdCareer());
            careerRepository.save(careerAct);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // Delete
    @DeleteMapping("/{idCareer}")
    public ResponseEntity<String> delete(@PathVariable Long idCareer) {
        if (careerRepository.findById(idCareer).get() != null) {
            careerRepository.deleteById(idCareer);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}