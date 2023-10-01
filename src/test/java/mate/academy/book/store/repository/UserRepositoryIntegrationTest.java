package mate.academy.book.store.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import mate.academy.book.store.model.User;
import mate.academy.book.store.repository.user.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Get user by mail, "
            + "expected result: return user by specific mail")
    public void findByEmail_Success() {
        //Given
        String userMail = "bob@mail.com";
        User expected = getUser();
        //When
        Optional<User> actual = userRepository.findByEmail(userMail);
        //Then
        assertEquals(expected, actual.get());
    }

    @Test
    @DisplayName("Get user by mail, "
            + "expected result: return user by specific mail")
    public void findByEmail_InvalidMail_GetError() {
        //Given
        String userMail = "unknown@mail.com";
        //When
        Optional<User> actual = userRepository.findByEmail(userMail);
        //Then
        assertEquals(Optional.empty(), actual);
    }

    private User getUser() {
        User user = new User();
        user.setId(1L);
        user.setEmail("bob@mail.com");
        user.setPassword("$2a$12$rUVIx9APvEpH9gveCYvOrOnUZVDdoF.w95J7eFckzFtlfGN.9m4tG");
        user.setShippingAddress("B0bAddress");
        user.setFirstName("Bob");
        user.setLastName("Obo");
        return user;
    }
}
