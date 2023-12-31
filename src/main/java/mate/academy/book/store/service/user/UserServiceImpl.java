package mate.academy.book.store.service.user;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.book.store.dto.user.request.UserRegistrationRequestDto;
import mate.academy.book.store.dto.user.response.UserResponseDto;
import mate.academy.book.store.exception.EntityNotFoundException;
import mate.academy.book.store.exception.RegistrationException;
import mate.academy.book.store.mapper.user.UserMapper;
import mate.academy.book.store.model.RoleName;
import mate.academy.book.store.model.ShoppingCart;
import mate.academy.book.store.model.User;
import mate.academy.book.store.repository.shoppingcart.ShoppingCartRepository;
import mate.academy.book.store.repository.user.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleService roleService;
    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto request)
            throws RegistrationException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("Unable to complete registration");
        }
        User user = userMapper.toModel(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(roleService.getRoleByRoleName(RoleName.ROLE_USER)));
        User savedUser = userRepository.save(user);
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
        return userMapper.toDto(savedUser);
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName()).orElseThrow(
                () -> new EntityNotFoundException("Can't find user with email: "
                        + authentication.getName()));
    }

    @Override
    public User getById(Long userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("Can't find user by id:" + userId));
    }
}
