package itst.social_raccoon_api.Services;

import itst.social_raccoon_api.Models.CareerModel;
import itst.social_raccoon_api.Models.ImageProfileModel;
import itst.social_raccoon_api.Models.ProfileModel;
import itst.social_raccoon_api.Models.UserModel;
import itst.social_raccoon_api.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CareerService careerService;

    @Autowired
    private ImageProfileService imageProfileService;

    @Autowired
    private ProfileService profileService;

    private final String defaultProfileImageUrl = "/uploads/default-profile.png";

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public UserModel findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserModel save(UserModel user, MultipartFile profileImage) throws IOException {
        CareerModel career = careerService.findById(user.getCareer().getIdCareer());
        if (career == null) {
            throw new NoSuchElementException("Career not found");
        }
        user.setCareer(career);
        UserModel newUser = userRepository.save(user);

        ProfileModel profile = new ProfileModel();
        profile.setIdUser(newUser);
        profileService.save(profile);

        if (profileImage != null && !profileImage.isEmpty()) {
            imageProfileService.addProfileImage(profile, profileImage);
        } else {
            ImageProfileModel defaultImageProfile = new ImageProfileModel();
            defaultImageProfile.setProfile(profile);
            defaultImageProfile.setImageUrl(defaultProfileImageUrl);
            defaultImageProfile.setImageThumbnailUrl(defaultProfileImageUrl);
            imageProfileService.save(defaultImageProfile);
        }
        return newUser;
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

    public UserModel updateProfileImage(Integer userId, MultipartFile profileImage) throws IOException {
        UserModel user = findById(userId);
        if (user == null) {
            throw new NoSuchElementException("User not found");
        }
        ImageProfileModel imageProfile = imageProfileService.getImageProfileByUserId(userId);
        if (imageProfile.getImageUrl().equals(defaultProfileImageUrl)) {
            imageProfile.setImageUrl(null);
            imageProfile.setImageThumbnailUrl(null);
            imageProfileService.update(imageProfile);
        }
        imageProfileService.addProfileImage(imageProfile.getProfile(), profileImage);
        return user;
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}