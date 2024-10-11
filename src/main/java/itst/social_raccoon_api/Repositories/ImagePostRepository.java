package itst.social_raccoon_api.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import itst.social_raccoon_api.Models.ImagePostModel;

public interface ImagePostRepository extends JpaRepository<ImagePostModel, Integer> {    
}