package itst.social.raccoon.api.repositories;

import itst.social.raccoon.api.models.CommentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<CommentModel, Integer> {

    @Query(value = "SELECT * FROM comment WHERE idPost = :postId", nativeQuery = true)
    Page<CommentModel> getCommentsByPostId(@Param("postId") Integer postId, Pageable pageable);

    @Query(value = "SELECT * FROM comment WHERE idUser = :userId", nativeQuery = true)
    Page<CommentModel> getCommentsByUserId(@Param("userId") Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM comment WHERE idPost = :postId AND idUser = :userId", nativeQuery = true)
    Page<CommentModel> getCommentsByPostIdAndUserId(@Param("postId") Integer postId, @Param("userId") Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM comment WHERE idPost = :postId AND idUser = :userId AND idComment = :commentId", nativeQuery = true)
    CommentModel getComment(@Param("postId") Integer postId, @Param("userId") Integer userId, @Param("commentId") Integer commentId);

}