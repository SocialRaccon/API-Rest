package itst.socialraccoon.api.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageStorageService {
    String storeImage(MultipartFile image) throws IOException;
    String deleteImage(String imageUrl) throws IOException;
}
