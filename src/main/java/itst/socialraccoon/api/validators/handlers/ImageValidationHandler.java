package itst.socialraccoon.api.validators.handlers;

import itst.socialraccoon.api.validators.ContentModerationValidationStrategy;
import itst.socialraccoon.api.validators.FileValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ImageValidationHandler {
    private final FileValidator fileValidator;
    private final ContentModerationValidationStrategy contentModerationStrategy;

    @Autowired
    public ImageValidationHandler(
            FileValidator fileValidator,
            ContentModerationValidationStrategy contentModerationStrategy) {
        this.fileValidator = fileValidator;
        this.contentModerationStrategy = contentModerationStrategy;
    }

    public void validateImage(MultipartFile file) {
        fileValidator.setStrategy(contentModerationStrategy);
        fileValidator.validate(file);
    }
}