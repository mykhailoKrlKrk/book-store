package mate.academy.book.store.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import mate.academy.book.store.validation.Password;

public record UserLoginRequestDto(
        @Size(min = 4, max = 50)
        @Email
        String email,
        @Password
        String password
) {
}
