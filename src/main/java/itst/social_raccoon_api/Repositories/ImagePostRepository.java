package itst.social_raccoon_api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import itst.social_raccoon_api.Models.ImagePostModel;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImagePostRepository extends JpaRepository<ImagePostModel, Integer> {
    @Query(value = "SELECT * FROM image_post WHERE idPost = :postId",nativeQuery = true)
    List<ImagePostModel> getImagePostByPostId(@Param("postId") Integer postId);

    @Query(value = "SELECT * FROM image_post WHERE idPost = :postId \n-- #pageable\n", nativeQuery = true)
    List<ImagePostModel> getImagePostByPostId(@Param("postId") Integer postId, Pageable pageable);

    @Query(value = "SELECT * FROM image_post WHERE idUser = :userId", nativeQuery = true)
    List<ImagePostModel> getImagePostByUserId(@Param("userId") Integer userId);

    @Query(value = "SELECT * FROM image_post WHERE idUser = :userId \n-- #pageable\n", nativeQuery = true)
    List<ImagePostModel> getImagePostByUserId(@Param("userId") Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM image_post WHERE idPost = :postId AND idUser = :userId", nativeQuery = true)
    List<ImagePostModel> getImagePostByPostIdAndUserId(@Param("postId") Integer postId, @Param("userId") Integer userId);

    @Query(value = "SELECT * FROM image_post WHERE idPost = :postId AND idUser = :userId \n-- #pageable\n", nativeQuery = true)
    List<ImagePostModel> getImagePostByPostIdAndUserId(@Param("postId") Integer postId, @Param("userId") Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM image_post WHERE idPost = :postId AND idImagePost = :imagePostId", nativeQuery = true)
    ImagePostModel getImagePost(@Param("postId") Integer postId, @Param("imagePostId") Integer imagePostId);

    @Query(value = "DELETE FROM image_post WHERE idPost = :postId AND idImagePost = :imagePostId", nativeQuery = true)
    void deleteImagePost(@Param("postId") Integer postId, @Param("imagePostId") Integer imagePostId);
}