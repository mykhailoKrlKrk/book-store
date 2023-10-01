package mate.academy.book.store.dto.user.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.experimental.Accessors;
import mate.academy.book.store.validation.Password;

@Data
@Accessors(chain = true)
public class UserLoginRequestDto {
    @Size(min = 4, max = 50)
    @Email
    private String email;
    @Password
    private String password;
}
