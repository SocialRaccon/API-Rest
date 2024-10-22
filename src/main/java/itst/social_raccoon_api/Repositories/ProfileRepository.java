package itst.social_raccoon_api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import itst.social_raccoon_api.Models.ProfileModel;

public interface ProfileRepository extends JpaRepository<ProfileModel, Integer> {
    
}
