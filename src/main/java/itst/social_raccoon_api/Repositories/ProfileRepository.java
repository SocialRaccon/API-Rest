package itst.social_raccoon_api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import itst.social_raccoon_api.Models.ProfileModel;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepository extends JpaRepository<ProfileModel, Integer> {
    // Find profile by user ID
    @Query(value = "SELECT * FROM profile WHERE idUser = :userId", nativeQuery = true)
    ProfileModel findByUserId(Integer userId);
}
