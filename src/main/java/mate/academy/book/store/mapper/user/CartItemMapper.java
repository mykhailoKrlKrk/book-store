package mate.academy.book.store.mapper.user;

import lombok.RequiredArgsConstructor;
import mate.academy.book.store.config.MapperConfig;
import mate.academy.book.store.dto.shoppingcart.request.CreateBookItemDto;
import mate.academy.book.store.dto.shoppingcart.response.CartItemResponseDto;
import mate.academy.book.store.model.Book;
import mate.academy.book.store.model.CartItem;
import mate.academy.book.store.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
@RequiredArgsConstructor
public abstract class CartItemMapper {
    @Mapping(target = "bookId", source = "cartItem.book.id")
    @Mapping(target = "bookTitle", source = "cartItem.book.title")
    public abstract CartItemResponseDto toDto(CartItem cartItem);

    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "id", ignore = true)
    public abstract CartItem toModel(CreateBookItemDto requestDto, Book book,
                                     ShoppingCart shoppingCart);
}
