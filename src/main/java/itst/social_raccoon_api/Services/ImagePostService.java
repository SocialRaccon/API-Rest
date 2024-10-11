package itst.social_raccoon_api.Services;

import org.hibernate.mapping.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import itst.social_raccoon_api.Repositories.ImagePostRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ImagePostService {
    
    @Autowired
    private ImagePostRepository ImagePostRepository;

    // public List<ImagePostModel> findAll(){
    //     return ImagePostRepository.findAll();
    // }


}
