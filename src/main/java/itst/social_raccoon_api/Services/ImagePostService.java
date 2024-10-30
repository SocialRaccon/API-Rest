package itst.social_raccoon_api.Services;

import itst.social_raccoon_api.Models.ImagePostModel;
import itst.social_raccoon_api.Models.ImageProfileModel;
import itst.social_raccoon_api.Models.ProfileModel;
import itst.social_raccoon_api.Repositories.ImagePostRepository;
import itst.social_raccoon_api.Repositories.ImageProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class ImagePostService {

    @Autowired
    private ImagePostRepository imagePostRepository;

    @Autowired
    private ImageStorageService imageStorageService;

    public List<ImagePostModel> findAll() {
        return imagePostRepository.findAll();
    }

    public ImagePostModel save(ImagePostModel imagePost) {
        return imagePostRepository.save(imagePost);
    }

    public void delete(Integer id) {
        imagePostRepository.deleteById(id);
    }

    public void update(ImagePostModel imagePost) {
        imagePostRepository.save(imagePost);
    }

    public void deleteImagePost(Integer postId, Integer imagePostId) {
        imagePostRepository.deleteImagePost(postId, imagePostId);
    }

    public List<ImagePostModel> getImagePostByPostId(Integer postId) {
        return imagePostRepository.getImagePostByPostId(postId);
    }

    public List<ImagePostModel> getImagePostByPostId(Integer postId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return imagePostRepository.getImagePostByPostId(postId, pageRequest);
    }

    public List<ImagePostModel> getImagePostByUserId(Integer userId) {
        return imagePostRepository.getImagePostByUserId(userId);
    }

    public List<ImagePostModel> getImagePostByUserId(Integer userId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return imagePostRepository.getImagePostByUserId(userId, pageRequest);
    }

    public List<ImagePostModel> getImagePostByPostIdAndUserId(Integer postId, Integer userId) {
        return imagePostRepository.getImagePostByPostIdAndUserId(postId, userId);
    }

    public List<ImagePostModel> getImagePostByPostIdAndUserId(Integer postId, Integer userId, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize);
        return imagePostRepository.getImagePostByPostIdAndUserId(postId, userId, pageRequest);
    }

    public ImagePostModel getImagePost(Integer postId, Integer imagePostId) {
        return imagePostRepository.getImagePost(postId, imagePostId);
    }
}
