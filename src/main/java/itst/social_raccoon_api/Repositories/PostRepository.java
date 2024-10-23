package itst.social_raccoon_api.Repositories;

import itst.social_raccoon_api.Models.PostModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface PostRepository extends JpaRepository<PostModel, Integer> {
    @Query(value = "SELECT * FROM post WHERE idUser = :idUser", nativeQuery = true)
    List<PostModel> findByUser(@Param("idUser") Integer idUser);

    Page<PostModel> findAllByOrderByDateCreatedDesc(Pageable pageable);
}