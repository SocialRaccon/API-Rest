package itst.social_raccoon_api.Services;

import itst.social_raccoon_api.Models.CareerModel;
import itst.social_raccoon_api.Models.ImageProfileModel;
import itst.social_raccoon_api.Models.ProfileModel;
import itst.social_raccoon_api.Models.UserModel;
import itst.social_raccoon_api.Repositories.ReactionRepository;
import itst.social_raccoon_api.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    @Lazy
    private ReactionService reactionService;

    @Autowired
    private ProfileService profileService;

    private final String defaultProfileImageUrl = "/uploads/default-profile.png";

    public List<UserModel> findAll() {
        return userRepository.findAll();
    }

    public UserModel findById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserModel save(UserModel user) {
        CareerModel career = careerService.findById(user.getCareer().getIdCareer());
        if (career == null) {
            throw new NoSuchElementException("Career not found" );
        }
        user.setCareer(career);
        UserModel newUser = userRepository.save(user);

        ProfileModel profile = new ProfileModel();
        profile.setIdUser(newUser);
        profile.setDescription("" );
        profileService.save(profile);

        // Se guarda la imagen por defecto al crear el usuario
        ImageProfileModel defaultImageProfile = new ImageProfileModel();
        defaultImageProfile.setProfile(profile);
        defaultImageProfile.setImageUrl(defaultProfileImageUrl);
        defaultImageProfile.setImageThumbnailUrl(defaultProfileImageUrl);
        imageProfileService.save(defaultImageProfile);

        return newUser;
    }

    public void uploadProfileImage(Integer userId, MultipartFile profileImage) throws IOException {
        UserModel user = findById(userId);
        if (user == null) {
            throw new NoSuchElementException("User not found" );
        }

        ProfileModel profile = profileService.findById(user.getIdUser()); // Obtener el perfil del usuario

        if (profile == null) {
            throw new NoSuchElementException("Profile not found for user with ID: " + userId);
        }

        imageProfileService.addProfileImage(profile, profileImage); // Guardar la imagen usando el servicio
    }

    public Boolean deleteProfileImage(Integer userId) {
        UserModel user = findById(userId);
        if (user == null) {
            throw new NoSuchElementException("User not found" );
        }
        ImageProfileModel imageProfile = imageProfileService.getImageProfileByUserId(userId);
        if (imageProfile.getImageUrl().equals(defaultProfileImageUrl)) {
            throw new IllegalArgumentException("The user has the default profile image" );
        }
        imageProfile.setImageUrl(defaultProfileImageUrl);
        imageProfile.setImageThumbnailUrl(defaultProfileImageUrl);
        imageProfileService.update(imageProfile);
        return true;
    }

    public Boolean updateProfileImage(Integer profileId, MultipartFile profileImage) throws IOException {
        ProfileModel profile = profileService.findById(profileId);
        UserModel user = findById(profile.getIdUser().getIdUser());
        if (user == null) {
            throw new NoSuchElementException("User not found" );
        }
        ImageProfileModel imageProfile = imageProfileService.getImageProfileByProfileId(profileId);
        if (imageProfile == null) {
            throw new NoSuchElementException("Profile image not found" );
        }
        imageProfileService.updateProfileImage(profile, profileImage);
        return true;
    }
    @Transactional
    public void deleteUser(Integer userId) {
        UserModel user = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("User not found"));

        // Eliminar las reacciones relacionadas con el usuario
        reactionService.deleteByUserId(user);

        // Eliminar el usuario
        userRepository.delete(user);
    }

    public void deleteById(Integer id) {
        userRepository.deleteById(id);
    }
}