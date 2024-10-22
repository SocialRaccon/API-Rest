package itst.social_raccoon_api.Repositories;

import itst.social_raccoon_api.Models.CommentModel;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentModel, Integer> {
    @Query(value = "SELECT * FROM comment WHERE idPost = :postId", nativeQuery = true)
    List<CommentModel> getCommentsByPostId(@Param("postId") Integer postId);

    @Query(value = "SELECT * FROM comment WHERE idPost = :postId \n-- #pageable\n", nativeQuery = true)
    List<CommentModel> getCommentsByPostId(@Param("postId") Integer postId, Pageable pageable);

    @Query(value = "SELECT * FROM comment WHERE idUser = :userId", nativeQuery = true)
    List<CommentModel> getCommentsByUserId(@Param("userId") Integer userId);

    @Query(value = "SELECT * FROM comment WHERE idUser = :userId \n-- #pageable\n", nativeQuery = true)
    List<CommentModel> getCommentsByUserId(@Param("userId") Integer userId, Pageable pageable);

    @Query(value = "SELECT * FROM comment WHERE idPost = :postId AND idUser = :userId", nativeQuery = true)
    List<CommentModel> getCommentsByPostIdAndUserId(@Param("postId") Integer postId, @Param("userId") Integer userId);

    @Query(value = "SELECT * FROM comment WHERE idPost = :postId AND idUser = :userId \n-- #pageable\n", nativeQuery = true)
    List<CommentModel> getCommentsByPostIdAndUserId(@Param("postId") Integer postId, @Param("userId") Integer userId, Pageable pageable);

    @Query(value = "DELETE FROM comment WHERE idPost = :postId AND idUser = :userId AND idComment = :commentId", nativeQuery = true)
    void deleteComment(@Param("postId") Integer postId, @Param("userId") Integer userId, @Param("commentId") Integer commentId);

    @Query(value = "SELECT * FROM comment WHERE idPost = :postId AND idUser = :userId AND idComment = :commentId", nativeQuery = true)
    CommentModel getComment(@Param("postId") Integer postId, @Param("userId") Integer userId, @Param("commentId") Integer commentId);
    
}
