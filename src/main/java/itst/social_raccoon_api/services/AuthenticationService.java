package itst.social_raccoon_api.services;

import java.util.List;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import itst.social_raccoon_api.models.AuthenticationModel;
import itst.social_raccoon_api.repositories.AuthenticationRepository;
import org.springframework.stereotype.Service;

@Service
@Transactional
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

    public AuthenticationModel findByEmail(String email) {
        return authenticationRepository.findByEmail(email);
    }

    public void update(AuthenticationModel career) {
        authenticationRepository.save(career);
    }

    public void delete(Integer id) {
        authenticationRepository.deleteById(id);
    }

}
