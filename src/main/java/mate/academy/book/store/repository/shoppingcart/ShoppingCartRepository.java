package mate.academy.book.store.repository.shoppingcart;

import java.util.Optional;
import mate.academy.book.store.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    Optional<ShoppingCart> findShoppingCartByUserId(Long id);
}
