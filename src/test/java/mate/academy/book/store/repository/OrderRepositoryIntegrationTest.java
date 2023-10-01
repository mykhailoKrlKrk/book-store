package mate.academy.book.store.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import mate.academy.book.store.model.Order;
import mate.academy.book.store.model.User;
import mate.academy.book.store.repository.order.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class OrderRepositoryIntegrationTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    @Sql(scripts = {
            "classpath:database/OrderRepositoryIntegrationTest/before/"
                    + "before_getAllByUser_ValidId_ReturnUserOrders.sql",
    }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {
            "classpath:database/OrderRepositoryIntegrationTest/after/"
                    + "after_getAllByUser_ValidId_ReturnUserOrders.sql"
    }, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Get all orders by user, "
            + "expected result: list with user orders")
    public void getAllByUser_ValidId_ReturnUserOrders() {
        List<Order> allOrdersByUser = orderRepository.getAllByUser(getUser());
        assertEquals(2, allOrdersByUser.size());
    }

    private User getUser() {
        User user = new User();
        user.setId(2L);
        user.setEmail("alice@mail.com");
        user.setPassword("$2a$12$FvxbShP4ccx0IYagELu0JON.m.6QldvUCUEjmsLx3KgU6yycA5GH");
        user.setShippingAddress("Alice");
        user.setFirstName("Bob");
        user.setLastName("Lor");
        return user;
    }
}
