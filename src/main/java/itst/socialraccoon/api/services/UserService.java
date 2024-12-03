package itst.socialraccoon.api.services;

import itst.socialraccoon.api.models.ImageProfileModel;
import itst.socialraccoon.api.models.ProfileModel;
import itst.socialraccoon.api.models.UserModel;
import itst.socialraccoon.api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    private ImageProfileService imageProfileService;

    @Autowired
    @Lazy
    private ReactionService reactionService;

    @Autowired
    private ProfileService profileService;

    private final String defaultProfileImageUrl = "https://firebasestorage.googleapis.com/v0/b/socialraccoon-990a3.appspot.com/o/user.png?alt=media&token=c303a942-13e8-4758-a578-e5b6e70400a1";

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public UserModel findById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User not found"));
    }

    @Transactional
    public UserModel save(UserModel user) {
        ProfileModel profile = user.getProfile();
        if (profile == null) {
            throw new IllegalArgumentException("Profile not found");
        }

        // Create a default image profile
        ImageProfileModel defaultImageProfile = new ImageProfileModel();
        defaultImageProfile.setProfile(profile);
        defaultImageProfile.setImageUrl(defaultProfileImageUrl);
        defaultImageProfile.setImageThumbnailUrl(defaultProfileImageUrl);

        // Add the default image profile to the profile's images set
        Set<ImageProfileModel> images = new HashSet<>();
        images.add(defaultImageProfile);
        profile.setImages(images);

        // Set the profile to the user
        user.setProfile(profile);

        // Save the user, which will cascade save the profile and image profiles
        return userRepository.save(user);
    }

    @Transactional
    public UserModel save(UserModel user, MultipartFile image) {
        try{
            ProfileModel profile = user.getProfile();
            if (profile == null) {
                throw new IllegalArgumentException("Profile not found");
            }

            // Save the image
            String imageUrl = imageStorageService.storeImage(image);

            // Create an image profile
            ImageProfileModel imageProfile = new ImageProfileModel();
            imageProfile.setProfile(profile);
            imageProfile.setImageUrl(imageUrl);
            imageProfile.setImageThumbnailUrl(imageUrl);

            // Add the image profile to the profile's images set
            Set<ImageProfileModel> images = new HashSet<>();
            images.add(imageProfile);
            profile.setImages(images);

            // Set the profile to the user
            user.setProfile(profile);

            // Save the user, which will cascade save the profile and image profiles
            return userRepository.save(user);
        } catch (IOException e) {
            throw new IllegalArgumentException("Error saving image");
        }
    }

    public Boolean deleteProfileImage(Integer userId) {
        UserModel user = findById(userId);
        if (user == null) {
            throw new NoSuchElementException("User not found");
        }
        ImageProfileModel imageProfile = imageProfileService.getImageProfileByUserId(userId);
        if (imageProfile.getImageUrl().equals(defaultProfileImageUrl)) {
            throw new IllegalArgumentException("The user has the default profile image");
        }
        imageProfile.setImageUrl(defaultProfileImageUrl);
        imageProfile.setImageThumbnailUrl(defaultProfileImageUrl);
        imageProfileService.update(imageProfile);
        return true;
    }


    @Transactional
    public void deleteUser(Integer userId) {
        UserModel user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));
        reactionService.deleteByUserId(user);
        userRepository.delete(user);
    }

    public void deleteById(Integer id) {
        if (!userRepository.existsById(id)) {
            throw new NoSuchElementException("User not found");
        }
        userRepository.deleteById(id);
    }

    public UserModel findByEmail(String email) {
        return userRepository.findByAuthentication_Email(email);
    }
}