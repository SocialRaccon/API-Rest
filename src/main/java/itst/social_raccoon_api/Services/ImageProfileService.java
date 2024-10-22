package itst.social_raccoon_api.Services;

import itst.social_raccoon_api.Models.ImageProfileModel;
import itst.social_raccoon_api.Repositories.ImageProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Transactional
public class ImageProfileService {

    @Autowired
    private ImageProfileRepository imageProfileRepository;

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

    public List<ImageProfileModel> getImageProfileByProfileId(Integer profileId) {
        return imageProfileRepository.getImageProfileByProfileId(profileId);
    }

    public List<ImageProfileModel> getImageProfileByProfileId(Integer profileId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return imageProfileRepository.getImageProfileByProfileId(profileId, pageRequest);
    }

    public List<ImageProfileModel> getImageProfileByUserId(Integer userId) {
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
}
