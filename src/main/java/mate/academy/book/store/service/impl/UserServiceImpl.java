package mate.academy.book.store.service.impl;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import mate.academy.book.store.dto.user.UserRegistrationRequestDto;
import mate.academy.book.store.dto.user.UserResponseDto;
import mate.academy.book.store.exception.RegistrationException;
import mate.academy.book.store.mapper.UserMapper;
import mate.academy.book.store.model.RoleName;
import mate.academy.book.store.model.User;
import mate.academy.book.store.repository.user.RoleRepository;
import mate.academy.book.store.repository.user.UserRepository;
import mate.academy.book.store.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    @Override
    public UserResponseDto register(UserRegistrationRequestDto request) throws RegistrationException {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RegistrationException("Unable to complete registration");
        }
        User user = userMapper.toModel(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(roleRepository.findRoleByRoleName(RoleName.ROLE_USER).get()));
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
