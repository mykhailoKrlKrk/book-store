package mate.academy.book.store.service.order;

import lombok.AllArgsConstructor;
import mate.academy.book.store.exception.EntityNotFoundException;
import mate.academy.book.store.model.OrderItem;
import mate.academy.book.store.repository.order.OrderItemRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem findByOrderAndId(Long orderId, Long itemId) {
        return orderItemRepository.findOrderItemByOrderIdAndId(orderId, itemId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find order item by parameters: "
                                + "OrderId:" + orderId + ",and itemId:" + itemId));
    }
}
