package itst.socialraccoon.api.validators;

import org.springframework.stereotype.Component;

@Component
public class CommentValidationStrategy implements TextValidationStrategy {

    @Override
    public boolean isValid(String text) {
        return text.length() <= 1000;
    }
}
