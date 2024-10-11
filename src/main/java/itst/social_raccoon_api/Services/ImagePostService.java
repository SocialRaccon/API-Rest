package itst.social_raccoon_api.Services;

import itst.social_raccoon_api.Models.ImagePost;
import itst.social_raccoon_api.Models.User;
import itst.social_raccoon_api.Repositories.ImagePostRepository;
import itst.social_raccoon_api.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;



@Service
public class ImagePostService {

    @Autowired
    private ImagePostRepository imagePostRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    public List<ImagePost> getAllImagePosts() {
        return imagePostRepository.findAll();
    }

    public Optional<ImagePost> getImagePostById(Long id) {
        return imagePostRepository.findById(id);
    }

    public ImagePost createImagePost(Long userId, ImagePost imagePost) {
        Optional<User> user = userService.getUserById(userId);
        if (user.isPresent()) {
            imagePost.setUser(user.get());
            return imagePostRepository.save(imagePost);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public ImagePost updateImagePost(Long id, ImagePost imagePostDetails) {
        Optional<ImagePost> imagePost = imagePostRepository.findById(id);
        if (imagePost.isPresent()) {
            ImagePost existingImagePost = imagePost.get();
            existingImagePost.setTitle(imagePostDetails.getTitle());
            existingImagePost.setDescription(imagePostDetails.getDescription());
            existingImagePost.setImageUrl(imagePostDetails.getImageUrl());
            return imagePostRepository.save(existingImagePost);
        } else {
            throw new RuntimeException("ImagePost not found");
        }
    }

    public void deleteImagePost(Long id) {
        imagePostRepository.deleteById(id);
    }
}