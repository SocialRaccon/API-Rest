package itst.socialraccoon.api.validators;

import itst.socialraccoon.api.annotations.FileType;
import jakarta.validation.ConstraintValidator;
import org.springframework.stereotype.Component;

@Component
public class FileTypeValidator implements ConstraintValidator<FileType, String> {
    private String[] types;

    @Override
    public void initialize(FileType constraintAnnotation) {
        this.types = constraintAnnotation.types();
    }

    @Override
    public boolean isValid(String value, jakarta.validation.ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        for (String type : types) {
            if (value.endsWith(type)) {
                return true;
            }
        }
        return false;
    }
}
