package mate.academy.book.store.config.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class IsbnValidator implements ConstraintValidator<Isbn, String> {
    public static final String PATTERN_OF_ISBN = "^(978|979)[ -]?\\d{1,5}[ -]?\\d{1,7}[ -]?\\d{1,6}[ -]?\\d$";
    @Override
    public boolean isValid(String isbn, ConstraintValidatorContext constraintValidatorContext) {
        return isbn != null && Pattern.compile(PATTERN_OF_ISBN).matcher(isbn).matches();
    }
}
