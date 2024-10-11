package itst.social_raccoon_api.Repositories;

import itst.social_raccoon_api.Models.CommentModel;
import itst.social_raccoon_api.Models.CompositeKeys.CommentPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentModel, CommentPK> {
}
