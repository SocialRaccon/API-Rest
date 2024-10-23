package itst.social_raccoon_api.Services;

import itst.social_raccoon_api.Models.ReactionIconModel;
import itst.social_raccoon_api.Repositories.ReactionIconRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ReactionIconService {

    @Autowired
    private ReactionIconRepository reactionIconRepository;

    public List<ReactionIconModel> findAll() {
        return reactionIconRepository.findAll();
    }

    public ReactionIconModel save(ReactionIconModel reactionIcon){
        return reactionIconRepository.save(reactionIcon);
    }

    public void delete(Integer id){
        reactionIconRepository.deleteById(id);
    }

    public void update(ReactionIconModel reactionIcon){
        reactionIconRepository.save(reactionIcon);
    }

    public void deleteReactionIcon(Integer reactionTypeId,Integer reactionIconId){
        reactionIconRepository.deleteReactionIcon(reactionTypeId ,reactionIconId);
    }

    public List<ReactionIconModel> getReactionIconByReactionTypeId(Integer reactionTypeId){
        return reactionIconRepository.getReactionIconByReactionTypeId(reactionTypeId);
    }

    public List<ReactionIconModel> getReactionIconByReactionTypeId(Integer reactionTypeId, int pageNumber, int pageSize){
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return reactionIconRepository.getReactionIconByReactionTypeId(reactionTypeId, pageRequest);
    }

    public ReactionIconModel getReactionIcon(Integer reactionTypeId, Integer reactionIconId){
        return reactionIconRepository.getReactionIcon(reactionTypeId,reactionIconId);
    }
}
