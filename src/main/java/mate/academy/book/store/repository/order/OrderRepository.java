package mate.academy.book.store.repository.order;

import java.util.List;
import mate.academy.book.store.model.Order;
import mate.academy.book.store.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> getAllByUser(User user);
}
