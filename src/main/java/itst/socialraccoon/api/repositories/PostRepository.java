package itst.socialraccoon.api.repositories;

import itst.socialraccoon.api.models.PostModel;
import itst.socialraccoon.api.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostRepository extends JpaRepository<PostModel, Integer> {
    @Query(value = "SELECT * FROM post WHERE idUser = :idUser", nativeQuery = true)
    List<PostModel> findByUser(@Param("idUser") Integer idUser);

    @Modifying
    @Query(value = "DELETE FROM post WHERE idUser = :idUser AND idPost = :idPost", nativeQuery = true)
    void deleteByUserAndPost(@Param("idUser") Integer idUser, @Param("idPost") Integer idPost);

    @Query(value = "SELECT * FROM post WHERE idUser = :idUser AND idPost = :idPost", nativeQuery = true)
    PostModel findByUserAndPost(@Param("idUser") Integer idUser, @Param("idPost") Integer idPost);

    @Query(value = "SELECT * FROM post WHERE idUser = :idUser", nativeQuery = true)
    Page<PostModel> findByUserAndPost(@Param("idUser") Integer idUser, Pageable pageable);

    //Get all posts ordered by date created and randomly without repeating posts
    @Query ("SELECT p FROM PostModel p ORDER BY RAND()")
    Page<PostModel> findAllByOrderByDateCreatedDesc(Pageable pageable);

    @Query("SELECT p FROM PostModel p WHERE p.user.idUser = :userId")
    Page<PostModel> findByUser_IdUser(@Param("userId") Integer userId, Pageable pageable);

    @Query(value = "SELECT p FROM PostModel p JOIN RelationshipModel r ON p.user.idUser = r.user.idUser WHERE r.followerUser.idUser = :userId ORDER BY RAND()")
    Page<PostModel> findRandomPostsByFollowedUsers(@Param("userId") Integer userId, Pageable pageable);

    @Query(value = "SELECT p.* FROM post p JOIN user u ON p.idUser = u.idUser JOIN career c ON u.idCareer = c.idCareer WHERE c.acronym = :acronym ORDER BY RAND()", nativeQuery = true)
    Page<PostModel> findRandomPostsByCareerAcronym(@Param("acronym") String acronym, Pageable pageable);
}