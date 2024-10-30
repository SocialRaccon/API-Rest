package itst.social_raccoon_api.Services;

import java.util.List;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itst.social_raccoon_api.Dto.PostDTO;
import itst.social_raccoon_api.Dto.ProfileDTO;
import itst.social_raccoon_api.Dto.RelationshipDTO;
import itst.social_raccoon_api.Models.ImageProfileModel;
import itst.social_raccoon_api.Models.PostModel;
import itst.social_raccoon_api.Models.ProfileModel;
import itst.social_raccoon_api.Repositories.ProfileRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ModelMapper modelMapper;

    public List<ProfileModel> findAll() {
        return profileRepository.findAll();
    }

    public ProfileModel save(ProfileModel authentication) {
        return profileRepository.save(authentication);
    }

    public ProfileModel findById(Integer id) {
        return profileRepository.findById(id).orElse(null);
    }

    public ProfileModel findByUserId(Integer id){
        return profileRepository.findByUserId(id);
    }

    public ProfileModel update(ProfileModel profile) {
        return profileRepository.save(profile);
    }

    public ProfileDTO updateWithDTO(ProfileModel profile) {
        ProfileModel profileModel =  profileRepository.save(profile);
        ProfileDTO profileDTO = modelMapper.map(profileModel, ProfileDTO.class);
        return profileDTO;
    }

    public void delete(Integer id) {
        profileRepository.deleteById(id);
    }

    public ProfileDTO getProfileByUserId(Integer userId) {
        ProfileModel profile = profileRepository.findByUserId(userId);
        if (profile == null) {
            throw new NoSuchElementException("Profile not found for user with ID: " + userId);
        }

        // Convert ProfileModel to ProfileDTO using ModelMapper
        ProfileDTO profileDTO = modelMapper.map(profile, ProfileDTO.class);

        return profileDTO;
    }

}