package itst.socialraccoon.api.services;

import itst.socialraccoon.api.models.ReactionTypeModel;
import itst.socialraccoon.api.repositories.ReactionTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ReactionTypeService {

    @Autowired
    private ReactionTypeRepository reactionTypeRepository;

    public List<ReactionTypeModel> getAll() {
        return reactionTypeRepository.findAll();
    }

    public ReactionTypeModel getById(Integer id) {
        return reactionTypeRepository.findById(id).get();
    }

    public void save(ReactionTypeModel reactionType) {
        reactionTypeRepository.save(reactionType);
    }

    public void delete(Integer id) {
        reactionTypeRepository.deleteById(id);
    }

    public void update(ReactionTypeModel reactionType) {
        reactionTypeRepository.save(reactionType);
    }

    public ReactionTypeModel getByName(String name) {
        return reactionTypeRepository.getByName(name);
    }

}
