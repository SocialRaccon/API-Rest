package itst.social_raccoon_api.Services;

import itst.social_raccoon_api.Models.ImagePostModel;
import itst.social_raccoon_api.Models.PostDescriptionModel;
import itst.social_raccoon_api.Models.PostModel;
import itst.social_raccoon_api.Models.UserModel;
import itst.social_raccoon_api.Repositories.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public PostModel findById(Integer id, Integer userId) {
        return postRepository.findByUserAndPost(id, userId);
    }

    public void deleteById(Integer id) {
        postRepository.deleteById(id);
    }

    public List<PostModel> findByUser(Integer id) {
        return postRepository.findByUser(id);
    }

    public List<PostModel> findByUser(Integer userId, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return postRepository.findByUserAndPost(userId, pageable).getContent();
    }

    public Page<PostModel> getFeed(Pageable pageable) {
        return postRepository.findAllByOrderByDateCreatedDesc(pageable);
    }

    @Transactional
    public void delete(Integer id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public void delete(Integer postId, Integer userId) {
        PostModel post = postRepository.findByUserAndPost(userId, postId);
        if (post == null) {
            throw new NoSuchElementException("Post not found or does not belong to the user");
        }
        postRepository.delete(post);
    }

    @Transactional
    public void deleteImage(Integer userId, Integer postId, Integer imageId) {
        PostModel post = postRepository.findByUserAndPost(userId, postId);
        boolean imageExists = post.getImages().stream()
                .anyMatch(image -> image.getIdImagePost().equals(imageId));

        if (!imageExists) {
            throw new NoSuchElementException("Image not found");
        }

        post.getImages().removeIf(image -> image.getIdImagePost().equals(imageId));
        postRepository.save(post);

        System.out.println("Image deleted successfully");
    }

    @Transactional
    public void addImage(Integer userId, Integer postId, MultipartFile image) {
        PostModel post = postRepository.findByUserAndPost(userId, postId);
        try {
            String imageUrl = imageStorageService.storeImage(image);
            ImagePostModel imagePost = new ImagePostModel();
            imagePost.setIdPost(post);
            imagePost.setImageUrl(imageUrl);
            imagePost.setImageThumbnailUrl(imageUrl);
            post.getImages().add(imagePost);
            postRepository.save(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public List<ImagePostModel> getImages(Integer userId, Integer postId) {
        PostModel post = postRepository.findByUserAndPost(userId, postId);
        return post.getImages();
    }

    @Transactional
    public PostModel update(Integer id, PostModel updatedPost) {
        PostModel existingPost = postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Post no encontrado"));

        existingPost.setIdPostDescription(updatedPost.getIdPostDescription());
        existingPost.setDateCreated(updatedPost.getDateCreated());


        return postRepository.save(existingPost);
    }

    @Transactional
    public PostModel update(Integer id, String description, Integer userId) {
        PostModel existingPost = postRepository.findByUserAndPost(id, userId);
        PostDescriptionModel descriptionModel = existingPost.getIdPostDescription();
        descriptionModel.setDescription(description);
        existingPost.setIdPostDescription(descriptionModel);
        return postRepository.save(existingPost);
    }

    @Transactional
    public String update(Integer id, Integer imageId, String imageUrl) {
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