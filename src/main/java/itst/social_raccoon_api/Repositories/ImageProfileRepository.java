package itst.social_raccoon_api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import itst.social_raccoon_api.Models.ImageProfileModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageProfileRepository extends JpaRepository<ImageProfileModel, Integer> {
    @Query(value = "SELECT * FROM image_profile WHERE idProfile = :profileId", nativeQuery = true)
    List<ImageProfileModel> getImageProfileByProfileId(@Param("profileId") Integer profileId);

    @Query(value = "SELECT * FROM image_profile WHERE idProfile = :profileId \n-- #pageable\n", nativeQuery = true)
    List<ImageProfileModel> getImageProfileByProfileId(@Param("profileId") Integer profileId, Pageable pageable);

    @Query(value = "SELECT * FROM image_profile WHERE idUser = :userId", nativeQuery = true)
    List<ImageProfileModel> getImageProfileByUserId(@Param("userId") Integer userId);

    @Query(value = "SELECT * FROM image_profile WHERE idUser = :userId \n-- #pageable\n", nativeQuery = true)
    List<ImageProfileModel> getImageProfileByUserId(@Param("userId") Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM image_profile WHERE idProfile = :profileId AND idUser = :userId", nativeQuery = true)
    List<ImageProfileModel> getImageProfileByProfileIdAndUserId(@Param("profileId") Integer profileId, @Param("userId") Integer userId);

    @Query(value = "SELECT * FROM image_profile WHERE idProfile = :profileId AND idUser = :userId \n-- #pageable\n", nativeQuery = true)
    List<ImageProfileModel> getImageProfileByProfileIdAndUserId(@Param("profileId") Integer profileId, @Param("userId") Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM image_profile WHERE idProfile = :profileId AND idImageProfile = :imageProfileId", nativeQuery = true)
    ImageProfileModel getImageProfile(@Param("profileId") Integer profileId, @Param("imageProfileId") Integer imageProfileId);

    @Query(value = "DELETE FROM image_profile WHERE idProfile = :profileId AND idImageProfile = :imageProfileId", nativeQuery = true)
    void deleteImageProfile(@Param("profileId") Integer profileId, @Param("imageProfileId") Integer imageProfileId);
}
