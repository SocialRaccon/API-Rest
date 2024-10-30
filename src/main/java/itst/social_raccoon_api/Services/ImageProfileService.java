package itst.social_raccoon_api.Services;

import itst.social_raccoon_api.Models.ImageProfileModel;
import itst.social_raccoon_api.Models.ProfileModel;
import itst.social_raccoon_api.Repositories.ImageProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Transactional
public class ImageProfileService {

    @Autowired
    private ImageProfileRepository imageProfileRepository;

    @Autowired
    private ImageStorageService imageStorageService;


    public List<ImageProfileModel> findAll() {
        return imageProfileRepository.findAll();
    }

    public ImageProfileModel save(ImageProfileModel imageProfile) {
        return imageProfileRepository.save(imageProfile);
    }

    public void delete(Integer id) {
        imageProfileRepository.deleteById(id);
    }

    public void update(ImageProfileModel imageProfile) {
        imageProfileRepository.save(imageProfile);
    }

    public void deleteImageProfile(Integer profileId, Integer imageProfileId) {
        imageProfileRepository.deleteImageProfile(profileId, imageProfileId);
    }

    public ImageProfileModel getImageProfileByProfileId(Integer profileId) {
        return imageProfileRepository.getImageProfileByProfileId(profileId);
    }

    public List<ImageProfileModel> getImageProfileByProfileId(Integer profileId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return imageProfileRepository.getImageProfileByProfileId(profileId, pageRequest);
    }

    public ImageProfileModel getImageProfileByUserId(Integer userId) {
        return imageProfileRepository.getImageProfileByUserId(userId);
    }

    public List<ImageProfileModel> getImageProfileByUserId(Integer userId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return imageProfileRepository.getImageProfileByUserId(userId, pageRequest);
    }

    public List<ImageProfileModel> getImageProfileByProfileIdAndUserId(Integer profileId, Integer userId) {
        return imageProfileRepository.getImageProfileByProfileIdAndUserId(profileId, userId);
    }

    public List<ImageProfileModel> getImageProfileByProfileIdAndUserId(Integer profileId, Integer userId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return imageProfileRepository.getImageProfileByProfileIdAndUserId(profileId, userId, pageRequest);
    }

    public ImageProfileModel getImageProfile(Integer profileId, Integer imageProfileId) {
        return imageProfileRepository.getImageProfile(profileId, imageProfileId);
    }

    public void addProfileImage(ProfileModel profile, MultipartFile profileImage) throws IOException {
        String imageUrl = imageStorageService.storeImage(profileImage);
        ImageProfileModel imageProfileOriginal = imageProfileRepository.getImageProfileByProfileId(profile.getIdProfile());
        if (imageUrl != null) {
            // Save the image profile to the database
            ImageProfileModel imageProfile = new ImageProfileModel();
            if (imageProfileOriginal != null) {
                imageProfile.setIdImageProfile(imageProfileOriginal.getIdImageProfile());
            }
            imageProfile.setProfile(profile);
            imageProfile.setImageUrl(imageUrl);
            imageProfile.setImageThumbnailUrl(imageUrl); // Assuming thumbnail is the same for simplicity
            imageProfileRepository.save(imageProfile);
        }
    }

    public void updateProfileImage(ProfileModel profile, MultipartFile profileImage) throws IOException {
        ImageProfileModel imageProfile = imageProfileRepository.getImageProfileByProfileId(profile.getIdProfile());
        if (imageProfile == null) {
            throw new IllegalArgumentException("Profile image not found");
        }
        String imageUrl = imageStorageService.storeImage(profileImage);
        if (imageUrl != null) {
            imageProfile.setImageUrl(imageUrl);
            imageProfile.setImageThumbnailUrl(imageUrl); // Assuming thumbnail is the same for simplicity
            imageProfileRepository.save(imageProfile);
        }
    }
}
