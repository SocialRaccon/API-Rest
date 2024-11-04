package itst.socialraccoon.api.services;

import itst.socialraccoon.api.models.ImageProfileModel;
import itst.socialraccoon.api.models.ProfileModel;
import itst.socialraccoon.api.repositories.ImageProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class ImageProfileService {

    @Autowired
    private ImageProfileRepository imageProfileRepository;

    @Autowired
    private ImageStorageService imageStorageService;

    @Autowired
    private ProfileService profileService;


    public List<ImageProfileModel> findAll() {
        return imageProfileRepository.findAll();
    }

    public ImageProfileModel save(ImageProfileModel imageProfile) {
        return imageProfileRepository.save(imageProfile);
    }

    @Transactional
    public void delete(Integer id) {
        if (imageProfileRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Image profile not found");
        }
        imageProfileRepository.deleteById(id);
    }

    public ImageProfileModel findById(Integer id) {
        return imageProfileRepository.getReferenceById(id);
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

    public void addProfileImage(Integer profileId, MultipartFile profileImage) {
        try {
            ProfileModel profile = profileService.findById(profileId);
            if (profile == null) {
                throw new IllegalArgumentException("Profile not found");
            }
            String imageUrl = imageStorageService.storeImage(profileImage);
            ImageProfileModel imageProfile = new ImageProfileModel();
            imageProfile.setProfile(profile);
            imageProfile.setImageUrl(imageUrl);
            imageProfile.setImageThumbnailUrl(imageUrl);
            imageProfileRepository.save(imageProfile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    public void updateProfileImage(Integer imageId, MultipartFile image) {
        ImageProfileModel imageProfile = imageProfileRepository.getReferenceById(imageId);
        if (imageProfile == null) {
            throw new IllegalArgumentException("Image profile not found");
        }
        try {
            String imageUrl = imageStorageService.storeImage(image);
            imageProfile.setImageUrl(imageUrl);
            imageProfile.setImageThumbnailUrl(imageUrl);
            imageProfileRepository.save(imageProfile);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    public void deleteProfileImage(Integer profileId) {
        ImageProfileModel imageProfile = imageProfileRepository.getImageProfileByProfileId(profileId);
        if (imageProfile != null) {
            imageProfileRepository.delete(imageProfile);
        }
    }
}
