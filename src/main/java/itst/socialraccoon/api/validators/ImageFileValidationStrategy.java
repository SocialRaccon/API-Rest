package itst.socialraccoon.api.validators;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class ImageFileValidationStrategy implements FileValidationStrategy {
    private static final List<String> ALLOWED_TYPES = Arrays.asList("image/jpeg", "image/png", "image/jpg");

    @Override
    public boolean isValid(MultipartFile file) {
        String contentType = file.getContentType();
        return contentType != null && ALLOWED_TYPES.contains(contentType);
    }

    public List<String> getAllowedTypes() {
        return ALLOWED_TYPES;
    }
}