package mate.academy.book.store.service.shoppingcart;

import mate.academy.book.store.dto.shoppingcart.request.CreateBookItemDto;
import mate.academy.book.store.dto.shoppingcart.request.UpdateBookQuantityDto;
import mate.academy.book.store.dto.shoppingcart.response.ShoppingCartResponseDto;
import mate.academy.book.store.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCartResponseDto getUserCart();

    ShoppingCart getCart();

    ShoppingCartResponseDto addBookToUserCart(CreateBookItemDto requestDto);

    ShoppingCartResponseDto updateBookQuantity(Long id, UpdateBookQuantityDto requestDto);

    void removeBookFromCart(Long cartItemId);
}
