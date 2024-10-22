package itst.social_raccoon_api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import itst.social_raccoon_api.Models.AuthenticationModel;
import itst.social_raccoon_api.Repositories.AuthenticationRepository;

public class AuthenticationService {
    
    @Autowired
    private AuthenticationRepository authenticationRepository;

    public List<AuthenticationModel> findAll() {
        return authenticationRepository.findAll();
    }

    public AuthenticationModel save(AuthenticationModel authentication) {
        return authenticationRepository.save(authentication);
    }
    public AuthenticationModel findById(Integer id) {
        return authenticationRepository.findById(id).orElse(null);
    }

    public void update(AuthenticationModel career) {
        authenticationRepository.save(career);
    }
    public void delete(Integer id) {
        authenticationRepository.deleteById(id);
    }

}
