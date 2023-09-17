package mate.academy.book.store.service.shoppingcart;

import mate.academy.book.store.dto.shoppingcart.request.CreateBookItemDto;
import mate.academy.book.store.model.CartItem;

public interface CartItemService {

    CartItem save(CreateBookItemDto requestDto);

    CartItem findById(Long id);

    void deleteById(Long cartItemId);
}
