package itst.social_raccoon_api.Services;

import itst.social_raccoon_api.Models.PostModel;
import itst.social_raccoon_api.Models.UserModel;
import itst.social_raccoon_api.Repositories.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    public Page<PostModel> getFeed(Pageable pageable) {
        return postRepository.findAllByOrderByDateCreatedDesc(pageable);
    }

    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }

    public PostModel updatePost(Integer id, PostModel updatedPost) {
        PostModel existingPost = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post no encontrado"));

        existingPost.setIdPostDescription(updatedPost.getIdPostDescription());
        existingPost.setDateCreated(updatedPost.getDateCreated());


        return postRepository.save(existingPost);
    }
}