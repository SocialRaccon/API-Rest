package itst.socialraccoon.api.services;

import java.util.List;

import itst.socialraccoon.api.models.AuthenticationModel;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import itst.socialraccoon.api.repositories.AuthenticationRepository;
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

    public void update(AuthenticationModel authentication) {
        authenticationRepository.save(authentication);
    }

    public void delete(Integer id) {
        authenticationRepository.deleteById(id);
    }

}
