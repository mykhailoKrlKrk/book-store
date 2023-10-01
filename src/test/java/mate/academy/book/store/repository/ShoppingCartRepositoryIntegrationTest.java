package mate.academy.book.store.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;
import mate.academy.book.store.model.ShoppingCart;
import mate.academy.book.store.model.User;
import mate.academy.book.store.repository.shoppingcart.ShoppingCartRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ShoppingCartRepositoryIntegrationTest {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Test
    @DisplayName("Find all books with category = 'horror', "
            + "expected result: list with two books")
    @Sql(scripts = {
            "classpath:database/ShoppingCartRepositoryIntegrationTest/before/"
                    + "before_findShoppingCartByUserId_ValidUerId_Success.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/ShoppingCartRepositoryIntegrationTest/after/"
                    + "after_findShoppingCartByUserId_ValidUerId_Success.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findShoppingCartByUserId_ValidUserId_Success() {
        //Given
        Long userId = 1L;
        //When
        Optional<ShoppingCart> actual = shoppingCartRepository.findShoppingCartByUserId(userId);
        ShoppingCart expected = new ShoppingCart();
        expected.setId(1L);
        expected.setUser(getUser());
        //Then
        assertEquals(expected, actual.get());
    }

    @Test
    @DisplayName("Find all books with category = 'horror', "
            + "expected result: list with two books")
    @Sql(scripts = {
            "classpath:database/ShoppingCartRepositoryIntegrationTest/before/"
                    + "before_findShoppingCartByUserId_InValidUserId_Error.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/ShoppingCartRepositoryIntegrationTest/after/"
                    + "after_findShoppingCartByUserId_InValidUserId_Error.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findShoppingCartByUserId_InValidUserId_Error() {
        //Given
        Long userId = 100L;
        //Then
        Optional<ShoppingCart> actual = shoppingCartRepository.findShoppingCartByUserId(userId);
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
