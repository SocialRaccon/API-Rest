package itst.social_raccoon_api.Services;

import itst.social_raccoon_api.Models.IconReactionModel;
import itst.social_raccoon_api.Repositories.IconReactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IconReactionService {

    @Autowired
    private IconReactionRepository iconReactionRepository;

    public List<IconReactionModel> getAllIconReactions() {
        return iconReactionRepository.findAll();
    }

    public Optional<IconReactionModel> getIconReactionById(Integer id) {
        return iconReactionRepository.findById(id);
    }

    public IconReactionModel createIconReaction(IconReactionModel iconReaction) {
        return iconReactionRepository.save(iconReaction);
    }

    public IconReactionModel updateIconReaction(Integer id, IconReactionModel iconReactionDetails) {
        IconReactionModel iconReaction = iconReactionRepository.findById(id).orElseThrow(() -> new RuntimeException("IconReaction not found"));
        iconReaction.setThumbnail(iconReactionDetails.getThumbnail());
        return iconReactionRepository.save(iconReaction);
    }

    public void deleteIconReaction(Integer id) {
        iconReactionRepository.deleteById(id);
    }
}