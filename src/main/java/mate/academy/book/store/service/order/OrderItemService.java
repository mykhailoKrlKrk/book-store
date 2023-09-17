package mate.academy.book.store.service.order;

import mate.academy.book.store.model.OrderItem;

public interface OrderItemService {
    OrderItem save(OrderItem orderItem);

    OrderItem findByOrderAndId(Long orderId, Long id);
}
