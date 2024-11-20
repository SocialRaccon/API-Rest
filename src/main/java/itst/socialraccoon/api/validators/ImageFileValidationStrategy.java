package itst.socialraccoon.api.validators;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
@Component
public class ImageFileValidationStrategy implements FileValidationStrategy {
    private static final List<String> ALLOWED_TYPES = Arrays.asList("image/jpeg", "image/jpg", "image/png");

    @Override
    public boolean isValid(MultipartFile file) {
        return file != null && ALLOWED_TYPES.contains(file.getContentType());
    }

    public List<String> getAllowedTypes() {
        return ALLOWED_TYPES;
    }
}