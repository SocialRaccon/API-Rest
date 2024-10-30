package itst.social_raccoon_api.Services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class LocalImageStorageService implements ImageStorageService {

    private static final String IMAGE_PATH = "uploads/";

    @Override
    public String storeImage(MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            // Create the upload directory if it doesn't exist
            File uploadDirFile = new File(IMAGE_PATH);
            if (!uploadDirFile.exists()) {
                uploadDirFile.mkdirs();
            }
            // Save the file to the local file system
            String fileName = image.getOriginalFilename();
            Path filePath = Paths.get(IMAGE_PATH, fileName);
            Files.write(filePath, image.getBytes());

            // Generate the URL for the saved image
            return "/uploads/" + fileName;
        }
        return null;
    }
}
