package mate.academy.book.store.repository.shoppingcart;

import mate.academy.book.store.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
