package itst.socialraccoon.api.annotations;
import itst.socialraccoon.api.validators.FileTypeValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FileTypeValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface FileType {
    String message() default "Invalid file type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String[] types();
}