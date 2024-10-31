package itst.social_raccoon_api.repositories;

import itst.social_raccoon_api.models.PostModel;
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

    @Query
    Page<PostModel> findAllByOrderByDateCreatedDesc(Pageable pageable);
}