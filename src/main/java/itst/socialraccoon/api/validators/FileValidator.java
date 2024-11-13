package itst.socialraccoon.api.validators;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileValidator {
    private FileValidationStrategy strategy;

    public void setStrategy(FileValidationStrategy strategy) {
        this.strategy = strategy;
    }

    public boolean validate(MultipartFile file) {
        return strategy.isValid(file);
    }
}