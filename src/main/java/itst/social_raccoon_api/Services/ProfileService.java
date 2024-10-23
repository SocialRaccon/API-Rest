package itst.social_raccoon_api.Services;

import java.util.List;
import java.util.NoSuchElementException;

import itst.social_raccoon_api.Dto.PostDTO;
import itst.social_raccoon_api.Dto.ProfileDTO;
import itst.social_raccoon_api.Dto.RelationshipDTO;
import itst.social_raccoon_api.Models.ImageProfileModel;
import itst.social_raccoon_api.Models.PostModel;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import itst.social_raccoon_api.Models.ProfileModel;
import itst.social_raccoon_api.Repositories.ProfileRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ImageProfileService imageProfileService;

    @Autowired
    private PostService postService;

    @Autowired
    private RelationshipService relationshipService;

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

    public ProfileDTO getProfileByUserId(Integer userId) {
        ProfileModel profile = profileRepository.findByUserId(userId);
        if (profile == null) {
            throw new NoSuchElementException("Profile not found for user with ID: " + userId);
        }

        // Convertir ProfileModel a ProfileDTO usando ModelMapper
        ProfileDTO profileDTO = modelMapper.map(profile, ProfileDTO.class);

        // Obtener la imagen del perfil
        ImageProfileModel imageProfile = imageProfileService.getImageProfileByUserId(userId);
        String profileImageUrl = imageProfile != null ? imageProfile.getImageUrl() : null;
        profileDTO.setProfileImageUrl(profileImageUrl);

        // Obtener los posts del usuario y convertirlos a PostDTO usando ModelMapper
        List<PostModel> posts = postService.findByUser(userId);
        java.lang.reflect.Type targetListType = new TypeToken<List<PostDTO>>() {
        }.getType(); // Tipo de la lista de PostDTO
        List<PostDTO> postDTOs = modelMapper.map(posts, targetListType);
        profileDTO.setPosts(postDTOs);

        // Obtener los seguidores y seguidos del usuario
        RelationshipDTO relationshipDTO = relationshipService.getFollowersAndFollowing(userId);
        profileDTO.setFollowing(relationshipDTO.getFollowing());
        profileDTO.setFollowers(relationshipDTO.getFollowers());

        return profileDTO;
    }

}