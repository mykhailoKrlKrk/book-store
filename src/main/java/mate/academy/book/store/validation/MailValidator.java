package mate.academy.book.store.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MailValidator implements ConstraintValidator<Mail, String> {
    private static final String MAIL_PATTERN = "^[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[A-Za-z0-9-]+"
            + "(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public boolean isValid(String mail, ConstraintValidatorContext constraintValidatorContext) {
        return mail != null && mail.matches(MAIL_PATTERN);
    }
}
