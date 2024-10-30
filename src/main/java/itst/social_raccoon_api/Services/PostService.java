package itst.social_raccoon_api.Services;

import itst.social_raccoon_api.Models.ImagePostModel;
import itst.social_raccoon_api.Models.PostDescriptionModel;
import itst.social_raccoon_api.Models.PostModel;
import itst.social_raccoon_api.Models.UserModel;
import itst.social_raccoon_api.Repositories.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {


    @Autowired
    private PostRepository postRepository;

    @Autowired
    private ImageStorageService imageStorageService;

    @Transactional
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

    @Transactional
    public void deletePost(Integer id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public PostModel updatePost(Integer id, PostModel updatedPost) {
        PostModel existingPost = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post no encontrado"));

        existingPost.setIdPostDescription(updatedPost.getIdPostDescription());
        existingPost.setDateCreated(updatedPost.getDateCreated());


        return postRepository.save(existingPost);
    }

    @Transactional
    public String updatePostDescription(Integer id, String description) {
        PostModel existingPost = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post no encontrado"));
        PostDescriptionModel descriptionModel = existingPost.getIdPostDescription();
        descriptionModel.setDescription(description);
        existingPost.setIdPostDescription(descriptionModel);
        postRepository.save(existingPost);
        return "Descripción actualizada";
    }

    @Transactional
    public String updatePostImageByImageId(Integer id, Integer imageId, String imageUrl) {
        PostModel existingPost = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post no encontrado"));
        existingPost.getImages().stream()
                .filter(image -> image.getIdImagePost().equals(imageId))
                .findFirst()
                .ifPresent(image -> image.setImageUrl(imageUrl));
        postRepository.save(existingPost);
        return "Imagen actualizada";
    }

    @Transactional
    public PostModel save(PostModel post, MultipartFile file) {
        try {
            String imageUrl = imageStorageService.storeImage(file);
            ImagePostModel imagePost = new ImagePostModel();
            imagePost.setIdPost(post);
            imagePost.setImageUrl(imageUrl);
            imagePost.setImageThumbnailUrl(imageUrl);
            post.setImages(List.of(imagePost));
            return postRepository.save(post);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}