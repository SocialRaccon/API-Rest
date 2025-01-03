// ContentModerationValidationStrategy.java
package itst.socialraccoon.api.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import itst.socialraccoon.api.services.AzureContentModeratorService;
import itst.socialraccoon.api.exceptions.InvalidFileTypeException;
import itst.socialraccoon.api.exceptions.InappropriateContentException;

import java.util.List;

@Component
public class ContentModerationValidationStrategy implements FileValidationStrategy {

    private final ImageFileValidationStrategy fileTypeValidator;
    private final AzureContentModeratorService moderatorService;

    @Autowired
    public ContentModerationValidationStrategy(
            ImageFileValidationStrategy fileTypeValidator,
            AzureContentModeratorService moderatorService) {
        this.fileTypeValidator = fileTypeValidator;
        this.moderatorService = moderatorService;
    }

    @Override
    public boolean isValid(MultipartFile file) {
        validateFileType(file);
        validateContent(file);
        return true;
    }

    private void validateFileType(MultipartFile file) {
        if (!fileTypeValidator.isValid(file)) {
            throw new InvalidFileTypeException(
                    "El archivo debe ser de tipo: " + String.join(", ", fileTypeValidator.getAllowedTypes())
            );
        }
    }

    private void validateContent(MultipartFile file) {
        if (!moderatorService.isImageSafe(file)) {
            throw new InappropriateContentException(
                    "La imagen contiene contenido inapropiado y no puede ser procesada"
            );
        }
    }

    public List<String> getAllowedTypes() {
        return fileTypeValidator.getAllowedTypes();
    }
}