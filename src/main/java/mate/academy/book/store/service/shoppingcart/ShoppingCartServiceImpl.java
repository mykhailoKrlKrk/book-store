package mate.academy.book.store.service.shoppingcart;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import mate.academy.book.store.dto.shoppingcart.request.CreateBookItemDto;
import mate.academy.book.store.dto.shoppingcart.request.UpdateBookQuantityDto;
import mate.academy.book.store.dto.shoppingcart.response.ShoppingCartResponseDto;
import mate.academy.book.store.mapper.user.ShoppingCartMapper;
import mate.academy.book.store.model.CartItem;
import mate.academy.book.store.model.ShoppingCart;
import mate.academy.book.store.model.User;
import mate.academy.book.store.repository.shoppingcart.ShoppingCartRepository;
import mate.academy.book.store.service.user.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemService cartItemService;
    private final ShoppingCartMapper shoppingCartMapper;
    private final UserService userService;

    @Override
    public ShoppingCartResponseDto getUserCart() {
        User user = userService.getAuthenticatedUser();
        return shoppingCartMapper
                .toDto(shoppingCartRepository.findShoppingCartByUserId(user.getId()));
    }

    @Override
    public ShoppingCart getCart() {
        User authenticatedUser = userService.getAuthenticatedUser();
        return shoppingCartRepository.findShoppingCartByUserId(authenticatedUser.getId());
    }

    @Override
    public ShoppingCartResponseDto addBookToUserCart(CreateBookItemDto requestDto) {
        ShoppingCart shoppingCart = getShoppingCartByUser();
        Long bookIdToAdd = requestDto.getBookId();
        Integer quantityToAdd = requestDto.getQuantity();

        Optional<CartItem> item = shoppingCart.getCartItems()
                .stream()
                .filter(cartItem -> cartItem.getBook().getId().equals(bookIdToAdd))
                .findFirst();

        if (item.isPresent()) {
            CartItem existingCartItem = item.get();
            int newQuantity = existingCartItem.getQuantity() + quantityToAdd;
            existingCartItem.setQuantity(newQuantity);
            return getUserCart();
        }
        CartItem cartItem = cartItemService.save(requestDto);
        shoppingCart.getCartItems().add(cartItem);
        return getUserCart();
    }

    @Override
    public ShoppingCartResponseDto updateBookQuantity(Long id, UpdateBookQuantityDto requestDto) {
        cartItemService.findById(id).setQuantity(requestDto.getQuantity());
        return getUserCart();
    }

    @Override
    public void removeBookFromCart(Long cartItemId) {
        cartItemService.deleteById(cartItemId);
        getUserCart();
    }

    public ShoppingCart getShoppingCartByUser() {
        User user = userService.getAuthenticatedUser();
        return shoppingCartRepository.findShoppingCartByUserId(user.getId());
    }
}
