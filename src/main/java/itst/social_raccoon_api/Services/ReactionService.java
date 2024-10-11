package itst.social_raccoon_api.Services;

import itst.social_raccoon_api.Models.ReactionModel;
import itst.social_raccoon_api.Repositories.ReactionRepository;
import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Transactional
public class ReactionService {

    @Autowired
    private ReactionRepository reactionRepository;

    public List<ReactionModel> findAll() {
        return reactionRepository.findAll();
    }

    public ReactionModel save(ReactionModel reaction) {
        return reactionRepository.save(reaction);
    }

    public ReactionModel findById(Integer id) {
        return reactionRepository.findById(id).orElse(null);
    }

    public void deleteById(Integer id) {
        reactionRepository.deleteById(id);
    }

}
