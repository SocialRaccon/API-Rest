package itst.socialraccoon.api.services;

import java.util.List;
import java.util.NoSuchElementException;

import itst.socialraccoon.api.dtos.ProfileDTO;
import itst.socialraccoon.api.models.ProfileModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import itst.socialraccoon.api.repositories.ProfileRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private RelationshipService relations;

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

    public ProfileModel findByUserId(Integer id) {
        return profileRepository.findByUserId(id);
    }

    @Transactional
    public ProfileModel update(ProfileModel profile) {
        return profileRepository.save(profile);
    }

    public ProfileDTO updateWithDTO(ProfileModel profile) {
        ProfileModel profileModel = profileRepository.save(profile);
        ProfileDTO profileDTO = modelMapper.map(profileModel, ProfileDTO.class);
        return profileDTO;
    }

    public void delete(Integer id) {
        profileRepository.deleteById(id);
    }

    public ProfileDTO getProfileByUserId(Integer userId) {
        ProfileModel profile = profileRepository.findByUserId(userId);
        if (profile == null) {
            throw new NoSuchElementException("Profile not found for user with given ID");
        }
        ProfileDTO profileDTO = modelMapper.map(profile, ProfileDTO.class);
        String username = profile.getIdUser().getName() + " " + profile.getIdUser().getLastName() + " " + profile.getIdUser().getSecondLastName();
        profileDTO.setUserName(username);
        profileDTO.setControlNumber(profile.getIdUser().getControlNumber());
        int followersCount = relations.countFollowers(userId);
        int followingCount = relations.countFollowing(userId);
        profileDTO.setFollowersCount(followersCount);
        profileDTO.setFollowingCount(followingCount);
        return profileDTO;
    }

    public ProfileDTO getProfileByControlNumber(String controlNumber) {
        ProfileModel profile = profileRepository.findAllByIdUser_ControlNumber(controlNumber);
        if (profile == null) {
            throw new NoSuchElementException("Profile not found for user with given control number: ");
        }
        ProfileDTO profileDTO = modelMapper.map(profile, ProfileDTO.class);
        String username = profile.getIdUser().getName() + " " + profile.getIdUser().getLastName() + " " + profile.getIdUser().getSecondLastName();
        profileDTO.setUserName(username);
        profileDTO.setControlNumber(profile.getIdUser().getControlNumber());
        int followersCount = relations.countFollowers(profile.getIdUser().getIdUser());
        int followingCount = relations.countFollowing(profile.getIdUser().getIdUser());
        profileDTO.setFollowersCount(followersCount);
        profileDTO.setFollowingCount(followingCount);
        return profileDTO;
    }

}