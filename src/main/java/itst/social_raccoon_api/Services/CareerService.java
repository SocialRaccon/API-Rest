package itst.social_raccoon_api.Services;

import itst.social_raccoon_api.Models.CareerModel;
import itst.social_raccoon_api.Models.UserModel;
import itst.social_raccoon_api.Repositories.CareerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CareerService {

    @Autowired
    private CareerRepository careerRepository;

    public List<CareerModel> findAll() {
        return careerRepository.findAll();
    }

    public CareerModel save(CareerModel career) {
        return careerRepository.save(career);
    }

    public CareerModel findById(Integer id) {
        return careerRepository.findById(id).orElse(null);
    }

    public void update(CareerModel career) {
        careerRepository.save(career);
    }
    public void delete(Integer id) {
        careerRepository.deleteById(id);
    }

    public List<UserModel> getUsersByCareerId(Integer careerId) {
        return careerRepository.getUsersByCareerId(careerId);
    }


}
