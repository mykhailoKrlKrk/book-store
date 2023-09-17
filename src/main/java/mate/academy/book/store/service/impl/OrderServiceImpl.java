package mate.academy.book.store.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import mate.academy.book.store.dto.order.request.CreateOrderRequestDto;
import mate.academy.book.store.dto.order.request.UpdateOrderStatusRequestDto;
import mate.academy.book.store.dto.order.response.OrderItemsResponseDto;
import mate.academy.book.store.dto.order.response.OrderResponseDto;
import mate.academy.book.store.exception.EntityNotFoundException;
import mate.academy.book.store.mapper.user.OrderItemMapper;
import mate.academy.book.store.mapper.user.OrderMapper;
import mate.academy.book.store.model.Book;
import mate.academy.book.store.model.CartItem;
import mate.academy.book.store.model.Order;
import mate.academy.book.store.model.OrderItem;
import mate.academy.book.store.model.ShoppingCart;
import mate.academy.book.store.repository.order.OrderRepository;
import mate.academy.book.store.service.OrderItemService;
import mate.academy.book.store.service.OrderService;
import mate.academy.book.store.service.ShoppingCartService;
import mate.academy.book.store.service.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartService shoppingCartService;
    private final UserService userService;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final OrderItemService orderItemService;

    @Override
    public OrderResponseDto createOrder(CreateOrderRequestDto requestDto) {
        ShoppingCart userCart = shoppingCartService.getCart();

        Order orderFromCart = orderMapper.createOrderFromCart(userCart);
        orderFromCart.setShippingAddress(requestDto.getShippingAddress());
        orderFromCart.setTotal(getTotal(userCart));
        Order savedOrder = orderRepository.save(orderFromCart);

        Set<OrderItem> ordersFromUserCart = getOrdersFromUserCart(userCart);
        ordersFromUserCart.forEach(item -> item.setOrder(savedOrder));
        ordersFromUserCart.forEach(orderItemService::save);
        orderFromCart.setOrderItems(ordersFromUserCart);
        return orderMapper.toDto(savedOrder);
    }

    @Override
    public List<OrderResponseDto> getUserOrders() {
        return orderRepository.getAllByUser(userService.getAuthenticatedUser()).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long id, UpdateOrderStatusRequestDto requestDto) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find order by id: " + id));
        order.setStatus(requestDto.getStatus());
        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderItemsResponseDto> getOrderItems(Long orderId) {
        return orderRepository.findById(orderId).get().getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .toList();
    }

    @Override
    public OrderItemsResponseDto getOrderItem(Long orderId, Long itemId) {
        return orderItemMapper.toDto(orderItemService.findByOrderAndId(orderId, itemId));
    }

    private BigDecimal getTotal(ShoppingCart shoppingCart) {
        return shoppingCart.getCartItems().stream()
                .map(CartItem::getBook)
                .map(Book::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Set<OrderItem> getOrdersFromUserCart(ShoppingCart shoppingCart) {
        return shoppingCart.getCartItems().stream()
                .map(item -> orderItemMapper.toOrderItem(item, item.getBook()))
                .collect(Collectors.toSet());
    }
}
