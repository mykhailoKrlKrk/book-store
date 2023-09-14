package mate.academy.book.store.service;

import mate.academy.book.store.dto.shoppingcart.request.CreateBookItemDto;
import mate.academy.book.store.dto.shoppingcart.request.UpdateBookQuantityDto;
import mate.academy.book.store.dto.shoppingcart.response.ShoppingCartResponseDto;

public interface ShoppingCartService {
    ShoppingCartResponseDto getUserCart();

    ShoppingCartResponseDto addBookToUserCart(CreateBookItemDto requestDto);

    ShoppingCartResponseDto updateBookQuantity(Long id, UpdateBookQuantityDto requestDto);

    ShoppingCartResponseDto removeBookFromCart(Long cartItemId);
}
