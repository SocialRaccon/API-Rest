package itst.social_raccoon_api.Controllers;

import itst.social_raccoon_api.Models.IconReactionModel;
import itst.social_raccoon_api.Services.IconReactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("iconReactions")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Tag(name = "IconReaction", description = "Provide methods to manage icon reactions")
public class IconReactionController {

    @Autowired
    private IconReactionService iconReactionService;

    @GetMapping()
    @Operation (summary = "Get all icon reactions", description = "Get all icon reactions from the database")
    public ResponseEntity<List<IconReactionModel>> findAll() {
        List<IconReactionModel> iconReactions = iconReactionService.findAll();
        return new ResponseEntity<>(iconReactions, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<IconReactionModel> getIconReactionById(@PathVariable Integer id) {
        Optional<IconReactionModel> iconReaction = iconReactionService.getIconReactionById(id);
        return iconReaction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public IconReactionModel createIconReaction(@RequestBody IconReactionModel iconReaction) {
        return iconReactionService.createIconReaction(iconReaction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IconReactionModel> updateIconReaction(@PathVariable Integer id, @RequestBody IconReactionModel iconReactionDetails) {
        IconReactionModel updatedIconReaction = iconReactionService.updateIconReaction(id, iconReactionDetails);
        return ResponseEntity.ok(updatedIconReaction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIconReaction(@PathVariable Integer id) {
        iconReactionService.deleteIconReaction(id);
        return ResponseEntity.noContent().build();
    }
}