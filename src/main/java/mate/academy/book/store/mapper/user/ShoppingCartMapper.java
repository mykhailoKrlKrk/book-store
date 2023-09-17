package mate.academy.book.store.mapper.user;

import java.util.stream.Collectors;
import mate.academy.book.store.config.MapperConfig;
import mate.academy.book.store.dto.shoppingcart.response.ShoppingCartResponseDto;
import mate.academy.book.store.model.ShoppingCart;
import mate.academy.book.store.service.user.UserService;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(config = MapperConfig.class)
public abstract class ShoppingCartMapper {
    @Autowired
    private UserService userService;
    @Autowired
    private CartItemMapper cartItemMapper;

    public abstract ShoppingCartResponseDto toDto(ShoppingCart shoppingCart);

    @AfterMapping
    public void setBookInfoToCartDto(@MappingTarget ShoppingCartResponseDto responseDto,
                                     ShoppingCart shoppingCart) {
        responseDto.setCartItems(shoppingCart.getCartItems().stream()
                .map(cartItemMapper::toDto)
                .collect(Collectors.toSet()));
    }

    @AfterMapping
    public void setUserToShoppingCartDto(@MappingTarget ShoppingCartResponseDto cartResponseDto) {
        cartResponseDto.setUserId(userService.getAuthenticatedUser().getId());
    }
}
