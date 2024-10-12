package itst.social_raccoon_api.Services;

import itst.social_raccoon_api.Models.PostModel;
import itst.social_raccoon_api.Models.UserModel;
import itst.social_raccoon_api.Repositories.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public PostModel save(PostModel post) {
        return postRepository.save(post);
    }

    public List<PostModel> findAll() {
        return postRepository.findAll();
    }

    public PostModel findById(Integer id) {
        return postRepository.findById(id).orElse(null);
    }

    public void deleteById(Integer id) {
        postRepository.deleteById(id);
    }

    public List<PostModel> findByUser(Integer id) {
        return postRepository.findByUser(id);
    }

}
