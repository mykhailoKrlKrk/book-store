package mate.academy.book.store.service.impl;

import lombok.AllArgsConstructor;
import mate.academy.book.store.exception.EntityNotFoundException;
import mate.academy.book.store.model.OrderItem;
import mate.academy.book.store.repository.order.OrderItemRepository;
import mate.academy.book.store.service.OrderItemService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItem save(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public OrderItem findByOrderAndId(Long orderId, Long id) {
        return orderItemRepository.findOrderItemByOrderIdAndId(orderId, id).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can't find order item by parameters: "
                                + "OrderId:" + orderId + ",and itemId:" + id));
    }
}
