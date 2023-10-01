package mate.academy.book.store.service.shoppingcart;

import lombok.RequiredArgsConstructor;
import mate.academy.book.store.dto.shoppingcart.request.CreateBookItemDto;
import mate.academy.book.store.exception.EntityNotFoundException;
import mate.academy.book.store.mapper.user.CartItemMapper;
import mate.academy.book.store.model.CartItem;
import mate.academy.book.store.model.ShoppingCart;
import mate.academy.book.store.model.User;
import mate.academy.book.store.repository.book.BookRepository;
import mate.academy.book.store.repository.shoppingcart.CartItemRepository;
import mate.academy.book.store.repository.shoppingcart.ShoppingCartRepository;
import mate.academy.book.store.service.user.UserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemMapper cartItemMapper;
    private final UserService userService;

    @Override
    public CartItem save(CreateBookItemDto requestDto) {
        return cartItemRepository.save(
                cartItemMapper.toModel(requestDto,
                        bookRepository.findById(requestDto.getBookId()).get(),
                        getShoppingCartByUser()));
    }

    @Override
    public CartItem findById(Long id) {
        return cartItemRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Can't find cart item by id: " + id));
    }

    @Override
    public void deleteById(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    private ShoppingCart getShoppingCartByUser() {
        User user = userService.getAuthenticatedUser();
        return shoppingCartRepository.findShoppingCartByUserId(user.getId())
                .orElseThrow(
                        () -> new EntityNotFoundException("Can't find cart by user id: "
                                + user.getId()));
    }
}
