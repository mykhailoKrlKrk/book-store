package mate.academy.book.store.mapper.user;

import java.util.stream.Collectors;
import mate.academy.book.store.config.MapperConfig;
import mate.academy.book.store.dto.order.response.OrderResponseDto;
import mate.academy.book.store.model.Order;
import mate.academy.book.store.model.ShoppingCart;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapperConfig.class)
public abstract class OrderMapper {
    @Autowired
    private OrderItemMapper itemMapper;

    @Mapping(target = "orderItems", ignore = true)
    @Mapping(target = "userId", source = "order.user.id")
    public abstract OrderResponseDto toDto(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "status", expression = "java(mate.academy.book.store.model.Status.PENDING)")
    @Mapping(target = "orderDate", expression = "java(java.time.LocalDateTime.now())")
    public abstract Order createOrderFromCart(ShoppingCart shoppingCart);

    @AfterMapping
    public void setOrderItemsToDto(@MappingTarget OrderResponseDto orderResponseDto, Order order) {
        orderResponseDto.setOrderItems(
                order.getOrderItems().stream()
                        .map(orderItem -> itemMapper.toDto(orderItem))
                        .collect(Collectors.toSet()));
    }
}
