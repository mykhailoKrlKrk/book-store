package mate.academy.book.store.mapper.user;

import mate.academy.book.store.config.MapperConfig;
import mate.academy.book.store.dto.order.response.OrderItemsResponseDto;
import mate.academy.book.store.model.Book;
import mate.academy.book.store.model.CartItem;
import mate.academy.book.store.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "bookId", source = "item.book.id")
    OrderItemsResponseDto toDto(OrderItem item);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "price", source = "book.price")
    OrderItem toOrderItem(CartItem cartItem, Book book);
}
