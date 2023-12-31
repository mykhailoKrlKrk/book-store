package mate.academy.book.store.dto.user.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import mate.academy.book.store.validation.FieldMatch;
import mate.academy.book.store.validation.Mail;
import mate.academy.book.store.validation.Password;

@Getter
@Setter
@NoArgsConstructor
@Accessors(chain = true)
@FieldMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match!"
)
public class UserRegistrationRequestDto {
    @NotBlank
    @Size(min = 4, max = 50)
    @Mail
    private String email;
    @NotBlank
    @Size(min = 4, max = 100)
    @Password
    private String password;
    private String repeatPassword;
    @NotBlank
    @Size(min = 2, max = 30)
    private String firstName;
    @NotBlank
    @Size(min = 2, max = 30)
    private String lastName;
    @NotBlank
    @Size(min = 4, max = 100)
    private String shippingAddress;
}
