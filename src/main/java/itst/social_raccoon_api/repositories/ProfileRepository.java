package itst.social_raccoon_api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import itst.social_raccoon_api.models.ProfileModel;
import org.springframework.data.jpa.repository.Query;

public interface ProfileRepository extends JpaRepository<ProfileModel, Integer> {
    // Find profile by user ID
    @Query(value = "SELECT * FROM profile WHERE idUser = :userId", nativeQuery = true)
    ProfileModel findByUserId(Integer userId);
}