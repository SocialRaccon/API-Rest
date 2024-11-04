package itst.social.raccoon.api.services;

import itst.social.raccoon.api.models.PostDescriptionModel;
import itst.social.raccoon.api.models.PostModel;
import itst.social.raccoon.api.repositories.PostRepository;
import itst.social.raccoon.api.models.ImagePostModel;
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

    @Autowired
    private ImagePostService imagePostService;

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
        if (pageNumber < 0 || pageSize < 1) {
            throw new IllegalArgumentException("Invalid page number or size");
        }
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return postRepository.findByUserAndPost(userId, pageable).getContent();
    }

    public Page<PostModel> getFeed(Pageable pageable) {
        return postRepository.findAllByOrderByDateCreatedDesc(pageable);
    }

    @Transactional
    public void delete(Integer id) {
        if (!postRepository.existsById(id)) {
            throw new NoSuchElementException("Post not found");
        }
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
    public void deleteImage(Integer postId, Integer imageId) {
        PostModel post = postRepository.getReferenceById(postId);
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
    public void addImage(Integer postId, MultipartFile image) {
        if (!postRepository.existsById(postId)) {
            throw new NoSuchElementException("Post not found");
        }
        PostModel post = postRepository.getReferenceById(postId);
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
    public List<ImagePostModel> getImages(Integer postId, int pageNumber, int pageSize)
    {
        if (pageNumber < 0 || pageSize < 1) {
            throw new IllegalArgumentException("Invalid page number or size");
        }
        if (!postRepository.existsById(postId)) {
            throw new NoSuchElementException("Post not found");
        }
        return imagePostService.getImagePostByPostId(postId, pageNumber, pageSize);
    }

    @Transactional
    public PostModel update(Integer id, String description) {
        PostModel existingPost = postRepository.getReferenceById(id);
        PostDescriptionModel descriptionModel = existingPost.getIdPostDescription();
        descriptionModel.setDescription(description);
        existingPost.setIdPostDescription(descriptionModel);
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
    public String update(Integer postId, Integer imageId, MultipartFile image) {
        PostModel post = postRepository.getReferenceById(postId);
        if (post == null) {
            throw new NoSuchElementException("Post not found");
        }
        ImagePostModel imagePost = imagePostService.getImagePost(postId, imageId);
        if (imagePost == null) {
            throw new NoSuchElementException("Image not found");
        }
        try {
            String imageUrl = imageStorageService.storeImage(image);
            imagePost.setImageUrl(imageUrl);
            imagePost.setImageThumbnailUrl(imageUrl);
            postRepository.save(post);
            return imageUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
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