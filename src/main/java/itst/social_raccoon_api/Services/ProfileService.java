package itst.social_raccoon_api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import itst.social_raccoon_api.Models.ProfileModel;
import itst.social_raccoon_api.Repositories.ProfileRepository;


public class ProfileService {
    
    @Autowired
    private ProfileRepository profileRepository;

    public List<ProfileModel> findAll() {
        return profileRepository.findAll();
    }

    public ProfileModel save(ProfileModel authentication) {
        return profileRepository.save(authentication);
    }
    public ProfileModel findById(Integer id) {
        return profileRepository.findById(id).orElse(null);
    }

    public void update(ProfileModel career) {
        profileRepository.save(career);
    }

    public void delete(Integer id) {
        profileRepository.deleteById(id);
    }

}
