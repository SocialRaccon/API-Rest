package itst.socialraccoon.api.validators;

import org.springframework.web.multipart.MultipartFile;

public interface FileValidationStrategy {
    boolean isValid(MultipartFile file);
}
